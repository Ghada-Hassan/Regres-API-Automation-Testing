package Requests;

import Utils.Constants;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserListReq {
  public static Response getUserList(){
    return RestAssured.given().log().all().get(Constants.baseURL+Constants.userEndPoint);

  }

}
