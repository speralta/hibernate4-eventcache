package fr.persistence.copy.repository;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import fr.persistence.copy.domain.Account;
import fr.persistence.copy.domain.Currency;
import fr.persistence.copy.domain.User;

@Named
@Singleton
public class Repository {

    @PersistenceContext
    protected EntityManager entityManager;

    @Transactional(readOnly = true)
    public User getUserById(String pk) {
        return entityManager.find(User.class, pk);
    }

    @Transactional
    public void merge(User... instances) {
        for (User instance : instances) {
            entityManager.merge(instance);
        }
    }

    @Transactional(readOnly = true)
    public void mergeOnReadOnly(User... instances) {
        for (User instance : instances) {
            entityManager.merge(instance);
        }
    }

    @Transactional(readOnly = true)
    public Currency getCurrencyById(String pk) {
        return entityManager.find(Currency.class, pk);
    }

    @Transactional(readOnly = true)
    public Account getAccountById(String pk) {
        return entityManager.find(Account.class, pk);
    }

    @Transactional(readOnly = true)
    public Map<Class<?>, Object> getAccountByIdWithMerge(User user, String pk) {
        user = entityManager.merge(user);
        Account account = entityManager.find(Account.class, pk);
        Map<Class<?>, Object> map = new HashMap<>();
        map.put(User.class, user);
        map.put(Account.class, account);
        return map;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
