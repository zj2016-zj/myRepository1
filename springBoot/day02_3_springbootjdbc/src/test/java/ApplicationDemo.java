import com.zj.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes ={ App.class})
public class ApplicationDemo {
    @Autowired
    DataSource dataSource;
    @Test
    public void getConnectionTest() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection.getClass());
    }
}
