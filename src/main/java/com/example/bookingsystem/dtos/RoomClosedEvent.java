package com.example.bookingsystem.dtos;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "room_closed_events")
public class RoomClosedEvent extends RoomEvent {

    @Getter
    private final String description = "Dörren stängd";

    public RoomClosedEvent() {

    }
    @Override
    public String getDescription() {
        return description;
    }
}
