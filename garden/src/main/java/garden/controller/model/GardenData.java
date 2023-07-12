package garden.controller.model;

import garden.entity.Location;
import garden.entity.Needs;
import garden.entity.Plant;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class GardenData {
 private Long locationId;
 private String locationName;
 private String locationSize;
 private String plantType;
 public Set<Needs> needs = new HashSet<>();
 public Set<Plant> plant = new HashSet<>();

 public GardenData(Location location) {
  locationId = location.getLocationId();
  locationName = location.getLocationName();
  locationSize = location.getLocationSize();
  plantType = location.getPlantType();

  for (Needs need : location.getNeeds()) {
   needs.add(new LocationNeeds(need));
  }

  for (Plant plants : location.getPlant()) {
   plant.add(new LocationPlant(plants));
  }
 }

 @Data
 @NoArgsConstructor
 public static class LocationNeeds extends Needs {
  private Long needsId;
  private String soilType;
  private String sunExposure;
  private String waterAmount;
  private String plantingSpace;

  public LocationNeeds(Needs need) {
   needsId = need.getNeedsId();
   soilType = need.getSoilType();
   sunExposure = need.getSunExposure();
   waterAmount = need.getWaterAmount();
   plantingSpace = need.getPlantingSpace();
  }
 }

 @Data
 @NoArgsConstructor
 public static class LocationPlant extends Plant {
  private Long plantId;
  private String plantName;
  private String flowerColor;
  private String growthSize;

  public LocationPlant(Plant plant) {
   plantId = plant.getPlantId();
   plantName = plant.getPlantName();
   flowerColor = plant.getFlowerColor();
   growthSize= plant.getGrowthSize();
  }

 }

}
