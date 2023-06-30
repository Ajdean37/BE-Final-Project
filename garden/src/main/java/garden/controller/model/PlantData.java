package garden.controller.model;

import garden.entity.Location;
import garden.entity.Needs;
import garden.entity.Plant;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class PlantData {
 private Long plantId;
 private String plantName;
 private String flowerColor;
 private String growthSize;
 private PlantLocation location;
 private Set<String> need = new HashSet<>();

 public PlantData(Plant plant){
  plantId = plant.getPlantId();
  plantName = plant.getPlantName();
  flowerColor = plant.getFlowerColor();
  growthSize = plant.getGrowthSize();
  location = new PlantLocation(plant.getLocation());

  for (Needs needs : plant.getNeeds()) {
   need.add(String.valueOf(needs.getNeedsId()));
  }
 }

 @Data
 @NoArgsConstructor
 public static class PlantLocation {
  private Long locationId;
  private String locationName;
  private String locationSize;
  private String plantType;

  public PlantLocation(Location location) {
   locationId = location.getLocationId();
   locationName = location.getLocationName();
   locationSize = location.getLocationSize();
   plantType = location.getPlantType();

  }
 }
}
