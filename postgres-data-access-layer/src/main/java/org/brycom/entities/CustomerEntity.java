package org.brycom.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "customer")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_generator")
    @SequenceGenerator(name = "customer_id_generator", sequenceName = "s_customer", allocationSize = 1)
    private Long id;

    @Basic(optional = false)
    private String name;

    @EqualsAndHashCode.Include
    @Basic(optional = false)
    private String phoneNumber;
}
