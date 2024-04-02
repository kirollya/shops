package pers.nico;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class MainFacadeTest {
    @Test
    void testHelloEndpoint() {
        given()
          .when().get("/main_page")
          .then()
             .statusCode(200)
             .body(is("Hello from RESTEasy Reactive"));
    }

}