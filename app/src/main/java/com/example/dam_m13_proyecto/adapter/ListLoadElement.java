package com.example.dam_m13_proyecto.adapter;

public class ListLoadElement {
    public String origin_address,origin_city,destination_address,destination_city,status,color;

    public ListLoadElement(String origin_address, String origin_city, String destination_address, String destination_city, String status, String color) {
        this.origin_address = origin_address;
        this.origin_city = origin_city;
        this.destination_address = destination_address;
        this.destination_city = destination_city;
        this.status = status;
        this.color = color;
    }

    public String getOrigin_address() {
        return origin_address;
    }

    public void setOrigin_address(String origin_address) {
        this.origin_address = origin_address;
    }

    public String getOrigin_city() {
        return origin_city;
    }

    public void setOrigin_city(String origin_city) {
        this.origin_city = origin_city;
    }

    public String getDestination_address() {
        return destination_address;
    }

    public void setDestination_address(String destination_address) {
        this.destination_address = destination_address;
    }

    public String getDestination_city() {
        return destination_city;
    }

    public void setDestination_city(String destination_city) {
        this.destination_city = destination_city;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
