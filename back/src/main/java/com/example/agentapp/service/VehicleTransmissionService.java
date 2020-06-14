package com.example.agentapp.service;

import com.example.agentapp.model.VehicleTransmission;
import com.example.agentapp.repository.VehicleTransmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class VehicleTransmissionService {
    @Autowired
    private VehicleTransmissionRepository vehicleTransmissionRepository;

    public void addNew(VehicleTransmission vehicleTransmission) throws Exception {
        if(exist(vehicleTransmission)) {
            throw new Exception("Already exist");
        } else {
            vehicleTransmissionRepository.save(vehicleTransmission);
        }
    }

    public List<VehicleTransmission> getAll() {
        return vehicleTransmissionRepository.findAll();
    }


    private boolean exist(VehicleTransmission vehicleTransmission) {
        List<VehicleTransmission> vehicleTransmissionList = vehicleTransmissionRepository.findAll();
        for (VehicleTransmission vt: vehicleTransmissionList
        ) {
            if(vt.getValue().toLowerCase().equals(vehicleTransmission.getValue().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public VehicleTransmission findOne(String id) throws Exception{
        VehicleTransmission vehicleTransmission = null;
        try {
            vehicleTransmission = vehicleTransmissionRepository.findOneById(Long.parseLong(id));
        } catch (EntityNotFoundException e) {
            throw new Exception("Can't find vehicle transmission with id = " + id);
        }
        return vehicleTransmission;
    }

    public void deleteOne(String id) throws Exception {
        try {
            findOne(id);
        } catch (EntityNotFoundException e) {
            throw new Exception("Can't find vehicle transmission with id = " + id);
        }
        vehicleTransmissionRepository.delete(findOne(id));
    }

    public void change(String id, VehicleTransmission vehicleTransmission1) throws Exception{
        try {
            VehicleTransmission vehicleTransmission = findOne(id);
            vehicleTransmission.setValue(vehicleTransmission1.getValue());
            vehicleTransmissionRepository.save(vehicleTransmission);
        } catch (EntityNotFoundException e) {
            throw new Exception("Can't find vehicle transmission with id = " + id);
        }
    }
}