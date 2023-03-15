package jabainitmo.database;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class House implements Entity {
    private long id;
    private String name;
    private LocalDate date;
    private int floors;
    private HouseType type;
    private Street street;
}
