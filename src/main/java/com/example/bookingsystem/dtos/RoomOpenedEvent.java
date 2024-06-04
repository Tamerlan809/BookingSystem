package com.example.bookingsystem.dtos;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;


@Entity
@Table(name = "room_opened_events")
public class RoomOpenedEvent extends RoomEvent {

    @Getter
    private final String description = "Dörren öppnad";
    public RoomOpenedEvent() {

    }
    @Override
    public String getDescription() {
        return description;
    }
}
