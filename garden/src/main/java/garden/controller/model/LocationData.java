package garden.controller.model;

import garden.entity.Location;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class LocationData {
 private Long locationId;
 private String locationName;
 private String locationSize;
 private String plantType;

 public LocationData(Location location) {
  locationId = location.getLocationId();
  locationName = location.getLocationName();
  locationSize = location.getLocationSize();
  plantType = location.getPlantType();
 }

}
