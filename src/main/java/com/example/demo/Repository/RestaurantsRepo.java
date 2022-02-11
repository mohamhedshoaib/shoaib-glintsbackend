package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Restaurants;

@Repository
public interface RestaurantsRepo extends JpaRepository<Restaurants, String>{
	
//	@Query(nativeQuery = true, value = "SELECT * FROM Menu " + 
//			"	WHERE price<='10.50' ORDER BY price DESC Limit 0, 25")
//	public List<Restaurants> findAllByMenu_PriceLessThan();
//	
	
	
	@Query(nativeQuery = true,value = "Select Restaurants.*,Menu.* FROM "
			+ " (Restaurants LEFT JOIN Menu  "
			+ " on menu.restaurant_Name=Restaurants.restaurant_Name) WHERE menu.price <= :price")
	public List<Restaurants> listTopRestaurnatsY(@Param("price") Double price);

	@Query("FROM Restaurants AS rdt LEFT JOIN Menu AS cm on cm.restaurantName=rdt.restaurantName WHERE cm.price <= :price")    //This is using a named query method
	public List<Restaurants> listTopRestaurnatsZ(@Param("price") Double price);

	public List<Restaurants> findAllByRestaurantName(String restaurantName);

	@Query(nativeQuery = true,value = "SELECT * FROM RESTAURANTS where opening_hours like %:time%")
	public List<Restaurants> findAllByOpenHours(String time);

	public Restaurants findByRestaurantName(String restaurantName);


	

}
