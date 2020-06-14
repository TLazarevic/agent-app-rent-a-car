package com.example.agentapp.service;

import com.example.agentapp.model.*;
import com.example.agentapp.repository.CityRepository;
import com.example.agentapp.repository.LocationRepository;
import com.example.agentapp.repository.StateRepository;
import com.example.agentapp.repository.StreetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.SortedSet;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StreetRepository streetRepository;

    public Location get(Long locationId) {
        Location location = null;
        try{
            if(locationRepository.findById(locationId).isPresent()){
                location = locationRepository.findById(locationId).get();
            }
        }
        catch (Exception e){

        }
        return location;
    }

    public Notification delete(Long locationId) {
        Notification notification = new Notification("Deleting location failed.");
        try{
            if(locationRepository.findById(locationId).isPresent()){
                locationRepository.deleteById(locationId);
                notification.setText("Deleted location.");
            }
            else{
                notification.setText("Location id does not exist.");
            }
        }
        catch (Exception e){

        }
        return notification;
    }

    public Location create(Location location) {
        Location locationNew = null;
        try{
            if(locationRepository.findByStateAndCityAndStreet(location.getState().getValue(), location.getCity().getValue(), location.getStreet().getValue()) != null){
                locationNew = locationRepository.findByStateAndCityAndStreet(location.getState().getValue(), location.getCity().getValue(), location.getStreet().getValue());
            }
            else{
                State stateObject = stateRepository.findByValue(location.getState().getValue());
                if (stateObject == null){
                    stateObject = stateRepository.save(new State(location.getState().getValue()));
                }
                City cityObject = cityRepository.findByValue(location.getCity().getValue());
                if (cityObject == null){
                    cityObject = cityRepository.save(new City(location.getCity().getValue()));
                }
                Street streetObject = streetRepository.findByValue(location.getStreet().getValue());
                if (streetObject == null){
                    streetObject = streetRepository.save(new Street(location.getStreet().getValue()));
                }
                locationNew = locationRepository.save(new Location(stateObject, cityObject, streetObject));
            }
        }
        catch (Exception e){

        }
        return locationNew;
    }

    public Long find(String state, String city, String street) {
        Long locationId = null;
        try{
            State stateObject = stateRepository.findByValue(state);
            City cityObject = cityRepository.findByValue(city);
            Street streetObject = streetRepository.findByValue(street);

            if (stateObject == null|| cityObject == null|| streetObject == null){
                locationId = null;
            }
            else{
                Location location = locationRepository.findByStateAndCityAndStreet(stateObject.getValue(), cityObject.getValue(), streetObject.getValue());
                if (location != null){
                    locationId = location.getId();
                }
            }
        }
        catch(Exception e){

        }
        return locationId;
    }

    public List<Location> getAll() {
        List<Location> locations = null;
        try{
            locations = locationRepository.findAll();
        }
        catch (Exception e){

        }

        return locations;
    }

    public SortedSet<City> getCitiesByState(Long stateId) {
        return this.locationRepository.getCitiesByState(stateId);
    }
}