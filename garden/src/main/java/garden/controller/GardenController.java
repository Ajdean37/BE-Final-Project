package garden.controller;


import garden.controller.model.GardenData;
import garden.service.GardenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/garden")
@Slf4j
public class GardenController {

 @Autowired
 private GardenService gardenService;

 @PostMapping("/location")
 @ResponseStatus(code = HttpStatus.CREATED)
 public GardenData createLocation(@RequestBody GardenData gardenData) {
  log.info("Creating Location {}", gardenData);
  return gardenService.saveLocation(gardenData);
 }

 @PutMapping("/location/{locationId}")
 @ResponseStatus(code = HttpStatus.CREATED)
 public GardenData modifyLocation(@PathVariable Long locationId, @RequestBody GardenData gardenData) {
  gardenData.setLocationId(locationId);
  log.info("Modifying Location {}", gardenData);
  return gardenService.saveLocation(gardenData);
 }

 @PostMapping("/location/{locationId}/plant")
 @ResponseStatus(code = HttpStatus.CREATED)
 public GardenData.LocationPlant addPlant(@PathVariable Long locationId,
                                          @RequestBody GardenData.LocationPlant plant) {
  log.info("Adding plant {} {}", locationId, plant);
  return gardenService.savePlant(locationId, plant);
 }

}
