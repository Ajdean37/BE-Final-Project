package garden.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Plant {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long plantId;

 private String plantName;
 private String flowerColor;
 private String growthSize;

 @EqualsAndHashCode.Exclude
 @ToString.Exclude
 @ManyToOne(cascade = CascadeType.ALL)
 @JoinColumn(name = "location_id", nullable = false)
 private Location location;

 @EqualsAndHashCode.Exclude
 @ToString.Exclude
 @ManyToMany(cascade = CascadeType.PERSIST)
 @JoinTable(name = "plant_needs", joinColumns = @JoinColumn(name = "plant_id"), inverseJoinColumns =
 @JoinColumn(name = "needs_id"))
 private Set<Needs> needs = new HashSet<>();
}
