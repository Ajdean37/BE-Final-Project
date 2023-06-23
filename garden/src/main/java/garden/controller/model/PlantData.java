package garden.controller.model;

import garden.entity.Plant;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlantData {
 private Long plantId;
 private String plantName;
 private String flowerColor;
 private String growthSize;

 public PlantData(Plant plant){
  plantId = plant.getPlantId();
  plantName = plant.getPlantName();
  flowerColor = plant.getFlowerColor();
  growthSize = plant.getGrowthSize();
 }

}
