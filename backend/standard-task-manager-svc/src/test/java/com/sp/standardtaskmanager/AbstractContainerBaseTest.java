package com.sp.standardtaskmanager;

import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jwt.SignedJWT;
import no.nav.security.mock.oauth2.MockOAuth2Server;
import no.nav.security.mock.oauth2.token.DefaultOAuth2TokenCallback;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ActiveProfiles("it")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureObservability
@Testcontainers
public abstract class AbstractContainerBaseTest {

    private static final String KC_ISSUERID = "fake-taskmgr-iss";
    private static final String KC_AUD = "account";
    private static String kcIssuerUrl;
    private static String kcJwksUrl;

    protected static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER =
            new PostgreSQLContainer<>("postgres:latest")
                    .withDatabaseName("std-task-mgr-test-db")
                    .withUsername("std-task-mgr-test")
                    .withPassword("123456")
                    .withReuse(true);

    protected static final MockOAuth2Server MOCK_OAUTH2_SERVER = new MockOAuth2Server();


    static {
        POSTGRE_SQL_CONTAINER.start();
        MOCK_OAUTH2_SERVER.start();
    }

    @DynamicPropertySource
    public static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRE_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRE_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRE_SQL_CONTAINER::getPassword);

        kcIssuerUrl = MOCK_OAUTH2_SERVER.issuerUrl(KC_ISSUERID).toString();
        registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri", () -> kcIssuerUrl);
        kcJwksUrl = MOCK_OAUTH2_SERVER.jwksUrl(KC_ISSUERID).toString();
        registry.add("spring.security.oauth2.resourceserver.jwt.jwk-set-uri", () -> kcJwksUrl);
    }

    protected final String getToken(String userId, String email) {
        SignedJWT signedJWT = MOCK_OAUTH2_SERVER.issueToken(KC_ISSUERID, "default",
                new DefaultOAuth2TokenCallback(
                        KC_ISSUERID,
                        userId,
                        JOSEObjectType.JWT.toString(),
                        new ArrayList<>(List.of(KC_AUD)),
                        new HashMap<>(Map.of(
                                "sub", userId,
                                "aud", KC_AUD,
                                "email", email
                        ))
                ));
        return "Bearer " + signedJWT.serialize();
    }

}
