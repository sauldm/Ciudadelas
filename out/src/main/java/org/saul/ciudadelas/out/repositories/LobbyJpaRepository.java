package org.saul.ciudadelas.out.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.saul.ciudadelas.out.entity.LobbyEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface LobbyJpaRepository extends JpaRepository<LobbyEntity, UUID> {

    @Query("SELECT l.id FROM LobbyEntity l LEFT JOIN l.players p GROUP BY l.id HAVING COUNT(p) < 2")
    List<UUID> findAllLobbyWithMaxTwoPlayers();

    @Query("select p.nickName from LobbyEntity l join l.players p where l.id = :id")
    List<String> findPlayerNickNames(@Param("id") UUID id);

    @Modifying
    @Query("update PlayerEntity p set p.lobby = null where p.lobby.id = :lobbyId and p.nickName = :nick")

    int removePlayer(@Param("lobbyId") UUID lobbyId,
                     @Param("nick") String nick);
}
