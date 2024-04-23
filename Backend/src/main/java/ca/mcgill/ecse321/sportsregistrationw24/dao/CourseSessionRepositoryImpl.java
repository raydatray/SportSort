package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CourseSessionRepositoryImpl implements CourseSessionRepositoryCustom {
  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Optional<List<CourseSession>> findCourseSessionsByFilters(Date startDate, Date endDate,
                                                                   Time startTime, Time endTime,
                                                                   CourseType courseType,
                                                                   List<DayOfWeek> daysOffered,
                                                                   CourseOffering courseOffering,
                                                                   Room room,
                                                                   InstructorAccount instructor) {
    // Initialize the SQL query and the parameters list
    String sql = "SELECT * FROM CourseSession WHERE 1=1";
    Map<String, Object> parameters = new HashMap<>();

    // Add conditions to the SQL query and the parameters list
    if (startDate != null) {
      sql += " AND date >= :startDate";
      parameters.put("startDate", startDate);
    }
    if (endDate != null) {
      sql += " AND date <= :endDate";
      parameters.put("endDate", endDate);
    }
    if (startTime != null) {
      sql += " AND start_time >= :startTime";
      parameters.put("startTime", startTime);
    }
    if (endTime != null) {
      sql += " AND end_time <= :endTime";
      parameters.put("endTime", endTime);
    }
    if (courseType != null) {
      sql += " AND course_offering_id IN (SELECT id FROM courseoffering WHERE course_type_id = :courseType)";
      parameters.put("courseType", courseType.getId());
    }
    if (daysOffered != null && !daysOffered.isEmpty()) {
      List<Integer> daysOfferedInt = daysOffered.stream()
        .map(day -> day.getValue() % 7)
        .collect(Collectors.toList());
      sql += " AND EXTRACT(DOW FROM date) IN (:daysOffered)";
      parameters.put("daysOffered", daysOfferedInt);
    }
    if (courseOffering != null) {
      sql += " AND course_offering_id = :courseOffering";
      parameters.put("courseOffering", courseOffering.getId());
    }
    if (room != null) {
      sql += " AND course_offering_id IN (SELECT id FROM courseoffering WHERE room_id = :room)";
      parameters.put("room", room.getId());
    }
    if (instructor != null) {
      sql += " AND course_offering_id IN (SELECT id FROM courseoffering WHERE instructor_account_id = :instructor)";
      parameters.put("instructor", instructor.getId());
    }

    // Execute the SQL query with the parameters
    List<CourseSession> result = entityManager.createNativeQuery(sql, CourseSession.class)
      .unwrap(org.hibernate.query.Query.class)
      .setProperties(parameters)
      .getResultList();

    return Optional.ofNullable(result.isEmpty() ? null : result);
  }

}
