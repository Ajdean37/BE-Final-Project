package garden.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Location {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long locationId;

 private String locationName;
 private String locationSize;
 private String plantType;

 @EqualsAndHashCode.Exclude
 @ToString.Exclude
 @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
 private Set<Plant> plant = new HashSet<>();
}
