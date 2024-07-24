package org.brycom.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.brycom.valueobject.NotificationState;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "meeting")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MeetingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meeting_id_generator")
    @SequenceGenerator(name = "meeting_id_generator", sequenceName = "s_meeting", allocationSize = 1)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @Basic(optional = false)
    private OffsetDateTime startTime;

    @Basic(optional = false)
    @EqualsAndHashCode.Include
    private LocalDate meetingDay;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    private NotificationState notificationState;

    @Basic(optional = false)
    @EqualsAndHashCode.Include
    private String phoneNumber;

    @Basic(optional = false)
    @EqualsAndHashCode.Include
    private String sourceCalendar;
}
