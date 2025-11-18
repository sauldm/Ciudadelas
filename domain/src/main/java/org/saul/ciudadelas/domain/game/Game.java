package org.saul.ciudadelas.domain.game;

import org.saul.ciudadelas.domain.exception.ExpectedGameError;
import org.saul.ciudadelas.domain.exception.InternalGameException;
import org.saul.ciudadelas.domain.game.deck_cards.DeckCards;
import org.saul.ciudadelas.domain.game.deck_cards.actions.MilitaryActionCard;
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
        Player playerThief = getPlayerByCharacter(characterThief);
        if (playerThief == null) throw new InternalGameException("El jugador que roba no puede ser nulo");
        Player playerRobed = getPlayerByCharacter(characterRobed);
        if (!getActualRound().getTurnByCharacter(characterRobed).canPlayerPlay())
            return // Enviar evento de que el jugador esta asesinado;
                    ;
        playerThief.addGold(playerRobed.getAllGold());
    }

    public void stopCharacterPlaying(CharacterCard characterCard) {
        if (characterCard == null) throw new InternalGameException("La carta no puede ser nula");
        getActualRound().skipCharacterTurn(characterCard);
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


    public Player getPlayerByCharacter(CharacterCard characterCard) {
        if (characterCard == null) throw new InternalGameException("La carta no puede ser nula");
        for (Player player : players) {
            if (player.haveCharacter(characterCard)) return player;
        }
        return null;
    }

    public Round getActualRound() {
        return rounds.getLast();
    }

    public void swapHandsWithPlayer(Player playerToSwap, CharacterCard actualCharacterCard) {
        if (playerToSwap == null) throw new InternalGameException("El jugador no puede ser nulo");
        if (actualCharacterCard == null) throw new InternalGameException("La carta no puede ser nula");
        Player actualPlayer = getPlayerByCharacter(actualCharacterCard);
        List<DistrictCard> tempHand = new ArrayList<>(actualPlayer.getAllDistrictCardsInHand());
        actualPlayer.addDistrictCards(playerToSwap.getAllDistrictCardsInHand());
        playerToSwap.addDistrictCards(tempHand);
    }

    public void executePlayerCharacterAbility(Player player, CharacterCard characterCardTarget) {
        if (player == null) throw new InternalGameException("El jugador no puede ser nulo");
        if (characterCardTarget == null) throw new InternalGameException("La carta no puede ser nula");
        if (getActualRound().getActualTurn().getPlayer() != player)
            throw new InternalGameException("No es el turno del jugador");
        if (getActualRound().getActualTurn().isCharacterHabilityUsed()) throw new ExpectedGameError("No puede usar otra vez la habilidad");//Enviar evento al frontend
        player.executeCharacterAbility(this, characterCardTarget, getActualRound().getActualTurn().getCharacter());
        getActualRound().getActualTurn().characterHabilityUsed();

    }

    public void destroyDistrictOfOtherPlayer(DistrictCard districtCard) {
        if (districtCard == null) throw new InternalGameException("La carta no puede ser nula");
        Player playerTarget = getPlayerByDistrictCard(districtCard);
        if (playerTarget == null) throw new InternalGameException("El jugador objetivo no puede ser nulo");
        this.deckDistrictCards.addCard(playerTarget.getDistrictCardFromHand(districtCard));


    }

    public Player getPlayerByDistrictCard(DistrictCard districtCard) {
        if (districtCard == null) throw new InternalGameException("La carta no puede ser nula");
        for (Player player : players) {
            if (player.haveDistrictCard(districtCard)) return player;
        }
        return null;
    }
}



