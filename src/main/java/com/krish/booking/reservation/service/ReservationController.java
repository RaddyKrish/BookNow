package com.krish.booking.reservation.service;

import com.krish.booking.reservation.model.Reservation;
import com.krish.booking.reservation.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @RequestMapping("/v1/availableSlots{date}")
    public List getAvilableTables (@PathVariable("date") @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) String date){
        return reservationService.getAvailableTable(date);
    }

    @RequestMapping("/v1/reservations{date}")
    public List<Reservation> getReservedTables (@PathVariable("date") @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
                                                            String date){
        return reservationService.getReservedTable(date);
    }

    @RequestMapping("/v1/reservations/{id}")
    public Reservation getReservation (@PathVariable String id){
        return reservationService.getReservation(id);
    }

    @RequestMapping(method= RequestMethod.POST, value="/v1/reservations")
    @ResponseBody
    public ResponseEntity<ResponseDTO> reserveTable(@RequestBody Reservation t, HttpServletResponse response){
       return reservationService.reserveTable(t, response);
    }

    @RequestMapping(method= RequestMethod.PUT, value="/v1/reservations/{id}")
    @ResponseBody
    public ResponseEntity<ResponseDTO> updateReservation(@RequestBody Reservation t,@PathVariable String id,
                                                         HttpServletResponse response){
       return reservationService.updateReservation(t, id, response);
    }

    @RequestMapping(method= RequestMethod.DELETE, value="/v1/reservations/{id}")
    @ResponseBody
    public ResponseEntity<ResponseDTO> removeReservation(@PathVariable String id,HttpServletResponse response){
        return reservationService.removeReservation(id, response);
    }

}
