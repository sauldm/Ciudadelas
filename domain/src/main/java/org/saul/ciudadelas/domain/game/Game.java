package org.saul.ciudadelas.domain.game;

import org.saul.ciudadelas.domain.exception.InternalGameException;
import org.saul.ciudadelas.domain.game.deck_cards.DeckCards;
import org.saul.ciudadelas.domain.game.deck_cards.actions.*;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;
import org.saul.ciudadelas.domain.game.deck_cards.cards.DistrictCard;
import org.saul.ciudadelas.domain.game.players.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.saul.ciudadelas.domain.game.GameConstants.*;

public class Game {
    private final Long id;
    private final DeckCards<DistrictCard> deckDistrictCards;
    private final List<Player> players;
    private final DeckCards<CharacterCard> deckCharacterCards;
    private List<Round> rounds;
    private List<RoundEvent> specialRoundEvents;
    private Turn turnSkipped;
    private List<Events> eventBuffer;


    private Game(Long id, DeckCards<DistrictCard> deckDistrictCards, List<Player> players, DeckCards<CharacterCard> deckCharacterCards) {
        this.id = id;
        this.deckDistrictCards = deckDistrictCards;
        this.players = players;
        this.deckCharacterCards = deckCharacterCards;
        this.rounds = new ArrayList<>();
        this.specialRoundEvents = new ArrayList<>();
        this.turnSkipped = null;


    }

    public static Game initializeNewGame(Long id,DeckCards<DistrictCard> deckDistrictCards, List<Player> players) {
        DeckCards<CharacterCard> deckCharacterCards = getAllCharacterCardsForGame();
        Game game = new Game(id,deckDistrictCards, players, deckCharacterCards);
        game.players.forEach(player -> player.addDistrictCardsInHand(deckDistrictCards.getCard(DISTRICT_CARDS_PER_PLAYER)));
        game.addRound();
        return game;
    }

    private static DeckCards<CharacterCard> getAllCharacterCardsForGame() {
        DeckCards<CharacterCard> deckCharacterCards = new DeckCards<>();
        deckCharacterCards.addCards(List.of(
                new AssassinActionCard(),
                new ThiefActionCard(),
                new WizardActionCard(),
                new KingActionCard(),
                new BishopActionCard(),
                new MerchantActionCard(),
                new ArchitectActionCard(),
                new MilitaryActionCard()
        ));
        return deckCharacterCards;
    }

    public void addTurnSkipped(Long characterId){
        if (characterId == null) throw new InternalGameException("La carta no puede ser nula");
        this.turnSkipped = getActualRound().getTurnByCharacter(characterId);

    }

    public Long getTurnSkippedCharacterId(){
        if (this.turnSkipped == null) return null;
        return this.turnSkipped.getCharacterId();
    }

    public List<DistrictCard> getDistrictCards(int numberOfCards) {
        return deckDistrictCards.getCard(numberOfCards);
    }

    private void addRound() {
        // Enviar evento de nueva ronda
        rounds.add(Round.initializeRound(getNewTurns()));
    }

    private List<Turn> getNewTurns() {
        List<Turn> turns = new ArrayList<>();
        CharacterCard randomCharacterCard;
        for (Player player : players) {
            for (int j = 0; j < CHARACTER_CARDS_PER_PLAYER; j++) {
                randomCharacterCard = deckCharacterCards.getRandomCard();
                player.addCharacterCard(randomCharacterCard);
                turns.add(new Turn(player, randomCharacterCard));
            }
        }
        Collections.sort(turns);
        return turns;
    }


    public void addRoundEvent(RoundEvent roundEvent) {
        if (roundEvent == null) throw new InternalGameException("El evento no puede ser nulo");
        getActualRound().addRoundEvent(roundEvent);
    }

    public void stoleCharacterGold(CharacterCard characterRobed, CharacterCard characterThief) {
        if (characterRobed == null) throw new InternalGameException("La carta no puede ser nula");
        if (characterThief == null) throw new InternalGameException("La carta no puede ser nula");
        Player playerThief = findPlayerByCharacterId(characterThief.getId());
        if (playerThief == null) throw new InternalGameException("El jugador que roba no puede ser nulo");
        Player playerRobed = findPlayerByCharacterId(characterRobed.getId());
        if (!getActualRound().getTurnByCharacter(characterRobed.getId()).canPlayerPlay())
            return // Enviar evento de que el jugador esta asesinado;
                    ;
        eventBuffer.add(Events.CHARACTER_CARD_STEALED);
        playerThief.addGold(playerRobed.getAllGold());
    }

