package resources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class Utils {

	public static RequestSpecification req;
	
	public RequestSpecification requestSpecification() throws IOException
	{
		
		if(req==null)
		{
		PrintStream log =new PrintStream(new FileOutputStream("logging.txt"));
		 req=new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl"))
				 .addFilter(RequestLoggingFilter.logRequestTo(log))
				 .addFilter(ResponseLoggingFilter.logResponseTo(log))
		.setContentType(ContentType.JSON).build();
		 return req;
		}
		return req;
		
		
	}
	
	public static String getGlobalValue(String key) throws IOException
	{
		Properties prop =new Properties();
		FileInputStream fis =new FileInputStream(System.getProperty("user.dir")+"/src/test/java/resources/global.properties");
		prop.load(fis);
		return prop.getProperty(key);
	
		
		
	}
	
	
	public String getJsonPath(Response response,String key)
	{
		  String resp=response.asString();
		JsonPath   js = new JsonPath(resp);
		return js.get(key).toString();
	}
	
	public Map<String, String> createMap(String key, String value){
		
		Map<String, String> map = new HashMap<String, String>();
		map.put(key, value);
		return map;
		
	}
	
	public Map<String, String> createMap(String key, String value, String key2, String value2){
		
		Map<String, String> map = new HashMap<String, String>();
		map.put(key, value);
		map.put(key2, value2);
		return map;
		
	}
	
	
	
	public String getCurrentDate() {
		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern("yyyy-MM-dd")
				.withZone(ZoneOffset.UTC);

		String datE = formatter.format(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")));
		
		Calendar cal = Calendar.getInstance();
		Date date = null;
		
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(datE);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, -1);

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		return dateFormat.format(cal.getTime());
	}
}
