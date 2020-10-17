package com.krish.booking.reservation.service;

import com.krish.booking.reservation.model.Reservation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation,String> {

   @Query(
            value = "SELECT * FROM reservation WHERE table_name=? and reservation_date = ?",
            nativeQuery = true)
    List<Reservation> findByNameByDate(String name, String dt);


    @Query(
            value = "SELECT * FROM reservation WHERE id=?",
            nativeQuery = true)
    Reservation findByIDCustom(Long id);

}
