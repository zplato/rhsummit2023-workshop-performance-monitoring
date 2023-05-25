package io.containerapps.javaruntime.workshop.quarkus;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class QuarkusResourceTest {

  @Test
  public void testHelloEndpoint() {
    given()
        .when().get("/quarkus")
        .then()
        .statusCode(200)
        .body(is("Quarkus: hello"));
  }

  @Test
  public void testCpuWithDBAndDescEndpoint() {
    given().param("iterations", 1).param("db", true).param("desc", "Java17")
        .when().get("/quarkus/cpu")
        .then()
        .statusCode(200)
        .body(startsWith("Quarkus: CPU consumption is done with"))
        .body(not(containsString("Java17")))
        .body(endsWith("The result is persisted in the database."));
  }

  @Test
  public void testMemoryWithDBAndDescEndpoint() {
    given().param("bites", 1).param("db", true).param("desc", "Java17")
        .when().get("/quarkus/memory")
        .then()
        .statusCode(200)
        .body(startsWith("Quarkus: Memory consumption is done with"))
        .body(not(containsString("Java17")))
        .body(endsWith("The result is persisted in the database."));
  }

  @Test
  public void testStats() {
    given()
        .when().get("/quarkus/stats")
        .then()
        .statusCode(200);
  }

}