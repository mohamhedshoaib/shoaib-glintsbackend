package com.example.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "PurchaseHistory")
public class PurchaseHistory {

	private String dishName;
	
	@Id
	@Column(name = "purchaseId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long purchaseId;
	
	@Column(name="restaurantName",nullable = false,length=1000000)	 
	private String restaurantName;
	
	@Column(name = "transactionAmount")
	private Double transactionAmount;
	
	@Column(name = "transactionDate")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm a")
	private Date transactionDate;
	
	public String getDishName() {
		return dishName;
	}
	public void setDishName(String dishName) {
		this.dishName = dishName;
	}
	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public Double getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(Double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public Long getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(Long purchaseId) {
		this.purchaseId = purchaseId;
	}
	@Override
	public String toString() {
		return "PurchaseHistory [dishName=" + dishName + ", purchaseId=" + purchaseId + ", restaurantName="
				+ restaurantName + ", transactionAmount=" + transactionAmount + ", transactionDate=" + transactionDate
				+ "]";
	}
	
	

	
	
}
