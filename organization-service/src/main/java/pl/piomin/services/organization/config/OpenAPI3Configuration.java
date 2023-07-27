package pl.piomin.services.organization.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(info = @Info(title = "Organization Service APIs", description = "This lists all the Organization Service API Calls. The Calls are OAuth2 secured, "
        + "so please use your client ID and Secret to test them out.",
        version = "v1.0"))
@SecurityScheme(name = "security_auth", type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(clientCredentials  = @OAuthFlow(tokenUrl = "${openapi.oAuthFlow.tokenUrl}", scopes = {
                @OAuthScope(name = "openid", description = "openid scope")
        })))
public class OpenAPI3Configuration {

}