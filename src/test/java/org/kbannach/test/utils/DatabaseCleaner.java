package org.kbannach.test.utils;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaDelete;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DatabaseCleaner {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void cleanup() {
        getAllEntities().forEach(this::delete);
    }

    private <T> void delete(Class<T> e) {
        CriteriaDelete<T> criteriaDelete = entityManager.getCriteriaBuilder().createCriteriaDelete(e);
        criteriaDelete.from(e);
        entityManager.createQuery(criteriaDelete).executeUpdate();
    }

    private Set<Class<?>> getAllEntities() {
        final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(Entity.class));

        return provider.findCandidateComponents("org.kbannach").stream()
                .map(this::getaClass)
                .collect(Collectors.toSet());
    }

    @SneakyThrows
    private Class<?> getaClass(BeanDefinition beanDefinition) {
        return Class.forName(beanDefinition.getBeanClassName());
    }

}
