package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Menu;

@Repository
public interface MenuRepo extends JpaRepository<Menu, Long>{
	
	
	@Query(nativeQuery = true, value = "SELECT * FROM Menu " + 
			"	WHERE price<=:price ORDER BY price DESC")
	public List<Menu> findAllByPriceLessThan(@Param("price") Double price);

	public List<Menu> findAllByDishName(String dishName);


	public boolean existsByRestaurantNameAndDishName(String restaurantName, String dishName);

	public Menu findByRestaurantNameAndDishName(String restaurantName, String dishName);

	
//	/Limit 0, 25
	
}