    public void stopCharacterPlaying(Long characterCardId) {
        if (characterCardId == null) throw new InternalGameException("La carta no puede ser nula");
        eventBuffer.add(Events.CHARACTER_CARD_ELIMINATED);
        getActualRound().skipCharacterTurn(characterCardId);
    }

    public boolean characterIsNotInRound(Long characterCardId) {
        if (characterCardId == null) throw new InternalGameException("La carta no puede ser nula");
        return getActualRound().characterIsNotInRound(characterCardId);
    }

    public void nextStep() {
        if (!getActualRound().getActualTurn().isTurnCompleted()) {
            System.out.println("No se puede pasar de turno, el turno actual no ha finalizado");
            return;
        }
        if (getActualRound().getActualTurn().getPlayer().districtCardsBuilt() >= MAX_DISTRICTS_TO_BUILD_GAME) {
            eventBuffer.add(Events.GAME_ENDED);
            return;
        }
        if (getActualRound().isLastTurn()) {
            clearPlayerCharacterCards();
            addRound();
            eventBuffer.add(Events.NEXT_ROUND);
            return;
        }
        getActualRound().nextTurn(this);
        eventBuffer.add(Events.NEXT_TURN);
    }

    public void clearPlayerCharacterCards(){
        for (Player player : players) {
            deckCharacterCards.addCards(player.clearCharacterCards());
        }
    }

    public Player findPlayerByCharacterId(Long characterCardId) {
        if (characterCardId == null) throw new InternalGameException("La carta no puede ser nula");
        for (Player player : players) {
            if (player.haveCharacter(characterCardId) != null) return player;
        }
        return null;
    }

    public Round getActualRound() {
        return rounds.getLast();
    }

    public void swapHandsWithPlayer(CharacterCard actualCharacter, Long targetPlayerId) {
        if (actualCharacter == null) throw new InternalGameException("El jugador no puede ser nulo");
        if (targetPlayerId == null) throw new InternalGameException("La carta no puede ser nula");
        Player actualPlayer = findPlayerByCharacterId(actualCharacter.getId());
        Player targetPlayer = players.stream().filter(player -> player.getId().equals(targetPlayerId)).findFirst().orElse(null);
        if (actualPlayer == null) throw new InternalGameException("El jugador no puede ser nulo");
        if (targetPlayer == null) throw new InternalGameException("El jugador objetivo no puede ser nulo");
        if (actualPlayer == targetPlayer) throw new InternalGameException("El jugador no puede elegirse a si mismo");

        List<DistrictCard> tempHand = new ArrayList<>(targetPlayer.getAllDistrictCardsInHand());
        eventBuffer.add(Events.HANDS_SWAPPED);
        targetPlayer.addDistrictCardsInHand(actualPlayer.getAllDistrictCardsInHand());
        actualPlayer.addDistrictCardsInHand(tempHand);

        getActualRound().getActualTurn().characterHabilityUsed();
    }

    public void executePlayerCharacterAbility(Long characterCardActionId, Long targetId) {
        if (!isTurnCharacter(characterCardActionId)) throw new InternalGameException("No es el turno de ese personaje");
        getActualRound().getActualTurn().executeCharacterHability(this, characterCardActionId, targetId);
    }

    public void executeDistrictAbility(Long districtCardId){
        if (districtCardId == null) throw new InternalGameException("La carta no puede ser nula");
        getActualRound().getActualTurn().executeDistrictAbility(this, districtCardId);
    }

