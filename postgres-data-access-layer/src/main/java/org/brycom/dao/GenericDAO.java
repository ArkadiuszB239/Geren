package org.brycom.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class GenericDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;

    @Transactional
    public <T> void saveInBatch(Iterable<T> entities) {
        int i=0;
        for (T entity: entities) {
            entityManager.persist(entity);

            i++;

            if (i % batchSize == 0 && i > 0) {
                log.info("Flushing the EntityManager containing {} entities ...", i);

                entityManager.flush();
                entityManager.clear();
                i = 0;
            }
        }

        if (i > 0) {
            log.info("Flushing the remaining {} entities ...", i);

            entityManager.flush();
            entityManager.clear();
        }
    }
}
