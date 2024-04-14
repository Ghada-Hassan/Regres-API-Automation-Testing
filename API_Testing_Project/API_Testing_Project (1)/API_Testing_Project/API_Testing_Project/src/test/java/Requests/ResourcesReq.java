package Requests;

import io.restassured.response.Response;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import Utils.Constants;

public class ResourcesReq {

    public static Response getUsersResource(){
        return RestAssured.given().log().all().get(Constants.baseURL+Constants.resEndPoint);

    }

    public static Response getSingleUserResource(String id){
        return RestAssured.given().log().all().get(Constants.baseURL+Constants.resEndPoint+id);

    }

    public static Response createUserResource(String name, String year, String color, String pantone_value){
        return   RestAssured.given().log().all().
                contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"name\": \""+name+"\",\n" +
                        "    \"year\": \""+year+"\",\n" +
                        "    \"color\": \""+color+"\",\n" +
                        "    \"pantone_value\": \""+pantone_value+"\"\n" +
                        "}").post(Constants.baseURL + Constants.resEndPoint);
    }
    public static Response updateUserPost(String id,String name, String year, String color, String pantone_value) {
        return RestAssured.given().log().all().
                contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"id\": \""+id+"\",\n" +
                        "    \"name\": \""+name+"\",\n" +
                        "    \"year\": \""+year+"\",\n" +
                        "    \"color\": \""+color+"\",\n" +
                        "    \"pantone_value\": \""+pantone_value+"\"\n" +
                        "}").put(Constants.baseURL + Constants.resEndPoint+"/"+id);
    }

    public static Response deleteRescource(String id){
        return RestAssured.given().log().all().delete(Constants.baseURL + Constants.resEndPoint+"/"+id);
    }
}
