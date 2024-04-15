package Tests;
import Requests.UserReq;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class UserTest {
    String username = "morpheus";
    String job = "leader";

    Response response = UserReq.getUserList();

    @Test
    public void checkGetStatusCode(){
        response.prettyPrint();
        response.then().statusCode(200);
    }
    @Test
    public void testAttributesAreCorrect() {
        response.prettyPrint();
        Map<String, Object> jsonResponse = response.jsonPath().getMap("$");
        List<String> expectedKeys = Arrays.asList("id", "email", "first_name", "last_name", "avatar");
        List<Map<String, Object>> dataArray = (List<Map<String, Object>>) jsonResponse.get("data");
        for (Map<String, Object> dataObject : dataArray) {
            Assert.assertEquals(dataObject.keySet(), expectedKeys, "Keys mismatch in data object");
        }
    }
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

    @Test
    public void testSupportPresence(){
        String Support = response.jsonPath().get("support").toString();
        Assert.assertNotNull(Support);
    }

    // Tests for Get single user

    @Test
    public void getSingleUserTest(){
        Response response = UserReq.getSingleUserList("2");
        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test
    public void checkSingleUserIDisCorrect(){
        Response response = UserReq.getSingleUserList("2");
        Map<String, Object> jsonResponse = response.jsonPath().getMap("data");
        System.out.print(jsonResponse);
        Assert.assertEquals(jsonResponse.get("id").toString(),"2");
    }
    @Test
    public void checkAllSingleUserAttributesExist(){
        Response response = UserReq.getSingleUserList("2");
        Map<String, Object> jsonResponse = response.jsonPath().getMap("data");
        List<String> expectedKeys = Arrays.asList("id", "email", "first_name", "last_name", "avatar");
        Assert.assertEquals(jsonResponse.size(), expectedKeys.size(), "Number of attributes does not match");
    }

    @Test
    public void checkSupportAttributesExist(){
        Response response = UserReq.getSingleUserList("2");
        Map<String, Object> jsonResponse = response.jsonPath().getMap("support");
        List<String> expectedKeys = Arrays.asList("url", "text");
        Assert.assertEquals(jsonResponse.size(), expectedKeys.size(), "Number of attributes does not match");
    }

    @Test
    public void checkURLAttribute(){
        Response response = UserReq.getSingleUserList("2");
        Map<String, Object> jsonResponse = response.jsonPath().getMap("support");
        Assert.assertEquals(jsonResponse.get("url"), "https://reqres.in/#support-heading");
    }

    @Test
    public void checkTextAttribute(){
        Response response = UserReq.getSingleUserList("2");
        Map<String, Object> jsonResponse = response.jsonPath().getMap("support");
        Assert.assertEquals(jsonResponse.get("text"), "To keep ReqRes free, contributions towards server costs are appreciated!");
    }




// Tests for Create user


    @Test
    public void CreateUserTest(){
        Response response = UserReq.createUser(username, job);
        response.prettyPrint();
        response.then().statusCode(201);
        JsonPath jsonPath = response.jsonPath();

        String name = jsonPath.getString("name");
       // Assert.assertEquals(name, username, "Name does not match expected value");


        String userJob = jsonPath.get("job");
        Assert.assertEquals(userJob,job);

        String id = jsonPath.get("id");
        Assert.assertNotNull(id);

        String createdAt = jsonPath.get("createdAt");
        Assert.assertTrue(createdAt.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z"),
                "createdAt format is not as expected");
    }

    @Test
    public void CreateUserWithoutJob(){
        Response response = UserReq.createUser(username,"");
        response.prettyPrint();
        response.then().statusCode(201);
        JsonPath jsonPath= response.jsonPath();

        String userJob = jsonPath.get("job");
        Assert.assertTrue(userJob.isEmpty(), "Job field is not null as expected");

        String createdAt = jsonPath.get("createdAt");
        Assert.assertTrue(createdAt.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z"),
                "createdAt format is not as expected");
    }
    @Test
    public void CreateUserWithoutname(){
        Response response = UserReq.createUser("",job);
        response.prettyPrint();
        response.then().statusCode(201);
        JsonPath jsonPath= response.jsonPath();

        String userJob = jsonPath.get("job");
        Assert.assertEquals(userJob,job);

        String id = jsonPath.get("id");
        Assert.assertNotNull(id);

        String userName = jsonPath.get("name");
        Assert.assertTrue(userName == null || userName.isEmpty(), "Job field is not null or empty as expected");

        String createdAt = jsonPath.get("createdAt");
        Assert.assertTrue(createdAt.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z"),
                "createdAt format is not as expected");
    }

    @Test
    public void updateUserTest(){
        Response response= UserReq.updateUser(username,"teacher");
        response.prettyPrint();
        response.then().statusCode(200);
        JsonPath jsonPath = response.jsonPath();

        String name = jsonPath.getString("name");
        //Assert.assertEquals(name, username, "Name does not match expected value");


        String userJob = jsonPath.get("job");
        Assert.assertEquals(userJob,"teacher");

        String updatedAt = jsonPath.get("updatedAt");
        Assert.assertTrue(updatedAt.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z"),
                "updatedAt format is not as expected");

    }

    @Test
    public void updateUserTestWithoutUsername(){
        Response response= UserReq.updateUser("",job);
        response.prettyPrint();
        response.then().statusCode(200);
        JsonPath jsonPath = response.jsonPath();

        String name = jsonPath.getString("name");
        Assert.assertTrue(name == null || name.isEmpty(), "Job field is not null or empty as expected");


        String userJob = jsonPath.get("job");
        Assert.assertEquals(userJob,job);

        String updatedAt = jsonPath.get("updatedAt");
        Assert.assertTrue(updatedAt.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z"),
                "updatedAt format is not as expected");

    }

    @Test
    public void updateUserTestWithoutJob(){
        Response response= UserReq.updateUser(username,"");
        response.prettyPrint();
        response.then().statusCode(200);
        JsonPath jsonPath = response.jsonPath();

        String name = jsonPath.getString("name");
        //Assert.assertEquals(name, username, "Name does not match expected value");


        String userJob = jsonPath.get("job");
        Assert.assertTrue(userJob == null || userJob.isEmpty(), "Job field is not null or empty as expected");

        String updatedAt = jsonPath.get("updatedAt");
        Assert.assertTrue(updatedAt.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z"),
                "updatedAt format is not as expected");

    }

    @Test
    public void delUserTest(){
        Response response = UserReq.deleteUser();
        response.then().statusCode(204);
    }

}
