package com.krish.booking.reservation.service;

import com.krish.booking.reservation.model.Reservation;
import com.krish.booking.reservation.model.RestaurantTable;
import com.krish.booking.reservation.dto.AvailabilityDTO;
import com.krish.booking.reservation.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ReservationService {
    @Autowired
    private TableRepository tableRepo;
    @Autowired
    private ReservationRepository reserveRepo;
    @Autowired
    private ResponseDTO resDTO;
    @Autowired
    private ReservationRepositoryImpl resRepoImpl;
    ResponseEntity resEntity;
    @Autowired
    private AvailabilityDTO availabilityDTO;

    public List<AvailabilityDTO> getAvailableTable (String dt){
          List<RestaurantTable> table = new ArrayList<>();
        tableRepo.findAll().forEach(table ::add);

        List<AvailabilityDTO> finalList = new ArrayList<>();
        List<String> availableTime = new ArrayList<>();
        availableTime.add("11AM-1PM");
        availableTime.add("1PM-3PM");
        availableTime.add("3PM-5PM");
        availableTime.add("5PM-7PM");
        List<Reservation> reserved = getReservedTable(dt);

            for (RestaurantTable counter :table) {
                for(String l: availableTime){
                    availabilityDTO.setTableName(counter.getTableName());
                    availabilityDTO.setAvailableDate(dt);
                    availabilityDTO.setAvailableTime(l);
                    finalList.add(availabilityDTO);
                    for (Reservation rs: reserved){
                       if (rs.getTableName().equals(counter.getTableName())&& rs.getReservationTime().equals(l)){
                            finalList.remove(availabilityDTO);
                        }
                    }
                }

            }
          return finalList;
    }

    public List<Reservation> getReservedTable (String inDate){
        List<RestaurantTable> table = new ArrayList<>();
        tableRepo.findAll().forEach(table ::add);
        List<Reservation> reserved = new ArrayList<>();
        for (RestaurantTable counter : table) {
                reserveRepo.findByNameByDate(counter.getTableName(), inDate).forEach(reserved::add);
            }
              return reserved;
    }

    public Reservation getReservation(String id) {
          return reserveRepo.findByIDCustom(Long.parseLong(id));
    }

    public ResponseEntity<ResponseDTO> reserveTable(Reservation reserveTable, HttpServletResponse response)
    {
        try {
            if (checkIfSafeToBook(reserveTable)) {
                Reservation res = reserveRepo.save(reserveTable);
                resDTO.setId(res.getId().toString());
                resDTO.setStatus("BOOKED");
                resEntity = new ResponseEntity(resDTO, HttpStatus.ACCEPTED);
            } else {
                resDTO.setId("0");
                resDTO.setStatus("UNAVAILABLE");
                resEntity = new ResponseEntity(resDTO, HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }catch (OptimisticLockingFailureException ex){
            resEntity = new ResponseEntity(HttpStatus.LOCKED);
        } catch(Exception e){
            resDTO.setId("0");
            resDTO.setStatus("UNAVAILABLE");
            resEntity = new ResponseEntity(resDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return  resEntity;
    }

    /** A method to evaluate conditions to confirm is if okay to book or modify reservation
     * No more than 20 tables for a give time.
     * Does additional validation by calling checkIfStoreOpen method.
     */
    public boolean checkIfSafeToBook(Reservation reserveTable){
        Boolean isSafe=false;
        String timeStr = reserveTable.getReservationTime();
        int counter = 0;
        String resTime = reserveTable.getReservationTime();
        List<Reservation> reserved = getReservedTable(reserveTable.getReservationDate());
        boolean isDuplicate = false;
        for (Reservation rs : reserved) {
            if (rs.getReservationTime().equals(resTime) && rs.getTableName().equals(reserveTable.getTableName())
                    && (rs.getReservationDate().equals(reserveTable.getReservationDate()))) {
                isDuplicate = true;
                break;
            } else if (rs.getReservationTime().equals(resTime)) {
                counter++;
            }
        }

        if (counter < 20 && Boolean.FALSE.equals(isDuplicate) && Boolean.TRUE.equals(checkIfStoreOpen(timeStr))) {
            isSafe = true;
        } else{
            isSafe =false;
        }
        return isSafe;
    }

    /** A method to evaluate the below conditions to confirm is if okay to book or modify reservation
     * Reservation should be for maximum of 2 hours.
     * Booking within restaurant open hours*/
    public boolean checkIfStoreOpen(String timeStr){
        String[] times = timeStr.split("-");
        String startTimeStr = times[0];
        String endTimeStr = times[1];
        Boolean isStoreOpen = true;
        Long maxHours=new Long(2);
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("h[:mm]a");
        LocalTime startTime = LocalTime.parse(startTimeStr,parser);
        LocalTime endTime = LocalTime.parse(endTimeStr,parser);
        if (startTime.isBefore(LocalTime.parse("11:00"))){
            isStoreOpen = false;
        } else if(endTime.isAfter(LocalTime.parse("19:00"))){
            isStoreOpen = false;
        } else if(0 !=(endTime.minusHours(maxHours)).compareTo(startTime) ){
            isStoreOpen = false;
        }
        else{
            isStoreOpen = true;
        } return isStoreOpen;
    }

    public ResponseEntity<ResponseDTO> updateReservation(Reservation updatedTable,String id,
                                                         HttpServletResponse response) {
        try {
            if (checkIfSafeToBook(updatedTable)) {
                resRepoImpl.updateReservationByID(updatedTable, Long.parseLong(id));
                resDTO.setId(id);
                resDTO.setStatus("BOOKED");
                resEntity = new ResponseEntity(resDTO, HttpStatus.ACCEPTED);
            } else {
                resDTO.setId("0");
                resDTO.setStatus("UNAVAILABLE");
                resEntity = new ResponseEntity(resDTO, HttpStatus.UNPROCESSABLE_ENTITY);
            }
        } catch(Exception e) {
            resDTO.setId("0");
            resDTO.setStatus("UNAVAILABLE");
            resEntity = new ResponseEntity(resDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return  resEntity;
    }

    public ResponseEntity<ResponseDTO>  removeReservation(String id,HttpServletResponse response) {

        resDTO.setId("0");
        resDTO.setStatus("UNRESERVED");
        ResponseEntity resEntity = new ResponseEntity(resDTO, HttpStatus.ACCEPTED);
        try {
            resRepoImpl.deletebyID(Long.parseLong(id));
       } catch (Exception e){
            resEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return  resEntity;
    }
}
