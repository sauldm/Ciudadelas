package org.saul.ciudadelas.out.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "players")
public class PlayerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String nickName;

    @ManyToOne
    @JoinColumn(name = "lobby_id")
    private LobbyEntity lobby;
}
