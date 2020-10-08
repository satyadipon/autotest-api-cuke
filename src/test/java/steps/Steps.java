package steps;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.Utils;

public class Steps extends Utils
{
	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;

	/* Initialize RequestSpecification by calling this steps */
	@Given("^Get the latest foreign exchange reference rates$")
	public void get_the_latest_foreign_exchange_reference_rates() throws Throwable {
		res=given().spec(requestSpecification());
	}

	/* Step to execute lastest endpoint and get response without any query parameter */
	@When("^user calls \"([^\"]*)\" with \"([^\"]*)\" http request$")
	public void user_calls_something_with_something_http_request(String resource, String method) throws Throwable {
		APIResources resourceAPI=APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());


		resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

		if(method.equalsIgnoreCase("GET"))
			response =res.when().get(resourceAPI.getResource());
	}
	
	/* Step to execute lastest endpoint and get response with query parameter symbol */
	@When("^user calls \"([^\"]*)\" with \"([^\"]*)\" http request and \"([^\"]*)\" parameter$")
	public void user_calls_something_with_something_http_request_and_something_parameter(String resource, String method, String currency) throws Throwable {

		APIResources resourceAPI=APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());


		resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

		if(method.equalsIgnoreCase("GET"))
			response =res.queryParams((createMap("symbols", currency))).when().get(resourceAPI.getResource());

	}
	
	/* Step to execute lastest endpoint and get response with query parameter symbol and base */
	@When("^user calls \"([^\"]*)\" with \"([^\"]*)\" http request and \"([^\"]*)\" parameter and \"([^\"]*)\" parameter$")
    public void user_calls_something_with_something_http_request_and_something_parameter_and_something_parameter(String resource, String method, String currency, String base) throws Throwable {
		
		APIResources resourceAPI=APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());


		resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

		if(method.equalsIgnoreCase("GET"))
			response =res.queryParams((createMap("symbols", currency, "base", base))).when().get(resourceAPI.getResource());
	
	}
	
	/* Step to execute lastest endpoint and get response with query parameter symbol, base and past date*/
	@When("^user calls \"([^\"]*)\" with \"([^\"]*)\" http request and \"([^\"]*)\" and \"([^\"]*)\" parameter and \"([^\"]*)\" parameter$")
    public void user_calls_something_with_something_http_request_and_something_and_something_parameter_and_something_parameter(String resource, String method, String pastDate, String currency, String base) throws Throwable {
		APIResources resourceAPI=APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource(pastDate));


		resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

		if(method.equalsIgnoreCase("GET"))
			response =res.queryParams((createMap("symbols", currency, "base", base))).when().get(resourceAPI.getResource(pastDate));
    }


	/* Step to assert response code 200 */
	@Then("^the API call got success with status code 200$")
	public void the_api_call_got_success_with_status_code_200() throws Throwable {
		assertEquals(response.getStatusCode(),200);
	}

	/* Step to assert currentdate from the response body */
	@And("^\"([^\"]*)\" in response body should be the current date$")
	public void something_in_response_body_should_be_the_current_date(String keyValue) throws Throwable {
		assertEquals(getJsonPath(response,keyValue), getCurrentDate());
	}
	
	/* Step to assert provided date from the response body */
	@And("^\"([^\"]*)\" in response body is \"([^\"]*)\"$")
	public void something_in_response_body_is_something(String keyValue, String Expectedvalue) throws Throwable {
		assertEquals(getJsonPath(response,keyValue),Expectedvalue);
	}

	/* Step to assert currency from the response body */
	@And("^verify currency exchange rate is available for \"([^\"]*)\" in response body$")
	public void verify_currency_exchange_rate_is_available_for_something_in_response_body(String keyValue) throws Throwable {

		String[] keyValues;
		if(keyValue.contains(","))
		{
			keyValues = keyValue.split(",");
			for(String value:keyValues) {
				assertTrue(getJsonPath(response, "rates."+value).length()>0);
			}
		}
		else
			assertTrue(getJsonPath(response, "rates."+keyValue).length()>0);
	}


}
