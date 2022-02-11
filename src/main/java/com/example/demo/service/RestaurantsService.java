package com.example.demo.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.MenuRepo;
import com.example.demo.Repository.RestaurantsRepo;
import com.example.demo.model.Menu;
import com.example.demo.model.Restaurants;

@Service
public class RestaurantsService {

	@Autowired
	RestaurantsRepo repo;

	@Autowired
	MenuRepo mRepo;
	
	
	@Autowired
	UserService usersService;
	
	HttpHeaders headers = new HttpHeaders();



	public List<Restaurants> saveRestaurnatsToH2() {

		JSONParser parser = new JSONParser();

		List<Restaurants> restaurantsList = new ArrayList<Restaurants>();

		try {

			//JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("src/main/resources/restaurants.json"));
			JSONArray jsonArray= (JSONArray) parser.parse(new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/restaurants.json"))));
			

			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject object = (JSONObject) jsonArray.get(i);

				Restaurants resObj = new Restaurants();

				try {

					resObj.setCashBalance((Double) object.get("cashBalance"));
				} catch (ClassCastException e) {
					Long longObj = (Long) (object.get("cashBalance"));
					Double d = (double) longObj;
					resObj.setCashBalance(d);
				}

				resObj.setOpeningHours((String) object.get("openingHours"));
				resObj.setRestaurantName((String) object.get("restaurantName"));

				List<Menu> mainMenuList = new ArrayList<Menu>();

				List<Menu> menuList = (List<Menu>) object.get("menu");

				JSONArray menuJsonArray = (JSONArray) menuList;

				for (int j = 0; j < menuJsonArray.size(); j++) {

					JSONObject menuJsonObject = (JSONObject) menuJsonArray.get(j);

					Menu menuObj = new Menu();

					menuObj.setDishName((String) menuJsonObject.get("dishName"));

					try {

						menuObj.setPrice((Double) menuJsonObject.get("price"));
					} catch (ClassCastException e) {
						Long longObj = (Long) (menuJsonObject.get("price"));
						Double d = (double) longObj;
						menuObj.setPrice(d);
					}

					mainMenuList.add(menuObj);
				}

				resObj.setMenu(mainMenuList);

				restaurantsList.add(resObj);
			}

			System.out.println("size of listRestaurants " + restaurantsList.size());

			repo.saveAll(restaurantsList);

			return restaurantsList;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Map<String, List<Menu>> listTopRestaurnats(String price,int topSize) {

		System.out.println("In listTopRestaurnats ");

		Double priceFromUi = Double.parseDouble(price);
		
		System.out.println("priceFromUi "+priceFromUi);
		
		List<Menu> restaurantsList = mRepo.findAllByPriceLessThan(priceFromUi);

		System.out.println("THe top Restaurnats within price are " + restaurantsList.size());
		
		Map<String, List<Menu>> result =
				restaurantsList.stream().collect(Collectors.groupingBy(Menu::getRestaurantName,Collectors.toList()));
		
		System.out.println("THe Group By Restaurnats within price are " + result.values().size());
		
		//result.entrySet().removeIf(e-> ((Map) e).values().size()<=5);
		
		Iterator<Map.Entry<String, List<Menu>> >
        iterator = result.entrySet().iterator();

    // Iterate over the HashMap
    while (iterator.hasNext()) {

        // Get the entry at this iteration
        Map.Entry<String, List<Menu>>
            entry
            = iterator.next();

        // Check if this value is the required value
        if (entry.getValue().size() <= topSize) {

            // Remove this entry from HashMap
            iterator.remove();
        }
    }
		
		System.out.println("THe top Restaurnats within price who have morethan "+topSize+" +are " + result.values().size());
		
		//result.entrySet().stream().forEach(e -> e.getValue() <= 3 ->result.values().remove(e));
		

		System.out.println("Exit listTopRestaurnats ");

		return result;

	}

	public List<Restaurants> listTopRestaurnatsY() {

		System.out.println("In listTopRestaurnats ");

		List<Restaurants> restaurantsList = repo.listTopRestaurnatsY((Double) 20.00);
		System.out.println("THe top 25 Restaurnats within price are "+restaurantsList.size());

		// System.out.println(repo.findTop10ByMenu_PriceLessThan((Double)15.00));

		return restaurantsList;

	}

	public List<Restaurants> listTopRestaurnatsZ() {

		System.out.println("In listTopRestaurnats ");

		List<Restaurants> restaurantsList = repo.listTopRestaurnatsZ((Double) 15.00);
		System.out.println("THe top 25 Restaurnats within price are "+restaurantsList.size());

		// System.out.println(repo.findTop10ByMenu_PriceLessThan((Double)15.00));

		return restaurantsList;

	}

	public ResponseEntity<?> searchRestaurants(String restaurantName, String dishName) {
		
		if(restaurantName != null && !restaurantName.isEmpty()) {
			List<Restaurants> resList = repo.findAllByRestaurantName(restaurantName);
			return ResponseEntity.status(HttpStatus.ACCEPTED).headers(headers)
					.body(resList);
		}
		else if(dishName != null && !dishName.isEmpty()) {
			List<Menu> dishList = mRepo.findAllByDishName(dishName);
			return ResponseEntity.status(HttpStatus.ACCEPTED).headers(headers)
					.body(dishList);
		}
		else {
			return null;
		}
	
	}
	
	
	public ResponseEntity<?> searchOpenHours(String Day,String startTime, String endTime) {
		
			//Weds 11:45 am - 4:45 pm 
			String time = Day + " " + startTime + " - " + endTime;
			System.out.println(time);
			List<Restaurants> resList = repo.findAllByOpenHours(time);
			
			return ResponseEntity.status(HttpStatus.ACCEPTED).headers(headers)
					.body(resList);
		
		
	
	}

	public ResponseEntity<?> purcahseDish(String dishName, String restaurantName,Long id) {
		
		System.out.println("In purcahseDish , dishName["+dishName+"] , restaurantName ["+restaurantName+"]");
		
		if(mRepo.existsByRestaurantNameAndDishName(restaurantName,dishName)) {
			
			System.out.println("Increase Cash blanace of restaurnat by getting the price");
			
			Menu menuObj = new Menu();
			
			menuObj =  mRepo.findByRestaurantNameAndDishName(restaurantName,dishName);
			
			menuObj.getPrice();
			
			System.out.println("menuObj.getPrice() "+menuObj.getPrice());
			
			Restaurants resObj = new Restaurants();
			
			resObj = repo.findByRestaurantName(restaurantName);
			
			System.out.println("restaurant Cash Balance "+resObj.getCashBalance());
			
			Double increaseValue = resObj.getCashBalance() + menuObj.getPrice();
			
			resObj.setCashBalance(increaseValue);
			
			System.out.println("Increase value of Restaurnat by ["+menuObj.getPrice()+"] Cash Balance "+resObj.getCashBalance());
			
			repo.saveAndFlush(resObj);
			
			System.out.println("Invoke user service and decrease user cash balance");
			
			Double decreasedPrice = usersService.decreaseUserCashBalance(menuObj.getPrice(),id);
			
			System.out.println("successfully decreased the cash balance of user ");
			
			return ResponseEntity.status(HttpStatus.ACCEPTED).headers(headers)
					.body("Ordered item ["+dishName+"] from ["+restaurantName+"] ,  The user "+id+"'s current cash Balance is "+decreasedPrice+"");
		}
		
		return null;
	}

}