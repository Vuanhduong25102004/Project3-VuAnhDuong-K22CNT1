package com.model;

import jakarta.persistence.*;

@Entity
@Table(name="Vad_services")
public class VadServices {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "Vadname")
	private String name;
	
	@Column(name = "Vaddescription")
	private String description;
	
	@Column(name = "Vadprice")
	private double price;
	
	public VadServices() {}
	
	public VadServices(String name, String description, double price) {
	    this.name = name;
	    this.description = description;
	    this.price = price;
	}
	
	// Getters và Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
}
