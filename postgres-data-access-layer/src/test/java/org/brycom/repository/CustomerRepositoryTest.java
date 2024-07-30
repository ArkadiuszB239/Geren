package org.brycom.repository;

import org.brycom.entities.CustomerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@EnableAutoConfiguration
@DataJpaTest
@ContextConfiguration(classes = CustomerRepository.class)
@EntityScan(basePackages = "org.brycom.entities")
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=validate"
})
class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void initData() {
        CustomerEntity customer = new CustomerEntity();
        customer.setName("Test name");
        customer.setPhoneNumber("433 577 233");
        entityManager.persistAndFlush(customer);
    }

    @Test
    void shouldQueryCustomerInsertedByEntityManager() {
        List<CustomerEntity> customer = customerRepository.findByPhoneNumbers(List.of("433 577 233"));

        assertThat(customer).isNotEmpty();
        assertEquals(1, customer.size());
        assertEquals("Test name", customer.get(0).getName());
    }

    @Test
    @Sql("classpath:create-customer.sql")
    void shouldQueryCustomerInsertedByScript() {
        List<CustomerEntity> customer = customerRepository.findByPhoneNumbers(List.of("333444555"));

        assertThat(customer).isNotEmpty();
        assertEquals(1, customer.size());
        assertEquals("Test", customer.get(0).getName());
    }

    @Test
    @Sql("classpath:create-customer.sql")
    void shouldQueryBothCustomers() {
        List<CustomerEntity> customer = customerRepository.findByPhoneNumbers(List.of("333444555", "433 577 233"));

        assertThat(customer).isNotEmpty();
        assertEquals(2, customer.size());
    }
}