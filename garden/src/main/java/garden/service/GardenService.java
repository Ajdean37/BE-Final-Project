package garden.service;

import garden.controller.model.GardenData;
import garden.dao.LocationDao;
import garden.dao.NeedsDao;
import garden.dao.PlantDao;
import garden.entity.Location;
import garden.entity.Plant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class GardenService {

 @Autowired
 private LocationDao locationDao;

 @Autowired
 private PlantDao plantDao;

 @Autowired
 private NeedsDao needsDao;

 @Transactional(readOnly = false)
 public GardenData saveLocation(GardenData gardenData) {
  Long locationId = gardenData.getLocationId();
  Location location;

  if (locationId == null) {
   location = findOrCreateLocation(locationId);
  } else {
   location = findLocationById(locationId);
   if (location == null) {
    throw new NoSuchElementException("Location with ID = " + locationId + " does not exist");
   }
  }
  copyLocation( location, gardenData);
  return new GardenData(locationDao.save(location));
 }

 private void copyLocation(Location location, GardenData gardenData) {
  location.setLocationId(gardenData.getLocationId());
  location.setLocationName(gardenData.getLocationName());
  location.setLocationSize(gardenData.getLocationSize());
  location.setPlantType(gardenData.getPlantType());
 }

 private Location findLocationById(Long locationId) {
  return locationDao.findById(locationId).orElseThrow(() -> new NoSuchElementException("Location with ID = " + locationId + " was not found"));
 }

 private Location findOrCreateLocation(Long locationId) {
  Location location;

  if (Objects.isNull(locationId)) {
   location = new Location();
  } else {
   location = findLocationById(locationId);
  }
  return location;
 }

 @Transactional(readOnly = false)
 public GardenData.LocationPlant savePlant(Long locationId, GardenData.LocationPlant locationPlant) {
  Location location = findLocationById(locationId);
  Long plantId = locationPlant.getPlantId();
  Plant plant = findOrCreatePlant(locationPlant.getPlantId(), plantId);
  copyPlantFields(plant, locationPlant);
  plant.setLocation(location);
  location.getPlant().add(plant);
  Plant dbPlant = plantDao.save(plant);

  return new GardenData.LocationPlant(dbPlant);
 }

 private void copyPlantFields(Plant plant, GardenData.LocationPlant locationPlant) {
  plant.setPlantId(locationPlant.getPlantId());
  plant.setPlantName(locationPlant.getPlantName());
  plant.setGrowthSize(locationPlant.getGrowthSize());
  plant.setFlowerColor(locationPlant.getFlowerColor());
 }

 private Plant findOrCreatePlant(Long plantId, Long locationId) {
  Plant plant;

  if (Objects.isNull(plantId)) {
   plant = new Plant();
  } else {
   plant = findPlantById(locationId, plantId);
  }
  return plant;
 }

 private Plant findPlantById(Long locationId, Long plantId) {
  Plant plant =
    plantDao.findById(plantId).orElseThrow(() -> new NoSuchElementException("Plant with ID = " + plantId + " was not " +
      "found"));

  if (locationId.equals(plant.getLocation().getLocationId())) {
   return plant;
  } else {
   plant = findPlantById(locationId, plantId);
  }
  return plant;
 }
}
