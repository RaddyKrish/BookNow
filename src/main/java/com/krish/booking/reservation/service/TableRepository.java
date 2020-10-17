package com.krish.booking.reservation.service;

import com.krish.booking.reservation.model.RestaurantTable;
import org.springframework.data.repository.CrudRepository;

public interface TableRepository extends CrudRepository <RestaurantTable,String>{

}
