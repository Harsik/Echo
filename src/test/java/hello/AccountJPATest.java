package hello;

import static org.junit.Assert.assertThat;

import org.junit.Test;
// import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import echo.model.Account;
import echo.repository.AccountRepository;
import echo.repository.RoleRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountJPATest {

@Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    // @Test
    // public void accountSaveTest(){
    //     final Account account = new Account("user1@email.com","password");
    //     final Account saveAccount = accountRepository.save(account);
    //     assertThat(saveAccount.getId(), is(notNullValue()));
    // }
}