package Tests;

import Requests.UserListReq;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.OrderingComparison.greaterThan;

public class UserListTest {

    @Test
    public void testUserListResponse(){


        Response response = UserListReq.getUserList();
        response.prettyPrint();
        response.then().statusCode(200);}}