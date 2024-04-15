package Requests;

import Utils.Constants;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserReq {

    public static Response getUserList(){
        return RestAssured.given().log().all().get(Constants.baseURL+Constants.userEndPoint);

    }

    public static Response getSingleUserList(String id){
        return RestAssured.given().log().all().get(Constants.baseURL+Constants.userEndPoint+"/"+id);

    }
    public static Response createUser(String username, String job){
        return  RestAssured.given().log().all().contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"username\": \"" + username + "\",\n" +
                        "    \"job\": \"" + job + "\"\n" +
                        "}").post(Constants.baseURL + Constants.userEndPoint);

    }

    public static Response updateUser(String username, String job){
        return RestAssured.given().log().all().contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"username\": \"" + username + "\",\n" +
                        "    \"job\": \"" + job + "\"\n" +
                        "}").put(Constants.baseURL + Constants.userEndPoint2);
    }

    public static Response deleteUser(){
        return RestAssured.given().log().all().contentType(ContentType.JSON)
                .delete(Constants.baseURL + Constants.userEndPoint2);
    }
}
