package com.krish.booking.reservation.service;

import com.krish.booking.reservation.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public  class ReservationRepositoryImpl{

    @Autowired
    JdbcTemplate template;

    private static final String deletebyID = "DELETE FROM reservation res WHERE res.id = ?";

    private static final String updateReservationByID = "UPDATE reservation res SET name=?,reservation_Date=?," +
            "table_Name=?,reservation_Time=?,contact=? WHERE res.id = ?";

    public void deletebyID(Long id){
             template.update(deletebyID,new Object []{id});
    }

    public void updateReservationByID(Reservation updatedTable, Long id){
        template.update(updateReservationByID,new Object[]{updatedTable.getName(),updatedTable.getReservationDate(),
                updatedTable.getTableName(),updatedTable.getReservationTime(),updatedTable.getContact(),id});
    }

}
