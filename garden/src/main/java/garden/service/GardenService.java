package garden.service;

import garden.controller.model.PlantData;
import garden.dao.LocationDao;
import garden.dao.NeedsDao;
import garden.dao.PlantDao;
import garden.entity.Location;
import garden.entity.Needs;
import garden.entity.Plant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

@Service
public class GardenService {

 @Autowired
 private LocationDao locationDao;

 @Autowired
 private NeedsDao needsDao;

 @Autowired
 private PlantDao plantDao;

@Transactional(readOnly = false)
 public PlantData savePlant(Long locationId, PlantData plantData) {
 Location location = findLocationById(locationId);

 Set<Needs> needs = needsDao.findAllByNeedsIn(plantData.getNeed());

 Plant plant = findOrCreatePlant(plantData.getPlantId());
 setPlantFields(plant, plantData);

 for(Needs need : needs) {
  need.getPlant().add(plant);
  plant.getNeeds().add(need);
 }

 Plant dbPlant = plantDao.save(plant);
 return new PlantData(dbPlant);
 }

 private void setPlantFields(Plant plant, PlantData plantData) {
  plant.setPlantId(plantData.getPlantId());
  plant.setPlantName(plantData.getPlantName());
  plant.setFlowerColor(plantData.getFlowerColor());
  plant.setGrowthSize(plantData.getGrowthSize());
 }

 private Plant findOrCreatePlant(Long plantId) {
  Plant plant;

  if (Objects.isNull(plantId)) {
   plant = new Plant();
  } else {
   plant = findPlantById(plantId);
  }
  return plant;
 }

 private Plant findPlantById(Long plantId) {
 return plantDao.findById(plantId).orElseThrow(() -> new NoSuchElementException("Plant with Id=" + plantId + "does " +
   "not exist"));
 }

 @Transactional(readOnly = true)
 private PlantData retrievePlantById(Long locationId, Long plantId) {
  findLocationById(locationId);
  Plant plant = findPlantById(plantId);

  if (plant.getLocation().getLocationId() != locationId) {
   throw new IllegalStateException("Plant with Id=" + plantId + "is not in location with Id=" + locationId);
  }
  return new PlantData(plant);
 }

 private Location findLocationById(Long locationId) {
 return locationDao.findById(locationId).orElseThrow(() -> new NoSuchElementException("Contributor with Id=" + locationId + "was not found"));
 }
}