    public void destroyDistrictOfOtherPlayer(Long districtCardId, CharacterCard actualCharacter) {
        if (actualCharacter == null) throw new InternalGameException("El jugador no puede ser nulo");
        if (districtCardId == null) throw new InternalGameException("La carta no puede ser nula");

        Player playerTarget = findPlayerByDistrictCardIdBuilt(districtCardId);
        if (playerTarget == null) throw new InternalGameException("El jugador objetivo no puede ser nulo");

        Player actualPlayer = findPlayerByCharacterId(actualCharacter.getId());
        if (actualPlayer == null) throw new InternalGameException("El jugador no puede ser nulo");

        DistrictCard districtCard = playerTarget.findDistrictCardBuilt(districtCardId);
        if (districtCard == null) throw new InternalGameException("La carta de distrito no puede ser nula");


        if (!actualPlayer.removeGold(districtCard.getPrice() + 1)) {
            System.out.println("El jugador no tiene suficiente oro para destruir el distrito");
            return; //Enviar evento al frontend
        }
        if (playerTarget.findCharacterUndestructible() != null) {
            System.out.println("El distrito no puede ser destruido por personaje indestructible");
            return; //Enviar evento al frontend
        }

        if (districtCard.isUndestructible()) {
            System.out.println("El distrito no puede ser destruido por ser indestructible");
            return; //Enviar evento al frontend
        }

        eventBuffer.add(Events.DISTRICT_CARD_DESTROYED);
        actualPlayer.removeGold(districtCard.getPrice()+1);
        this.deckDistrictCards.addCard(playerTarget.getDistrictCardBuilt(districtCardId));
        getActualRound().getActualTurn().characterHabilityUsed();
    }


    public Player findPlayerByDistrictCardIdBuilt(Long districtCardId) {
        if (districtCardId == null) throw new InternalGameException("La carta no puede ser nula");
        for (Player player : players) {
            if (player.findDistrictCardBuilt(districtCardId) != null) return player;
        }
        return null;
    }

    public void swapCardsWithGame(WizardActionCard wizardActionCard) {
        if (wizardActionCard == null) throw new InternalGameException("La carta no puede ser nula");
        Player actualPlayer = findPlayerByCharacterId(wizardActionCard.getId());
        System.out.println("actualPlayer district cards before swap: " +actualPlayer.districtCardDeckCards());

        List<DistrictCard> playerCards = actualPlayer.getAllDistrictCardsInHand();
        List<DistrictCard> gameCards = deckDistrictCards.getCard(playerCards.size());
        actualPlayer.addDistrictCardsInHand(gameCards);
        deckDistrictCards.addCards(playerCards);
        System.out.println("actualPlayer district cards: " +actualPlayer.districtCardDeckCards());
        getActualRound().getActualTurn().characterHabilityUsed();
    }

    public CharacterCard findCharacterCardById(Long characterCardId) {
        CharacterCard characterCard = getActualRound().findCharacterById(characterCardId) ;
        if (characterCard == null){
            characterCard = deckCharacterCards.haveThisCard(characterCardId);
        }
        return characterCard;
    }
    public void buildDistrictCard(Long districtCardId, Long characterCardId) {
        if (!isTurnCharacter(characterCardId)) throw new InternalGameException("No es el turno de ese personaje");

        CharacterCard characterCard = findCharacterCardById(characterCardId);
        if (characterCard == null) throw new InternalGameException("La carta no puede ser nula");
        if (!characterCard.canBuildDistrict(getActualRound().getDistrictsBuiltThisTurn())) return;//Enviar evento al frontend

        Player player = findPlayerByCharacterId(characterCardId);
        if (player == null) throw new InternalGameException("El jugador no puede ser nulo");

        DistrictCard districtCard = player.findDistrictCardInHand(districtCardId);
        if (districtCard == null) throw new InternalGameException("La carta no puede ser nula");
        if (!player.removeGold(districtCard.getPrice())){
            System.out.println("El jugador no tiene suficiente oro para construir el distrito");
            return; //Enviar evento al frontend
        }


        player.removeGold(districtCard.getPrice());
        player.buildDistrictCard(player.getDistrictCardFromHand(districtCardId));
        getActualRound().incrementDistrictsBuiltThisTurn();
    }

    public boolean isTurnCharacter(Long characterCardId){
        return getActualRound().getActualTurn().getCharacterId().equals(characterCardId);
    }



    public void characterChooseCoins(){
        getActualRound().playerAddCoins(TURN_PLAYER_GOLD);
    }

    public void characterChooseCards(){
        getActualRound().playerAddDistrictCard(this,TURN_DISTRICT_CARD_PLAYER);
    }

    public List<Events> getEventsBuffer() {
        List<Events> events = new ArrayList<>(this.eventBuffer);
        this.eventBuffer.clear();
        return events;
    }
}