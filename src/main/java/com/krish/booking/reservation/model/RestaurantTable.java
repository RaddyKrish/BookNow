package com.krish.booking.reservation.model;

import javax.persistence.*;

@Entity
public class RestaurantTable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String tableName;

   public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

}
