package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.CourseType;
import ca.mcgill.ecse321.sportsregistrationw24.model.InstructorAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.Room;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;

import ca.mcgill.ecse321.sportsregistrationw24.model.CourseOffering;
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

public class CourseOfferingRepositoryImpl implements CourseOfferingRepositoryCustom{
  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Optional<List<CourseOffering>> findCourseOfferingsByFilters(Date startDate, Date endDate,
                                                                     Time startTime, Time endTime,
                                                                     Integer lowPrice, Integer highPrice,
                                                                     CourseType courseType,
                                                                     List<DayOfWeek> daysOffered,
                                                                     Room room,
                                                                     InstructorAccount instructor) {
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
    if (lowPrice != null) {
      predicates.add(cb.greaterThanOrEqualTo(courseOffering.get("price"), lowPrice));
    }
    if (highPrice != null) {
      predicates.add(cb.lessThanOrEqualTo(courseOffering.get("price"), highPrice));
    }
    if (courseType != null) {
      predicates.add(cb.equal(courseOffering.get("courseType"), courseType));
    }
    if (daysOffered != null && !daysOffered.isEmpty()) {
      predicates.add(courseOffering.join("daysOffered").in(daysOffered));
    }
    if (room != null) {
      predicates.add(cb.equal(courseOffering.get("room"), room));
    }
    if (instructor != null) {
      predicates.add(cb.equal(courseOffering.get("instructorAccount"), instructor));
    }

    cq.where(cb.and(predicates.toArray(new Predicate[0])));
    List<CourseOffering> result = entityManager.createQuery(cq).getResultList();
    return Optional.ofNullable(result.isEmpty() ? null : result);
  }
}

