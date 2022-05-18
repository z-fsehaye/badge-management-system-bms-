package com.membership.controller;

import com.membership.domain.Location;
import com.membership.domain.TimeSlot;
import com.membership.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    private LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public List<Location> findAll() {
        return locationService.findAll();
    }

    @GetMapping("/{id}")
    public Location findById(@PathVariable(name = "id") long id){
        return locationService.findById(id);
    }

    @PostMapping
    public Location addNewLocation(@RequestBody Location location){
        return locationService.save(location);
    }

    @PutMapping("/{id}")
    public Location updateLocation(@PathVariable(name = "id") long id, @RequestBody Location location){
        return locationService.update(id, location);
    }

    @DeleteMapping("/{id}")
    public String deleteLocation(@PathVariable(name = "id") long id){
        locationService.deleteById(id);
        return "Successfully deleted location.";
    }

    @PatchMapping("/{id}/timeslot")
    public Location addTimeSlotForLocation(@PathVariable(name = "id") Long id, @RequestBody TimeSlot timeSlot){
        return locationService.addTimeSlotForLocation(id, timeSlot);
    }
}
