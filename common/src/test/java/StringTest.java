import com.test.crowd.util.CrowdUtils;
import org.junit.Test;


public class StringTest {
	
	@Test
	public void testSendCode() {
		String appcode = "2c4a15f2c7e8415e81be238f27f5c4d9";
		String randomCode = CrowdUtils.randomCode(4);
		String phoneNum = "15534812137";
		CrowdUtils.sendShortMessage(appcode, randomCode, phoneNum);
	}

}
