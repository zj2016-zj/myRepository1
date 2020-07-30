import com.zj.ConsumeApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ConsumeApp.class)
public class LoadBalanceTest {
    @Autowired
    LoadBalancerClient client;
    @Test
   public void testBalance(){
       for(int i = 1;i<=10;i++){
           ServiceInstance instance = client.choose("serviceProvider");
           System.out.println(instance.getHost() + ":" +instance.getPort());
       }
   }
}
