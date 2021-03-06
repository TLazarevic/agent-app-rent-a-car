package com.example.agentapp.model.vehicle;


import com.example.agentapp.model.builders.VehicleBuilder;

import javax.persistence.*;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.GregorianCalendar;

@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(name="mileage", nullable = false)
    private int mileage;

    @Column(name="mileage_limit", nullable = false)
    private int mileageLimit;

    @Column(name="collision_protection", nullable = false)
    private boolean collisionProtection;

    @Column(name="children_seats", nullable = false)
    private int childrenSeats;

    @OneToOne
    @JoinColumn(name="image", referencedColumnName = "id", nullable = true)
    private VehicleImage image;

    @Column(name="start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name="end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name="fuel_type_id", nullable = false)
    private Long fuelTypeId;

    @Column(name="make_id", nullable = false)
    private Long makeId;

    @Column(name="model_id", nullable = false)
    private Long modelId;

    @Column(name="style_id", nullable = false)
    private Long styleId;

    @Column(name="transmission_id", nullable = false)
    private Long transmissionId;

    @Column(name="location_id", nullable = false)
    private Long locationId;

    @Column(name="user_id", nullable = false)
    private Long userId;

    public Vehicle(Long id, int mileage, int mileageLimit, boolean collisionProtection, int childrenSeats, LocalDateTime startDate, LocalDateTime endDate, Long fuelTypeId, Long makeId, Long modelId, Long styleId, Long transmissionId, Long locationId, Long userId, VehicleImage vehicleImage) {
        this.id = id;
        this.mileage = mileage;
        this.mileageLimit = mileageLimit;
        this.collisionProtection = collisionProtection;
        this.childrenSeats = childrenSeats;
        this.startDate = startDate;
        this.endDate = endDate;
        this.fuelTypeId = fuelTypeId;
        this.makeId = makeId;
        this.modelId = modelId;
        this.styleId = styleId;
        this.transmissionId = transmissionId;
        this.locationId = locationId;
        this.userId = userId;
        this.image = vehicleImage;
    }

    public Vehicle() {
    }

    public static VehicleBuilder builder(){
        return new VehicleBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public int getMileageLimit() {
        return mileageLimit;
    }

    public void setMileageLimit(int mileageLimit) {
        this.mileageLimit = mileageLimit;
    }

    public int getChildrenSeats() {
        return childrenSeats;
    }

    public void setChildrenSeats(int childrenSeats) {
        this.childrenSeats = childrenSeats;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setCollisionProtection(boolean collisionProtection) {
        this.collisionProtection = collisionProtection;
    }

    public boolean getCollisionProtection() {
        return collisionProtection;
    }

    public VehicleImage getImage() {
        return image;
    }

    public void setImage(VehicleImage image) {
        this.image = image;
    }

    public Long getFuelTypeId() {
        return fuelTypeId;
    }

    public void setFuelTypeId(Long fuelTypeId) {
        this.fuelTypeId = fuelTypeId;
    }

    public Long getMakeId() {
        return makeId;
    }

    public void setMakeId(Long makeId) {
        this.makeId = makeId;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public Long getStyleId() {
        return styleId;
    }

    public void setStyleId(Long styleId) {
        this.styleId = styleId;
    }

    public Long getTransmissionId() {
        return transmissionId;
    }

    public void setTransmissionId(Long transmissionId) {
        this.transmissionId = transmissionId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }



    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", mileage=" + mileage +
                ", mileageLimit=" + mileageLimit +
                ", collisionProtection=" + collisionProtection +
                ", childrenSeats=" + childrenSeats +
                ", picturePath='" + image.getName() + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", fuelTypeId=" + fuelTypeId +
                ", makeId=" + makeId +
                ", modelId=" + modelId +
                ", styleId=" + styleId +
                ", transmissionId=" + transmissionId +
                ", locationId=" + locationId +
                ", userId=" + userId +
                '}';
    }

    public com.example.agentapp.xmlmodel.vehicle.Vehicle toXML(Vehicle vehicle) throws DatatypeConfigurationException {

        com.example.agentapp.xmlmodel.vehicle.Vehicle xmlModel = new com.example.agentapp.xmlmodel.vehicle.Vehicle();

        com.example.agentapp.xmlmodel.vehicle.vehicle_image.VehicleImage image = new com.example.agentapp.xmlmodel.vehicle.vehicle_image.VehicleImage();
        image.setId(vehicle.getImage().getId());
        image.setName(vehicle.getImage().getName());
        image.setType(vehicle.getImage().getType());
        image.setPicByte(vehicle.getImage().getPicByte());

        xmlModel.setId(vehicle.getId());
        xmlModel.setChildrenSeats(vehicle.getChildrenSeats());
        xmlModel.setMileage(vehicle.getMileage());
        xmlModel.setMileageLimit(vehicle.getMileageLimit());

        LocalDate date = vehicle.getStartDate().toLocalDate();
        GregorianCalendar gcal = GregorianCalendar.from(date.atStartOfDay(ZoneId.systemDefault()));
        XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);

        xmlModel.setStartDate(xcal);

        date = vehicle.getEndDate().toLocalDate();
        gcal = GregorianCalendar.from(date.atStartOfDay(ZoneId.systemDefault()));
        xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);

        System.err.println(xcal);

        xmlModel.setEndDate(xcal);

        xmlModel.setVehicleImage(image);
        xmlModel.setCollisionProtection(vehicle.collisionProtection);

        xmlModel.setLocationId(vehicle.getLocationId());
        xmlModel.setMakeId(vehicle.getMakeId());
        xmlModel.setStyleId(vehicle.getStyleId());
        xmlModel.setFuelTypeId(vehicle.getFuelTypeId());
        xmlModel.setModelId(vehicle.getModelId());
        xmlModel.setUserId(vehicle.getUserId());
        xmlModel.setTransmissionId(vehicle.getTransmissionId());

        return xmlModel;
    }
}
