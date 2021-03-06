//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.19 at 12:26:54 AM CEST 
//


package com.example.agentapp.xmlmodel.catalogue.vehicle_model;

import com.example.agentapp.xmlmodel.catalogue.vehicle_make.VehicleMake;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element ref="{http://rentacar.com/vehicle-make}vehicleMake"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "id",
    "value",
    "vehicleMake"
})
@XmlRootElement(name = "vehicleModel")
public class VehicleModel {

    protected long id;
    @XmlElement(required = true)
    protected String value;
    @XmlElement(namespace = "http://rentacar.com/vehicle-make", required = true)
    protected VehicleMake vehicleMake;

    /**
     * Gets the value of the id property.
     * 
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(long value) {
        this.id = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the vehicleMake property.
     * 
     * @return
     *     possible object is
     *     {@link VehicleMake }
     *     
     */
    public VehicleMake getVehicleMake() {
        return vehicleMake;
    }

    /**
     * Sets the value of the vehicleMake property.
     * 
     * @param value
     *     allowed object is
     *     {@link VehicleMake }
     *     
     */
    public void setVehicleMake(VehicleMake value) {
        this.vehicleMake = value;
    }

    @Override
    public String toString() {
        return "VehicleModel{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", vehicleMake=" + vehicleMake +
                '}';
    }

    public com.example.agentapp.model.catalogue.VehicleModel toModel(VehicleModel vehicleModel){
        com.example.agentapp.model.catalogue.VehicleModel vehicleModelModel = new com.example.agentapp.model.catalogue.VehicleModel();
        vehicleModelModel.setId(vehicleModel.getId());
        vehicleModelModel.setValue(vehicleModel.getValue());
        vehicleModelModel.setVehicleMake(vehicleModel.getVehicleMake().toModel(vehicleModel.getVehicleMake()));
        return vehicleModelModel;
    }

}
