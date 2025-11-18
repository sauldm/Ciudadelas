package org.saul.ciudadelas.domain.game;

import org.saul.ciudadelas.domain.exception.ExpectedGameError;
import org.saul.ciudadelas.domain.exception.InternalGameException;
import org.saul.ciudadelas.domain.game.deck_cards.DeckCards;
import org.saul.ciudadelas.domain.game.deck_cards.actions.WizardActionCard;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;
import org.saul.ciudadelas.domain.game.deck_cards.cards.DistrictCard;
import org.saul.ciudadelas.domain.game.players.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.saul.ciudadelas.domain.game.GameConstants.CHARACTER_CARDS_PER_PLAYER;
import static org.saul.ciudadelas.domain.game.GameConstants.DISTRICT_CARDS_PER_PLAYER;

public class Game {
    private final DeckCards<DistrictCard> deckDistrictCards;
    private final List<Player> players;
    private final DeckCards<CharacterCard> deckCharacterCards;
    public List<Round> rounds;


    private Game(DeckCards<DistrictCard> deckDistrictCards, List<Player> players, DeckCards<CharacterCard> deckCharacterCards) {
        this.deckDistrictCards = deckDistrictCards;
        this.players = players;
        this.deckCharacterCards = deckCharacterCards;
        this.rounds = new ArrayList<>();


    }

    public static Game initializeNewGame(DeckCards<DistrictCard> deckDistrictCards, List<Player> players, DeckCards<CharacterCard> deckCharacterCards) {
        Game game = new Game(deckDistrictCards, players, deckCharacterCards);
        game.players.forEach(player -> player.addDistrictCards(deckDistrictCards.getCard(DISTRICT_CARDS_PER_PLAYER)));
        game.addRound();
        return game;
    }

    public List<DistrictCard> getDistrictCards(int numberOfCards) {
        return deckDistrictCards.getCard(numberOfCards);
    }

    public void addRound() {
        // Enviar evento de nueva ronda
        rounds.add(Round.initializeRound(getNewTurns()));
    }


    public List<Turn> getNewTurns() {
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
        playerThief.addGold(playerRobed.getAllGold());
    }

    public void stopCharacterPlaying(Long characterCardId) {
        if (characterCardId == null) throw new InternalGameException("La carta no puede ser nula");
        getActualRound().skipCharacterTurn(characterCardId);
        nextStep();
    }

    public void nextStep() {
        if (!getActualRound().getActualTurn().isTurnCompleted()) {
            System.out.println("No se puede pasar de turno, el turno actual no ha finalizado");
            return; //Enviar evento de error
        }
        if (!getActualRound().nextTurn()) {
            for (Player player : players) {
                deckCharacterCards.addCards(player.clearCharacterCards());
            }
            addRound();

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

    public void swapHandsWithPlayer(CharacterCard actualCharacter, Long targetCharacterId) {
        if (actualCharacter == null) throw new InternalGameException("El jugador no puede ser nulo");
        if (targetCharacterId == null) throw new InternalGameException("La carta no puede ser nula");
        Player actualPlayer = findPlayerByCharacterId(actualCharacter.getId());
        Player targetPlayer = findPlayerByCharacterId(targetCharacterId);
        if (actualPlayer == null) throw new InternalGameException("El jugador no puede ser nulo");
        if (targetPlayer == null) throw new InternalGameException("El jugador objetivo no puede ser nulo");
        List<DistrictCard> tempHand = new ArrayList<>(targetPlayer.getAllDistrictCardsInHand());
        targetPlayer.addDistrictCards(actualPlayer.getAllDistrictCardsInHand());
        actualPlayer.addDistrictCards(tempHand);
        getActualRound().getActualTurn().characterHabilityUsed();
    }

    public void executePlayerCharacterAbility(Long characterCardActionId, Long targetId) {
        getActualRound().getActualTurn().executeCharacterHability(this, characterCardActionId, targetId);
    }

    public void destroyDistrictOfOtherPlayer(Long districtCardId) {
        if (districtCardId == null) throw new InternalGameException("La carta no puede ser nula");
        Player playerTarget = findPlayerByDistrictCardId(districtCardId);
        if (playerTarget == null) throw new InternalGameException("El jugador objetivo no puede ser nulo");
        this.deckDistrictCards.addCard(playerTarget.getDistrictCardFromHand(districtCardId));
        getActualRound().getActualTurn().characterHabilityUsed();


    }

    public Player findPlayerByDistrictCardId(Long districtCardId) {
        if (districtCardId == null) throw new InternalGameException("La carta no puede ser nula");
        for (Player player : players) {
            if (player.haveDistrictCard(districtCardId)) return player;
        }
        return null;
    }

    public void swapCardsWithGame(WizardActionCard wizardActionCard) {
        if (wizardActionCard == null) throw new InternalGameException("La carta no puede ser nula");
        Player actualPlayer = findPlayerByCharacterId(wizardActionCard.getId());
        List<DistrictCard> playerCards = actualPlayer.getAllDistrictCardsInHand();
        List<DistrictCard> gameCards = deckDistrictCards.getCard(playerCards.size());
        actualPlayer.addDistrictCards(gameCards);
        deckDistrictCards.addCards(playerCards);
        getActualRound().getActualTurn().characterHabilityUsed();
    }

    public CharacterCard findCharacterCardById(Long characterCardId) {
        return getActualRound().findCharacterById(characterCardId);
    }

}