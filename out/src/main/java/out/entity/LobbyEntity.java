package out.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

    @OneToMany(mappedBy = "lobby")
    private List<PlayerEntity> players;
}
