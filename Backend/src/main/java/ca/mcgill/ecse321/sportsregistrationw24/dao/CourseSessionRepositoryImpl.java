package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CourseSessionRepositoryImpl implements CourseSessionRepositoryCustom{
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
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<CourseSession> cq = cb.createQuery(CourseSession.class);
    Root<CourseSession> courseSession = cq.from(CourseSession.class);
    List<Predicate> predicates = new ArrayList<>();
    List<CourseSession> daysOfferedResult = new ArrayList<>();

    if (startDate != null) {
      predicates.add(cb.greaterThanOrEqualTo(courseSession.get("date"), startDate));
    }
    if (endDate != null) {
      predicates.add(cb.lessThanOrEqualTo(courseSession.get("date"), endDate));
    }
    if (startTime != null) {
      predicates.add(cb.greaterThanOrEqualTo(courseSession.get("startTime"), startTime));
    }
    if (endTime != null) {
      predicates.add(cb.lessThanOrEqualTo(courseSession.get("endTime"), endTime));
    }
    if (courseType != null) {
      predicates.add(cb.equal(courseSession.get("courseOffering").get("courseType"), courseType));
    }
    if (daysOffered != null && !daysOffered.isEmpty()) {
      // Convert DayOfWeek to corresponding integer values
      List<Integer> daysOfferedInt = daysOffered.stream()
        .map(day -> day.getValue() % 7) // Adjust DayOfWeek values for PostgreSQL
        .collect(Collectors.toList());

      // Create a native query to check if the day of the week of the course session's date is in daysOffered
      String sql = "SELECT * FROM CourseSession WHERE EXTRACT(DOW FROM date) IN (:daysOffered)";
      daysOfferedResult = entityManager.createNativeQuery(sql, CourseSession.class)
        .setParameter("daysOffered", daysOfferedInt)
        .getResultList();
    }
    if (courseOffering != null) {
      predicates.add(cb.equal(courseSession.get("courseOffering"), courseOffering));
    }
    if (room != null) {
      predicates.add(cb.equal(courseSession.get("room"), room));
    }
    if (instructor != null) {
      predicates.add(cb.equal(courseSession.get("instructor"), instructor));
    }

    cq.where(predicates.toArray(new Predicate[0]));
    List<CourseSession> result= entityManager.createQuery(cq).getResultList();
    result.addAll(daysOfferedResult);
    return Optional.ofNullable(result.isEmpty() ? null : result);
  }
}
