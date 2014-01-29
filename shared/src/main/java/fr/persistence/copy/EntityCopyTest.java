package fr.persistence.copy;

import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;

import fr.persistence.copy.domain.Account;
import fr.persistence.copy.domain.User;
import fr.persistence.copy.repository.Repository;

public abstract class EntityCopyTest {

    @Inject
    private Repository repository;

    private User instanceA;
    private User instanceB;
    private User instanceC1;
    private User instanceC2;

    @Before
    public void setUp() {
        instanceA = repository.getUserById("1");
        instanceB = repository.getUserById("1");
        Assertions.assertThat(instanceA == instanceB).isFalse();
        instanceC1 = repository.getUserById("1");
        instanceC1.setName(UUID.randomUUID().toString());
        instanceC2 = repository.getUserById("1");
        instanceC2.setName(UUID.randomUUID().toString());
        Assertions.assertThat(instanceC1.getName()).isNotEqualTo(instanceC2.getName());
    }

    @Test
    public void testMerge() {
        repository.merge(instanceB, instanceA);
    }

    @Test
    public void testMergeModified() {
        repository.merge(instanceC1, instanceC2);
        User user = repository.getUserById("1");
        Assertions.assertThat(user.getName()).isEqualTo(instanceC2.getName());
    }

    @Test(expected = Exception.class)
    public void testMergeOptimisticLock() {
        repository.merge(instanceC1);
        repository.merge(instanceC2);
    }

    @Test
    public void testMergeSameInstance() {
        repository.merge(instanceA, instanceA);
    }

    @Test
    public void testMergeReadOnly() {
        repository.mergeOnReadOnly(instanceA, instanceB);
    }

    @Test
    public void testMergeReadOnlySameInstance() {
        repository.mergeOnReadOnly(instanceA, instanceA);
    }

    @Test
    public void testMergeOnCurrency() {
        User instanceD = repository.getUserById("1");
        instanceD.setCurrency(repository.getCurrencyById("1"));
        User instanceE = repository.getUserById("1");
        instanceE.setCurrency(repository.getCurrencyById("1"));
        repository.merge(instanceD, instanceE);
    }

    @Test
    public void testMergeGraph() {
        User instanceF = repository.getUserById("1");
        Account account = repository.getAccountById(instanceF.getAccount().getId());
        account.getCurrency().setName("Tata dollar");
        instanceF.setAccount(account);
        instanceF.getCurrency().setName("Toto dollar");
        repository.merge(instanceF);
    }

    @Test
    public void testMergeGraphNoModification() {
        User instanceG = repository.getUserById("1");
        Account account = repository.getAccountById(instanceG.getAccount().getId());
        instanceG.setAccount(account);
        Assertions.assertThat(instanceG.getCurrency() == account.getCurrency()).isFalse();
        repository.merge(instanceG);
    }

    @Test
    public void testMergeGraphNoModificationWithManualReattach() {
        User instanceG = repository.getUserById("1");
        Account account = repository.getAccountById(instanceG.getAccount().getId());
        instanceG.setAccount(account);
        account.setCurrency(instanceG.getCurrency());
        Assertions.assertThat(instanceG.getCurrency() == account.getCurrency()).isTrue();
        repository.merge(instanceG);
    }

    @Test
    public void testMergeGraphNoModificationSolution() {
        User instanceG = repository.getUserById("1");
        Map<Class<?>, Object> result = repository.getAccountByIdWithMerge(instanceG, instanceG.getAccount().getId());
        instanceG = (User) result.get(User.class);
        Account account = (Account) result.get(Account.class);
        instanceG.setAccount(account);
        Assertions.assertThat(instanceG.getCurrency() == account.getCurrency()).isTrue();

        repository.merge(instanceG);
    }
}
