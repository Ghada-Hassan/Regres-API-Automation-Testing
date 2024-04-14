package Tests;
import Requests.UserListReq;
import Requests.UserReq;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import Requests.ResourcesReq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.OrderingComparison.greaterThan;


public class RessourcesTest {
    Response response = ResourcesReq.getUsersResource();
    @Test
    public void checkGetStatusCode(){
        response.prettyPrint();
        response.then().statusCode(200);
    }
    @Test
    public void testAttributesAreCorrect() {
        response.prettyPrint();
        Map<String, Object> jsonResponse = response.jsonPath().getMap("$");
        List<String> expectedKeys = Arrays.asList("id", "name", "year", "color", "pantone_value");
        List<Map<String, Object>> dataArray = (List<Map<String, Object>>) jsonResponse.get("data");
        for (Map<String, Object> dataObject : dataArray) {
            Assert.assertEquals(dataObject.keySet(), expectedKeys, "Keys mismatch in data object");
        }}
    @Test
    public void testAttributesNotNull() {
        Map<String, Object> jsonResponse = response.jsonPath().getMap("$");
        List<Map<String, Object>> dataArray = (List<Map<String, Object>>) jsonResponse.get("data");
        for (Map<String, Object> dataObject : dataArray) {
            for (String key : dataObject.keySet()) {
                System.out.println(dataObject.get(key));
                Assert.assertNotNull(dataObject.get(key), "Attribute '" + key + "' is null");
            }
        }}
    @Test
    public void testNumberOfPages() {
        int totalPages = response.jsonPath().getInt("total_pages");
        Assert.assertEquals(totalPages, 2, "Number of pages is not equal to 2");
    }
    @Test
    public void testNumberOfResources(){
        int totalResources= response.jsonPath().getInt("total");
        Assert.assertEquals(totalResources,12,"Number of resources is not equal to 12 ");
    }
    @Test
    public void testNumberOfResourcesPerPage(){
        int totalResourcesPerPage= response.jsonPath().getInt("per_page");
        Assert.assertEquals(totalResourcesPerPage,6,"Number of resources is not equal to 6 ");
    }
    // tests for get single resource
    @Test
    public void getSingleResourcetest(){
        Response response = ResourcesReq.getSingleUserResource("2");
        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test
    public void checkIdIsCorrect(){
        Response response = ResourcesReq.getSingleUserResource("2");
        Map<String, Object> jsonResponse = response.jsonPath().getMap("data");
        System.out.print(jsonResponse);
        Assert.assertEquals(jsonResponse.get("id").toString(),"2");
    }
    @Test
    public void checkAllAtrributesExist(){
        Response response = ResourcesReq.getSingleUserResource("2");
        Map<String, Object> jsonResponse = response.jsonPath().getMap("data");
        List<String> expectedKeys = Arrays.asList("id", "name", "year", "color", "pantone_value");
        Assert.assertEquals(jsonResponse.size(), expectedKeys.size(), "Number of attributes does not match");
    }
    // tests for create user resource
    @Test
    public void checkCreateStatusCode(){
        Response response = ResourcesReq.createUserResource("Sahar","2015"," #E2583E","17-1456");
        response.prettyPrint();
        response.then().statusCode(201);
    }
    @Test
    public void testAttributesCreatedAreCorrect() {
        Response response =ResourcesReq.createUserResource("Mahmoud","2015"," #E2583E","17-1456");
        String[] output = {"name", "year", "color", "pantone_value", "id", "createdAt"};
        Map<String, Object> jsonResponse = response.jsonPath().getMap("$");
        String[] keys = jsonResponse.keySet().toArray(new String[0]);
        List<String> actualKeys = Arrays.asList(keys);
        Assert.assertEquals(actualKeys, Arrays.asList(output), "Attributes are not created correctly");
    }
    @Test
    public void testCreatedAttributesNotEmpty() {
        Response response = ResourcesReq.createUserResource("Mahmoud","2015"," #E2583E","17-1456");
        Map<String, Object> jsonResponse = response.jsonPath().getMap("$");
        String[] array = jsonResponse.keySet().toArray(new String[0]);
        for (int i = 0; i < array.length; i++) {
            String element = array[i];
            System.out.print(jsonResponse.get(element));
            Assert.assertNotEquals(jsonResponse.get((element)), "", "Value for key '" + element + "' is null");
        }
    }
    @Test
    public void checkCreateDateFormatIsCorrect(){
        Response response = ResourcesReq.createUserResource("sahar", "2011"," #E2583E","17-1456");
        JsonPath jsonPath = response.jsonPath();
        String createdAt = jsonPath.get("createdAt");
        Assert.assertTrue(createdAt.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z"),
                "createdAt date format is not correct");
    }
    @Test
    public void checkCreatedValuesAreCorrect() {
        Response response = ResourcesReq.createUserResource("Sahar", "2015", " #E2583E", "17-1456");
        JsonPath jsonPath = response.jsonPath();
        Assert.assertEquals(jsonPath.get("name"), "Sahar", "name is not as expected");
        Assert.assertEquals(jsonPath.get("year"), "2015", "year is not as expected");
        Assert.assertEquals(jsonPath.get("color"), " #E2583E", "color is not as expected");
        Assert.assertEquals(jsonPath.get("pantone_value"), "17-1456", "pantone-value is not as expected");
    }
    //tests for update user resources
    @Test
    public void checkUpadteStatusCode(){
        Response response = ResourcesReq.updateUserPost("2","sahar","2001","#C74375","17-2031");
        response.prettyPrint();
        response.then().statusCode(200);
    }
    @Test
    public void checkAttributesAreCorrect(){
        Response response =ResourcesReq.updateUserPost("2","sahar","2001","#C74375","17-2031");
        String[] output = {"id", "name", "year","color", "pantone_value", "updatedAt"};
        Map<String, Object> jsonResponse = response.jsonPath().getMap("$");
        String[] keys = jsonResponse.keySet().toArray(new String[0]);
        List<String> actualKeys = Arrays.asList(keys);
        Assert.assertEquals(actualKeys, Arrays.asList(output), "Attributes are not created correctly");
    }
    @Test
    public void checkUpdateDateFormatIsCorrect(){
        Response response = ResourcesReq.updateUserPost("2","sahar","2001","#C74375","17-2031");
        JsonPath jsonPath = response.jsonPath();
        String updatedAt = jsonPath.get("updatedAt");
        Assert.assertTrue(updatedAt.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z"),
                "updatedAt date format is not correct");
    }
    @Test
    public void checkUpdatedValuesAreCorrect() {
        Response response = ResourcesReq.updateUserPost("2", "sahar", "2001", "#C74375", "17-2031");
        JsonPath jsonPath = response.jsonPath();
        Assert.assertEquals(jsonPath.get("name"), "sahar", "name is not as expected");
        Assert.assertEquals(jsonPath.get("year"), "2001", "year is not as expected");
    }

    @Test
    public void delResTest(){
        Response response=ResourcesReq.deleteRescource("2");
        response.prettyPrint();
        response.then().statusCode(204);
    }
}
