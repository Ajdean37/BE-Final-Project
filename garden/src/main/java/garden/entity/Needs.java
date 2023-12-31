package garden.entity;

import garden.controller.model.GardenData;
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
 @ManyToMany(mappedBy = "needs", cascade = CascadeType.PERSIST)
 private Set<Location> locations = new HashSet<>();

}
