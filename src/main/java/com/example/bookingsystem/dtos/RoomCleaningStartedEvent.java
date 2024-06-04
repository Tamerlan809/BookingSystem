package com.example.bookingsystem.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "room_cleaning_started_events")
public class RoomCleaningStartedEvent extends RoomEvent {
    @Getter @Setter
    @JsonProperty("CleaningByUser")
    private String cleaningByUser;



    public RoomCleaningStartedEvent() {}

    @Override
    public String getDescription() {
        return "Städning påbörjat av " + cleaningByUser;
    }

}
