package com.example.demo.service;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.RestaurantsRepo;
import com.example.demo.Repository.UsersRepo;
import com.example.demo.model.PurchaseHistory;
import com.example.demo.model.Users;
 

@Service
public class UserService {
	
	@Autowired
	UsersRepo uRepo;
	
	@Autowired
	RestaurantsRepo rRepo;
	
	@SuppressWarnings("unchecked")
	public List<Users> saveUsersToH2() {
		
		JSONParser parser = new JSONParser();
		
		List<Users> usersList = new ArrayList<Users>();
		
		try {
			
			//JSONArray jsonArray= (JSONArray) parser.parse(new FileReader("src/main/resources/users.json"));
			JSONArray jsonArray= (JSONArray) parser.parse(new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/users.json"))));
			
			
			
			for(int n = 0; n < jsonArray.size(); n++)
			{
			    JSONObject object = (JSONObject) jsonArray.get(n);
			   
			    Users usersObj = new Users();
			    
			    
			    try {
				    
			    	usersObj.setCashBalance((Double) object.get("cashBalance"));
			    } catch(ClassCastException e) {
			    	Long longObj = (Long) (object.get("cashBalance"));
			    	Double d = (double)longObj;
			    	usersObj.setCashBalance(d);
			    }
			    usersObj.setId((Long)object.get("id"));
			    usersObj.setName((String)object.get("name"));
			   			   			     
			    
			    List<PurchaseHistory> mainPurchaseList = new ArrayList<PurchaseHistory>();
			    
			    List<PurchaseHistory> purchaseList = (List<PurchaseHistory>) object.get("purchaseHistory");
			    
			    JSONArray purchaseJsonArray= (JSONArray)purchaseList;
			    
			    for(int j = 0; j < purchaseJsonArray.size(); j++) {
			    	
			    	JSONObject purchaseJsonObject = (JSONObject) purchaseJsonArray.get(j);
			    	
			    	PurchaseHistory purchaseObj = new PurchaseHistory();
			    	
			    	purchaseObj.setDishName((String)purchaseJsonObject.get("dishName"));
			    	purchaseObj.setRestaurantName((String)purchaseJsonObject.get("restaurantName"));
			    	
			    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm a", Locale.ENGLISH);

			    	String dateInString = (String)purchaseJsonObject.get("transactionDate");
			    	Date date = formatter.parse(dateInString);
			    	
			    	purchaseObj.setTransactionDate(date);
			    	
			    	try {
					    
			    		purchaseObj.setTransactionAmount((Double)purchaseJsonObject.get("transactionAmount"));
				    } catch(ClassCastException e) {
				    	Long longObj = (Long) (purchaseJsonObject.get("transactionAmount"));
				    	Double d = (double)longObj;
				    	purchaseObj.setTransactionAmount(d);
				    }
			    	
			    	mainPurchaseList.add(purchaseObj);
			    }
			    

			    
			    usersObj.setPurchaseHistory(mainPurchaseList);

			    usersList.add(usersObj);
			}
			
			System.out.println("size of users "+ usersList.size());
			
			uRepo.saveAll(usersList);
			
			return usersList;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Double decreaseUserCashBalance(Double menuPrice,Long id) {
		
		System.out.println("decreaseUserCashBalance, menuPrice ["+menuPrice+"], id ["+id+"]");
		Optional<Users> userObj = uRepo.findById(id);
		
		System.out.println(userObj.get().getCashBalance());
		
		Double decreasePrice = userObj.get().getCashBalance() - menuPrice;
		
		
		BigDecimal bd = BigDecimal.valueOf(decreasePrice);
	    bd = bd.setScale(2, RoundingMode.HALF_UP);
	    
		System.out.println("decreasePrice "+decreasePrice + " , bd.doubleValue() "+bd.doubleValue());
		
		
		userObj.get().setCashBalance(bd.doubleValue());
		
		uRepo.saveAndFlush(userObj.get());
		
		System.out.println("Exit decreaseUserCashBalance");
		
		return bd.doubleValue();
		
		
	}
}