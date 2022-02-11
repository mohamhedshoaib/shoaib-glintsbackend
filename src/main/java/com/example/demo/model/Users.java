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
@Table(name = "Users")
public class Users {

	@Id
	@Column(name = "id")
	private Long id;
	
	@Column(name = "cashBalance")
	private Double cashBalance;
	
	@Column(name = "name")
	private String name;
	
	//@OneToMany(targetEntity=PurchaseHistory.class, mappedBy="dishName", fetch=FetchType.EAGER)
	
	@Column()
	@OneToMany(fetch = FetchType.LAZY,cascade = javax.persistence.CascadeType.ALL)
    @JoinColumn( name = "userId",referencedColumnName = "id")
   private List<PurchaseHistory> purchaseHistory = new ArrayList<PurchaseHistory>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getCashBalance() {
		return cashBalance;
	}

	public void setCashBalance(Double cashBalance) {
		this.cashBalance = cashBalance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PurchaseHistory> getPurchaseHistory() {
		return purchaseHistory;
	}

	public void setPurchaseHistory(List<PurchaseHistory> purchaseHistory) {
		this.purchaseHistory = purchaseHistory;
	}

	@Override
	public String toString() {
		return "Users [id=" + id + ", cashBalance=" + cashBalance + ", name=" + name + ", purchaseHistory="
				+ purchaseHistory + "]";
	}
	
	
	
	
	
	}
