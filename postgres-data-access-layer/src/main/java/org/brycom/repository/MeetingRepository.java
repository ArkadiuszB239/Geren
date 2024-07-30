package org.brycom.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.brycom.entities.MeetingEntity;
import org.brycom.valueobject.MeetEventState;
import org.brycom.valueobject.MeetEventsSelectionPeriod;
import org.springframework.stereotype.Repository;

import java.time.ZoneOffset;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MeetingRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public MeetingEntity findById(Long id) {
        return entityManager.find(MeetingEntity.class, id);
    }

    public List<MeetingEntity> findBySelectionPeriodAndState(MeetEventsSelectionPeriod selectionPeriod, MeetEventState state) {
        return entityManager.createQuery("""
                        SELECT m from MeetingEntity m
                        JOIN FETCH m.customer c
                        WHERE m.meetEventState = :state AND m.startTime between :periodStart AND :periodEnd
                        """, MeetingEntity.class)
                .setParameter("state", state)
                .setParameter("periodStart", selectionPeriod.getPeriodStart().atOffset(ZoneOffset.UTC))
                .setParameter("periodEnd", selectionPeriod.getPeriodEnd().atOffset(ZoneOffset.UTC))
                .getResultList();
    }
}
