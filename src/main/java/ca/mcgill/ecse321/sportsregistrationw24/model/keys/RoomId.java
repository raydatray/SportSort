package ca.mcgill.ecse321.sportsregistrationw24.model.keys;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RoomId implements Serializable {
    private Integer floorNumber;
    private Integer roomNumber;

    public RoomId() {}

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public RoomId(Integer floorNumber, Integer roomNumber) {
        this.floorNumber = floorNumber;
        this.roomNumber = roomNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoomId)) return false;
        RoomId roomId = (RoomId) o;
        return Objects.equals(getFloorNumber(), roomId.getFloorNumber()) &&
                Objects.equals(getRoomNumber(), roomId.getRoomNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFloorNumber(), getRoomNumber());
    }
}
