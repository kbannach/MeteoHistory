package org.kbannach;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kbannach.test.utils.DatabaseCleaner;
import org.kbannach.test.utils.MockMvcHelper;
import org.kbannach.test.utils.TransactionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public abstract class IntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected TransactionWrapper transactionWrapper;

    @Autowired
    protected MockMvcHelper mockMvcHelper;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void cleanupDatabase() {
        databaseCleaner.cleanup();
    }

    protected <T> T findById(Class<T> clazz, Long id) {
        return transactionWrapper.executeInReadOnlyTransactionAndReturn(em -> em.find(clazz, id));
    }
}
