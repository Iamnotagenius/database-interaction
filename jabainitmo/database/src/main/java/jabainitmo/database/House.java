package jabainitmo.database;

import java.time.LocalDate;

import jabainitmo.database.jdbc.JdbcEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "houses")
public class House implements JdbcEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private LocalDate date;
    private int floors;

    @Enumerated(EnumType.STRING)
    private HouseType type;

    @ManyToOne
    @JoinColumn(name = "street", nullable = false)
    private Street street;

    public House() {
    }
}
