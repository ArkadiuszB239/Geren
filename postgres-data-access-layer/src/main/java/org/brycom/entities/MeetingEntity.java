package org.brycom.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.brycom.valueobject.MeetEventState;

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
    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private MeetEventState meetEventState;

    @Basic(optional = false)
    private String phoneNumber;

    @Basic(optional = false)
    private String sourceCalendar;

    @Basic(optional = false)
    @EqualsAndHashCode.Include
    private String calendarId;

    @Basic(optional = false)
    @EqualsAndHashCode.Include
    private String eventId;
}
