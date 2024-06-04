package com.example.bookingsystem.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "room_cleaning_finished_events")
public class RoomCleaningFinishedEvent extends RoomEvent {
    @Getter @Setter
    @JsonProperty("CleaningByUser")
    private String cleaningByUser;

    public RoomCleaningFinishedEvent() {

    }
    @Override
    public String getDescription() {
        return "Städning avlutad av " + cleaningByUser;
    }
}
