package out.adapters;

import org.saul.ciudadelas.domain.game.deck_cards.cards.DistrictCard;
import org.saul.ciudadelas.ports.CardRepositoryPort;
import org.springframework.stereotype.Repository;
import out.mapper.CardMapper;
import out.repositories.CardJpaRepository;

import java.util.List;

@Repository
public class CardRepositoryAdapter implements CardRepositoryPort {

    private final CardJpaRepository jpaRepository;
    private final CardMapper mapper;

    public CardRepositoryAdapter(CardJpaRepository jpaRepository, CardMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<DistrictCard> findAllCards() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
