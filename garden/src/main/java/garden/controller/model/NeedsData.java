package garden.controller.model;

import garden.entity.Needs;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NeedsData {
 private Long needsId;
 private String soilType;
 private String sunExposure;
 private String waterAmount;
 private String plantingSpace;

 public NeedsData(Needs needs) {
  needsId = needs.getNeedsId();
  soilType = needs.getSoilType();
  sunExposure = needs.getSunExposure();
  waterAmount = needs.getWaterAmount();
  plantingSpace = needs.getPlantingSpace();
 }
}
