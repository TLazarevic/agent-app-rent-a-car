package com.example.agentapp.service;

import com.example.agentapp.dto.BundleDTO;
import com.example.agentapp.dto.RequestDTO;
import com.example.agentapp.dto.RequestForFrontDTO;
import com.example.agentapp.dto.VehicleMainViewDTO;
import com.example.agentapp.dto.user.UserDTO;
import com.example.agentapp.model.Bundle;
import com.example.agentapp.model.Request;
import com.example.agentapp.model.enums.Status;
import com.example.agentapp.repository.BundleRepository;
import com.example.agentapp.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

@Service
public class RequestService {

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    BundleRepository bundleRepository;

    public boolean areDatesValid(LocalDateTime startDate, LocalDateTime endDate) {
        if(startDate==null || endDate==null)
            return false;
        if (endDate.compareTo(startDate) >= 0 &&
                startDate.compareTo(LocalDateTime.now()) >= 0)
            return true;
        else {
            System.err.println("Invalid dates");
            return false;
        }
    }

    public boolean isBundleValid(Bundle bundle) {
        List<Request> requests = bundle.getRequests();
        System.out.println(requests);
        if (requests.size() <= 1) {
            System.err.println("Bundle cannot contain one vehicle only");
            return false;
        }
        Long ownerId = requests.get(0).getOwnerId();
        for (Request request : requests) {
            if (!request.getOwnerId().equals(ownerId)) {
                System.out.println(request.getOwnerId());
                System.err.println("All bundle members must have the same owner");
                return false;
            }

        }
        return true;
    }

    public List<Request> findAll() {
        return this.requestRepository.findAll();
    }

    public Request update(Long id, Request newRequest) {
        Request request = requestRepository.findById(id).get();

        if (request != null) {
            request = newRequest;
            requestRepository.save(request);
            return request;
        }
        return null;
    }

    public boolean addRequest(RequestDTO requests) {
        //-----------------checks------------------

        for (Bundle bundle : requests.getBundles()) {
            if (!isBundleValid(bundle))
                return false;
            for (Request request : bundle.getRequests()) {
                if (!(areDatesValid(request.getStartDate(), request.getEndDate()) && request.getUserId() != null))
                    return false;
            }
        }
        for (Request request : requests.getRequests()) {
            if (!(areDatesValid(request.getStartDate(), request.getEndDate()) && request.getUserId() != null))
                return false;
        }

        //-------------------------------------------

        for (Bundle bundle : requests.getBundles()) {
            Bundle newBundle = bundleRepository.saveAndFlush(new Bundle(requests.getRequests())); //contains Id
            for (Request request : bundle.getRequests()) {
                request.setBundle(newBundle);
                request.setStatus(Status.PENDING);
                requestRepository.saveAndFlush(request);
            }

        }
        for (Request request : requests.getRequests()) {
            request.setStatus(Status.PENDING);
            requestRepository.saveAndFlush(request);
        }
        return true;
    }

    public Boolean delete(Long id) {
        Request request = requestRepository.findById(id).get();
        if (request != null) {
            requestRepository.delete(request);
            return true;
        }
        return false;
    }

    public boolean addPhysicalRenting(Request request) {

        if(request.getVehicleId()==null)
            return false;

        LocalDateTime startdate = request.getStartDate();
        LocalDateTime enddate = request.getEndDate();

        if (areDatesValid(startdate, enddate)) {

            List<Request> overlappingReqs = requestRepository.overlapingRequests(startdate, enddate, request.getVehicleId());
            System.out.println(overlappingReqs);

            for (Request req : overlappingReqs) {
                if (req.getBundle() == null) {
                    //Cancel requests which are not part of a bundle
                    if (req.getStatus() != Status.RESERVED) {
                        req.setStatus(Status.CANCELLED);
                        requestRepository.save(req);
                    } else return false; //conflict
                } else {
                    //Cancel members of a bundle for each conflicting request that is in a bundle
                    List<Request> bundleMembers = requestRepository.bundleMembers(req.getBundle());
                    for (Request reqInBundle : bundleMembers) {
                        if (reqInBundle.getStatus() != Status.RESERVED) {
                            reqInBundle.setStatus(Status.CANCELLED);
                            requestRepository.save(req);
                        } else return false; //conflict
                    }
                }

            }

            request.setStatus(Status.RESERVED);
            request.setBundle(null);
            requestRepository.save(request);
            return true;
        } else return false;
    }

