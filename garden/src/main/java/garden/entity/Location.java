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

 @ManyToMany(cascade = CascadeType.PERSIST)
 @JoinTable(name = "location_needs", joinColumns = @JoinColumn(name = "needs_id"), inverseJoinColumns =
 @JoinColumn(name = "location_id"))
 @EqualsAndHashCode.Exclude
 @ToString.Exclude
 public Set<Needs> needs = new HashSet<>();

 @EqualsAndHashCode.Exclude
 @ToString.Exclude
 @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
 public Set<Plant> plant = new HashSet<>();
}
