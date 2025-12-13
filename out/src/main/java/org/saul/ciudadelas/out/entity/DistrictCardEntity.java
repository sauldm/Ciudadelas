package org.saul.ciudadelas.out.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.saul.ciudadelas.domain.game.deck_cards.Color;

@Getter
@Setter
@Entity
@Table(name = "cards")
public class DistrictCardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Color color;
    private boolean undestructible;
    private String description;
    private Long price;
    private int points;
}
