package org.brycom.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.brycom.entities.CustomerEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<CustomerEntity> findByPhoneNumbers(List<String> phoneNumbers) {
        return entityManager.createQuery(
                        """
                                SELECT customer from CustomerEntity customer
                                WHERE phoneNumber in (:phoneNumbers)
                                """, CustomerEntity.class
                ).setParameter("phoneNumbers", phoneNumbers)
                .getResultList();
    }
}
