package out.mapper;

import org.saul.ciudadelas.domain.game.deck_cards.cards.Card;
import org.saul.ciudadelas.domain.game.deck_cards.cards.DistrictCard;
import out.entity.DistrictCardEntity;

public class CardMapper {

    public DistrictCardEntity toEntity(DistrictCard card) {
        DistrictCardEntity entity = new DistrictCardEntity();
        entity.setName(card.getName());
        entity.setDescription(card.getDescription());
        entity.setColor(card.getColor());
        entity.setUndestructible(card.isUndestructible());
        entity.setPrice(card.getPrice());
        entity.setPoints(card.getPoints());
        return entity;
    }

    public DistrictCard toDomain(DistrictCardEntity entity) {
        return new DistrictCard(entity.getId(), entity.getName(),entity.getColor(), entity.isUndestructible(), entity.getDescription(),entity.getPrice(), entity.getPoints());
    }
}
