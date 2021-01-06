package com.app.entity;

import java.time.LocalDate;
import java.util.Date;

public class Item {

    private long id;
    private String vendorCode;
    private String size;
    private String name;
    private String organization;
    private String city;
    private String department;
    private String inventoryCode;
    private LocalDate date;
    private String officeDepartment;
    private String epc;

    public Item() {
    }

    public Item(long id, String vendorCode, String size,
                String name, String organization, String city,
                String department, String inventoryCode, LocalDate date,
                String officeDepartment, String epc) {
        this.id = id;
        this.vendorCode = vendorCode;
        this.size = size;
        this.name = name;
        this.organization = organization;
        this.city = city;
        this.department = department;
        this.inventoryCode = inventoryCode;
        this.date = date;
        this.officeDepartment = officeDepartment;
        this.epc = epc;
    }

    public long getId() {
        return id;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public String getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public String getOrganization() {
        return organization;
    }

    public String getCity() {
        return city;
    }

    public String getDepartment() {
        return department;
    }

    public String getInventoryCode() {
        return inventoryCode;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getOfficeDepartment() {
        return officeDepartment;
    }

    public String getEpc() {
        return epc;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", vendorCode='" + vendorCode + '\'' +
                ", size=" + size +
                ", name='" + name + '\'' +
                ", organization='" + organization + '\'' +
                ", city='" + city + '\'' +
                ", department='" + department + '\'' +
                ", inventoryCode='" + inventoryCode + '\'' +
                ", date='" + date + '\'' +
                ", officeDepartment='" + officeDepartment + '\'' +
                ", epc=" + epc +
                '}';
    }
}
