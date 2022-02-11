package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Restaurants")
public class Restaurants {

	@Id
	@Column(length=1000000,name = "restaurantName" )
	private String restaurantName;
	
	@Column(name = "cashBalance")
	private Double cashBalance;
	
	@Column(length=1000000,name = "openingHours")
	private String openingHours;
	
	@Column()
	@OneToMany(fetch = FetchType.LAZY,cascade = javax.persistence.CascadeType.ALL)
    @JoinColumn( name = "restaurantName",referencedColumnName = "restaurantName")
	private List<Menu> menu = new ArrayList<Menu>();
	
	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public Double getCashBalance() {
		return cashBalance;
	}
	public void setCashBalance(Double cashBalance) {
		this.cashBalance = cashBalance;
	}
	public String getOpeningHours() {
		return openingHours;
	}
	public void setOpeningHours(String openingHours) {
		this.openingHours = openingHours;
	}
	public List<Menu> getMenu() {
		return menu;
	}
	public void setMenu(List<Menu> menu) {
		this.menu = menu;
	}
	@Override
	public String toString() {
		return "Restaurants [restaurantName=" + restaurantName + ", cashBalance=" + cashBalance + ", openingHours="
				+ openingHours + ", menu=" + menu + "]";
	}
	
	
}
