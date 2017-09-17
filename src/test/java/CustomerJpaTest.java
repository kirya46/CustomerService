import com.common.Application;
import com.common.config.AppConfig;
import com.common.config.DataConfig;
import com.common.domain.Customer;
import com.common.persistence.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Kirill Stoianov on 17/09/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class, AppConfig.class, DataConfig.class})
public class CustomerJpaTest {

    @Autowired
    private CustomerRepository repository;

    @Test
    public void saveCustomer() throws Exception{
         Customer customer = new Customer("Test name", "Test surname", "2131244");
        final Customer save = this.repository.save(customer);
        System.out.println(save);
        System.out.println(this.repository.findAll().toString());
        System.out.println(this.repository.findOne(save.getId()));
    }
}
