package Requests;

import Utils.Constants;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class RegistrationReq {
 public static Response RegisterUser(String email, String password){
  return RestAssured.given().log().all().contentType(ContentType.JSON)
          .body("{\n" +
                  "    \"email\": \"" + email + "\",\n" +
                  "    \"password\": \"" + password + "\"\n" +
                  "}").post(Constants.baseURL + Constants.regEndPoint);

 }

 public static Response RegisterunSuccessWithoutPass(String email){
  return RestAssured.given().log().all().contentType(ContentType.JSON)
          .body("{\n" +
                  "    \"email\": \"" + email + "\",\n" +
                  "}").post(Constants.baseURL + Constants.regEndPoint);

 }


}
