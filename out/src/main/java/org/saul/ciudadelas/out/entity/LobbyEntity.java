package org.saul.ciudadelas.out.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "lobby")
public class LobbyEntity {

    @Id
    private UUID id;

    @OneToMany
    @JoinColumn(name = "lobby_id")
    private List<PlayerEntity> players;
}
