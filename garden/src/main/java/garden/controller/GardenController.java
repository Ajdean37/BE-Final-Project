package garden.controller;

import garden.controller.model.PlantData;
import garden.service.GardenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/garden")
@Slf4j
public class GardenController {

 @Autowired
 private GardenService gardenService;

 @PostMapping("/plant")
 public PlantData insertPlant(
   @RequestBody PlantData plantData, Long locationId) {
  log.info("Creating plant {}", plantData);
  return gardenService.savePlant(locationId, plantData);
 }

}