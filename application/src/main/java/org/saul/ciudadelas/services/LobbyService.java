    package org.saul.ciudadelas.services;

    import org.saul.ciudadelas.domain.game.players.Player;
    import org.saul.ciudadelas.domain.lobby.Lobby;
    import org.saul.ciudadelas.ports.LobbyRepositoryPort;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    import java.util.List;
    import java.util.UUID;

    @Service
    public class LobbyService {

        private final LobbyRepositoryPort lobbyRepositoryPort;
        private final PlayerService playerService;

        public LobbyService(LobbyRepositoryPort lobbyRepositoryPort, PlayerService playerService) {
            this.lobbyRepositoryPort = lobbyRepositoryPort;
            this.playerService = playerService;
        }

        public List<UUID> getLobbiesWithLessThan2Players(){
            return lobbyRepositoryPort.findAllLobbyWithMaxTwoPlayers();
        }

        @Transactional
        public Lobby createNewLobby(UUID lobbyId) {
            Lobby lobby = new Lobby(lobbyId);
            return lobbyRepositoryPort.save(lobby);

        }

        @Transactional
        public Player addPlayerToLobby(UUID lobbyId, String nickName) {
            Lobby lobby = lobbyRepositoryPort.findById(lobbyId).orElseThrow(() -> new RuntimeException("Lobby not found"));
            Player player = playerService.findByName(nickName).orElseThrow(() -> new RuntimeException("Player not found"));
            lobby.addPlayer(player);
            lobbyRepositoryPort.save(lobby);
            System.out.println(lobbyRepositoryPort.save(lobby));
            return player;

        }

        @Transactional
        public boolean removePlayerFromLobby(UUID lobbyId, String nickName) {
            return lobbyRepositoryPort.removePlayer(lobbyId, nickName);
        }



        @Transactional
        public boolean deleteLobby(UUID lobbyId){
            lobbyRepositoryPort.findById(lobbyId).orElseThrow(() -> new RuntimeException("Lobby not found"));
            return lobbyRepositoryPort.remove(lobbyId) != null;
        }

        public List<String> getAllPlayers(UUID id) {
            return lobbyRepositoryPort.findAllPlayers(id);
        }
    }
