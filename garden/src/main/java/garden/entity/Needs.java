package garden.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;


@Data
@Entity
public class Needs {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long needsId;

 private String soilType;
 private String sunExposure;
 private String waterAmount;
 private String plantingSpace;

 @EqualsAndHashCode.Exclude
 @ToString.Exclude
 @ManyToMany(mappedBy = "needs")
 private Set<Plant> plant = new HashSet<>();

}
