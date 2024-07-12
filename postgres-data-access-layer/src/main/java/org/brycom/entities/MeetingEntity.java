package org.brycom.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.brycom.valueobject.NotificationState;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "meeting")
public class MeetingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meeting_id_generator")
    @SequenceGenerator(name = "meeting_id_generator", sequenceName = "s_meeting", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @Basic(optional = false)
    private OffsetDateTime startTime;

    @Basic(optional = false)
    private LocalDate meetingDay;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    private NotificationState notificationState;

    @Basic(optional = false)
    private String phoneNumber;
}
