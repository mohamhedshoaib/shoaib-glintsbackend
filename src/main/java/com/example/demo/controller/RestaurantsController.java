package com.example.demo.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Menu;
import com.example.demo.service.RestaurantsService;
 

@RestController

@RequestMapping("/glints")
public class RestaurantsController {
	
	@Autowired
	RestaurantsService resService;
	
	@RequestMapping(value="/listTopRestaurnats", method = RequestMethod.GET)
	public Map<String, List<Menu>>  listTopRestaurnats(@RequestParam String price,@RequestParam int topSize){
		return resService.listTopRestaurnats(price,topSize);
	}
	
	@RequestMapping(value="/searchRestaurants", method = RequestMethod.GET)
	public ResponseEntity<?>  searchRestaurants(@RequestParam(required = false) String restaurantName,
			@RequestParam(required = false) String dishName){
		return resService.searchRestaurants(restaurantName,dishName);
	}
	
	@RequestMapping(value="/searchOpenHours", method = RequestMethod.GET)
	public ResponseEntity<?>  searchOpenHours(@RequestParam(required = true) String day,@RequestParam(required = true)String startTime,
			@RequestParam(required = true)String endTime){
		return resService.searchOpenHours(day,startTime, endTime);
	}
	
	@RequestMapping(value="/purcahseDish", method = RequestMethod.POST)
	public ResponseEntity<?>  purcahseDish(@RequestParam(required = true) String dishName,@RequestParam(required = true) String restaurantName
			,@RequestParam(required = true) Long userId){
		return resService.purcahseDish(dishName,restaurantName,userId);
	}
}