package org.kbannach.test.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.function.Consumer;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class TransactionWrapper {

    private final EntityManager entityManager;

    @Transactional
    public void executeInTransaction(Consumer<EntityManager> consumer) {
        consumer.accept(entityManager);
    }

    @Transactional(readOnly = true)
    public <T> T executeInReadOnlyTransactionAndReturn(Function<EntityManager, T> function) {
        return function.apply(entityManager);
    }
}
