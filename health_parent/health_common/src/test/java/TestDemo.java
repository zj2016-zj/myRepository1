import com.zj.utils.SMSUtils;
import org.junit.Test;

public class TestDemo {
    @Test
    public  void getM() {
        try {
            SMSUtils.sendShortMessage("SMS_193233569","18390139465","1234");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