    public List<Request> getAllRequestsByOwner (Long ownerId) {
        List<Request> requestsList = requestRepository.findAll();
        List<Request> newRequestsList = new ArrayList<>();
        for (Request request : requestsList) {
            if (request.getOwnerId().equals(ownerId)) {
                newRequestsList.add(request);
            }
        }
        return newRequestsList;
    }

    public List<Request> getAllRequestsByUser (Long userId) {
        List<Request> requestsList = requestRepository.findAll();
        List<Request> newRequestsList = new ArrayList<>();
        for (Request request : requestsList) {
            if (request.getUserId().equals(userId)) {
                newRequestsList.add(request);
            }
        }
        return newRequestsList;
    }
    //TYPE OF USER - PARAMETER FOR CHOOSING OWNER USERNAME OR BUYER USERNAME
    //USE 0 FOR OWNER USERNAME
    //USE 1 FOR BUYER USERNAME
    public List<RequestForFrontDTO> getDtoList (int typeOfUser, List<Request> requestsList, List<UserDTO> userDTOList, List<VehicleMainViewDTO> vehicleList) {
        List<RequestForFrontDTO> newDTOList = new ArrayList<>();
        for (Request request : requestsList) {
            RequestForFrontDTO dto = new RequestForFrontDTO();
            dto.setId(request.getId());
            dto.setTotalCost(request.getTotalCost());
            dto.setStartDate(request.getStartDate());
            dto.setEndDate(request.getEndDate());
            dto.setStatus(request.getStatus());
            //SETTING USERNAME FOR REQUEST DTO
            for (UserDTO user : userDTOList) {
                if (typeOfUser == 0) {
                    if (user.getId() == request.getOwnerId()) {
                        dto.setUsername(user.getUsername());
                        break;
                    }
                } else {
                    if (user.getId() == request.getUserId()) {
                        dto.setUsername(user.getUsername());
                        break;
                    }
                }
            }

            //SETTING VEHICLE MAKE AND MODEL FOR REQUEST DTO
            for (VehicleMainViewDTO vehicle : vehicleList) {
                if (vehicle.getId().equals(request.getVehicleId())) {
                    dto.setMakePlusModel(vehicle.getMake() + " " + vehicle.getModel());
                    break;
                }
            }

            dto.setBundleId(request.getBundle().getId());
            newDTOList.add(dto);
        }

        return newDTOList;
    }

    public List<BundleDTO> getBundles(List<RequestForFrontDTO> requestList) {
        TreeSet<Long> bundleIdSet = new TreeSet<>();
        List<BundleDTO> bundleList = new ArrayList<>();
        for (RequestForFrontDTO request : requestList) {
            bundleIdSet.add(request.getBundleId());
        }

        for (Long bundleId : bundleIdSet) {
            BundleDTO dto = new BundleDTO();
            for (RequestForFrontDTO request : requestList) {
                dto.setId(bundleId);
                if (request.getBundleId().equals(bundleId)) {
                    dto.getRequestsList().add(request);
                    dto.setUsername(request.getUsername());
                }
                float totalPrice = 0;
                for (RequestForFrontDTO request1 : dto.getRequestsList()) {
                    totalPrice += request1.getTotalCost();
                }
                dto.setTotalCost(totalPrice);
            }
            bundleList.add(dto);
        }
        return bundleList;
    }
}