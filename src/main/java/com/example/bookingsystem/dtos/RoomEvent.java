package com.example.bookingsystem.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RoomClosedEvent.class, name = "RoomClosed"),
        @JsonSubTypes.Type(value = RoomCleaningStartedEvent.class, name = "RoomCleaningStarted"),
        @JsonSubTypes.Type(value = RoomOpenedEvent.class, name = "RoomOpened"),
        @JsonSubTypes.Type(value = RoomCleaningFinishedEvent.class, name = "RoomCleaningFinished")
})
@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class RoomEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("RoomNo")
    private String roomNo;

    @JsonProperty("TimeStamp")
    private LocalDateTime timeStamp;

    public RoomEvent() {}

    public abstract String getDescription();

}
