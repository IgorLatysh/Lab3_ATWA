import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class LabThree {
    private static final String baseUrl = "https://petstore.swagger.io";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = baseUrl;
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.requestSpecification = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        RestAssured.responseSpecification = new ResponseSpecBuilder().build();
    }

    @Test()
    public void verifyGetPetById() {
        given().get("/v2/pet/1")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test(dependsOnMethods = "verifyGetPetById")
    public void verifyAddNewPet() {
        Map<String, ?> body = Map.of(
                "id", 1,
                "name", "Buddy",
                "status", "available"
        );

        given().body(body)
                .post("/v2/pet")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test(dependsOnMethods = "verifyAddNewPet")
    public void verifyUpdatePet() {
        Map<String, ?> body = Map.of(
                "id", 1,
                "name", "Buddy",
                "status", "sold"
        );

        given().body(body)
                .put("/v2/pet")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test(dependsOnMethods = "verifyUpdatePet")
    public void verifyDeletePet() {
        given().delete("/v2/pet/1")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
}