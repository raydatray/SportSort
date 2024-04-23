package ca.mcgill.ecse321.sportsregistrationw24.dto.Room;

public class RoomUpdateCO {
  private Integer id;
  private String name;
  private Integer capacity;

  public RoomUpdateCO() {
  }

  public RoomUpdateCO(Integer id, String name, Integer capacity) {
    this.id = id;
    this.name = name;
    this.capacity = capacity;
  }

  public Integer getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public Integer getCapacity() {
    return this.capacity;
  }
}
