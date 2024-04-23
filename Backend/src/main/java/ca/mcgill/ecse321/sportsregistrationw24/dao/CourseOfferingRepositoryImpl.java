package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.CourseOffering;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseType;
import ca.mcgill.ecse321.sportsregistrationw24.model.InstructorAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseOfferingRepositoryImpl implements CourseOfferingRepositoryCustom {
  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Optional<List<CourseOffering>> findCourseOfferingsByFilters(Date startDate, Date endDate,
                                                                     Time startTime, Time endTime,
                                                                     Integer highPrice,
                                                                     List<CourseType> courseTypes,
                                                                     List<DayOfWeek> daysOffered,
                                                                     List<Room> rooms,
                                                                     List<InstructorAccount> instructors) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<CourseOffering> cq = cb.createQuery(CourseOffering.class);
    Root<CourseOffering> courseOffering = cq.from(CourseOffering.class);
    List<Predicate> predicates = new ArrayList<>();

    if (startDate != null) {
      predicates.add(cb.greaterThanOrEqualTo(courseOffering.get("startDate"), startDate));
    }
    if (endDate != null) {
      predicates.add(cb.lessThanOrEqualTo(courseOffering.get("endDate"), endDate));
    }
    if (startTime != null) {
      predicates.add(cb.greaterThanOrEqualTo(courseOffering.join("courseSessions").get("startTime"), startTime));
    }
    if (endTime != null) {
      predicates.add(cb.lessThanOrEqualTo(courseOffering.join("courseSessions").get("endTime"), endTime));
    }
    if (highPrice != null) {
      predicates.add(cb.lessThanOrEqualTo(courseOffering.get("price"), highPrice));
    }
    if (daysOffered != null && !daysOffered.isEmpty()) {
      predicates.add(courseOffering.join("daysOffered").in(daysOffered));
    }
    if (courseTypes != null && !courseTypes.isEmpty()) {
      predicates.add(courseOffering.get("courseType").in(courseTypes));
    }
    if (rooms != null && !rooms.isEmpty()) {
      predicates.add(courseOffering.get("room").in(rooms));
    }
    if (instructors != null && !instructors.isEmpty()) {
      predicates.add(courseOffering.get("instructorAccount").in(instructors));
    }


    cq.where(cb.and(predicates.toArray(new Predicate[0])));
    List<CourseOffering> result = entityManager.createQuery(cq).getResultList();
    return Optional.ofNullable(result.isEmpty() ? null : result);
  }
}

