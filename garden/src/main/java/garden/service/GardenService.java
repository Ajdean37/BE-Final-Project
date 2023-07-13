package garden.service;

import garden.controller.model.GardenData;
import garden.dao.LocationDao;
import garden.dao.NeedsDao;
import garden.dao.PlantDao;
import garden.entity.Location;
import garden.entity.Needs;
import garden.entity.Plant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
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

 @Transactional(readOnly = false)
 public GardenData.LocationNeeds saveNeeds(Long locationId, GardenData.LocationNeeds locationNeeds) {
  Location location = findLocationById(locationId);
  Long needsId = locationNeeds.getNeedsId();
  Needs needs = findOrCreateNeeds(locationNeeds.getNeedsId(), needsId);
  copyNeeds(needs, locationNeeds);
  needs.setNeedsId(needsId);
  location.getNeeds().add(needs);
  Needs dbNeeds = needsDao.save(needs);
  return new GardenData.LocationNeeds(dbNeeds);
 }

 private void copyNeeds(Needs needs, GardenData.LocationNeeds locationNeeds) {
  needs.setNeedsId(locationNeeds.getNeedsId());
  needs.setSoilType(locationNeeds.getSoilType());
  needs.setSunExposure(locationNeeds.getSunExposure());
  needs.setWaterAmount(locationNeeds.getWaterAmount());
  needs.setPlantingSpace(locationNeeds.getPlantingSpace());
 }

 private Needs findOrCreateNeeds(Long needsId, Long locationId) {
  Needs needs;

  if (Objects.isNull(needsId)) {
   needs = new Needs();
  } else {
   needs = findNeedsById(locationId, needsId);
  }

  for (Location location : needs.getLocations()) {
   if (Objects.equals(location.getLocationId(), locationId)) {
    needs.getLocations().add(location);
   }
  }
  return needs;
 }

 private Needs findNeedsById(Long locationId, Long needsId) {
  Needs needs =
    needsDao.findById(needsId).orElseThrow(() -> new NoSuchElementException("Needs with ID = " + needsId + "was not " +
      "found"));

  if (locationId.equals(needs.getNeedsId())) {
   return needs;
  } else {
   throw new IllegalArgumentException("Needs with ID = " + needsId + " does not match the location ID of " + locationId );
  }
 }

 @Transactional(readOnly = true)
 public List<GardenData> retrieveAllLocations() {
  List<GardenData> result = new LinkedList<>();
  List<Location> locations = locationDao.findAll();

  for (Location location : locations) {
   GardenData gd = new GardenData(location);
   gd.getNeeds().clear();
   gd.getPlant().clear();
   result.add(gd);
  }
  return result;
 }

 @Transactional(readOnly = true)
 public  GardenData retrieveLocationsById(Long locationId) {
  Location location =
    locationDao.findById(locationId).orElseThrow(() -> new NoSuchElementException("Location with ID = " + locationId + " does not exist"));
  return new GardenData(location);
 }

 @Transactional(readOnly = false)
 public void deleteLocationById(Long locationId) {
  Location location = findLocationById(locationId);
  locationDao.delete(location);
 }
}
