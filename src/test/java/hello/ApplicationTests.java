package hello;

import static org.junit.Assert.assertThat;

import org.junit.Test;
// import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import echo.model.Account;
import echo.repository.AccountRepository;
import echo.repository.RoleRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
}
