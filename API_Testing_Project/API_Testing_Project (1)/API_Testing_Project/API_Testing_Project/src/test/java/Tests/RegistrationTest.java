package Tests;

import Requests.RegistrationReq;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.OrderingComparison.greaterThan;

public class RegistrationTest {
    Response response = RegistrationReq.RegisterUser("eve.holt@reqres.in", "pistol");

    @Test
    public void checkRegisterStatusCode() {
        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test
    public void validateAttributesNotNull(){
        JsonPath jsonPath = response.jsonPath();
        int id = jsonPath.get("id");
        Assert.assertNotNull(id);
        String token = jsonPath.get("token");
        Assert.assertNotNull(token);
    }

    @Test
    public void validateAttributesAreCorrect(){
        JsonPath jsonPath = response.jsonPath();
        int id = jsonPath.get("id");
        assertThat(id, greaterThan(0));
        String token = jsonPath.get("token");
        Assert.assertEquals(token,"QpwL5tke4Pnpja7X4");
    }

    @Test
    public void registerUnsuccessfulWithoutPassTest(){
        Response response = RegistrationReq.RegisterUser("sydney@fife","");
        response.prettyPrint();
        response.then().statusCode(400);

        JsonPath jsonPath= response.jsonPath();
        String error = jsonPath.get("error");
        Assert.assertNotNull(error);
        Assert.assertEquals(error,"Missing password");
    }

    @Test
    public void registerUnsuccessfulWithoutEmailTest(){
        Response response = RegistrationReq.RegisterUser("","cityslicka");
        response.prettyPrint();
        response.then().statusCode(400);

        JsonPath jsonPath= response.jsonPath();
        String error = jsonPath.get("error");
        Assert.assertNotNull(error);
        Assert.assertEquals(error,"Missing email or username");
    }
}
