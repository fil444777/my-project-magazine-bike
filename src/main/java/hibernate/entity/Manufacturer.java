package hibernate.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = {"motorcycle"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "manufacturers")
@Audited
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "Manufacturers")
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(
            mappedBy = "manufacturer",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Motorcycle> motorcycle = new ArrayList<>();
}
