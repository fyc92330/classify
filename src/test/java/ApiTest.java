import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class ApiTest {

	@Test
	void stockDemo() {
		String date = "20230101";
		String stockNo = "0050";

		RestTemplate restTemplate = new RestTemplate();
		String url = "https://www.twse.com.tw/exchangeReport/STOCK_DAY?response=json&date={date}&stockNo={stockNo}";

		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("date", date);
		uriVariables.put("stockNo", stockNo);

		ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class, uriVariables);
		String responseBodyJson = responseEntity.getBody();
		System.err.println(responseBodyJson);
	}

}
