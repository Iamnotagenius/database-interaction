package jabainitmo.database;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Street implements Entity {
    private long id;
    private String name;
    private int postIndex;
}
