package Tests;
import Requests.UserReq;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserTest {
    String username = "morpheus";
    String job = "leader";
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
