package com.backend.tomagram.config.application;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Abdelrahman Sayed",
                        email = "abdelrahmansayed171@gmail.com",
                        url = "https://www.linkedin.com/in/abdelrahmannafady/"
                ),
                description = "Social Media Backend API documentation",
                title = "TomaGram",
                version = "1.1",
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/license/mit"
                )
        ),
        security = {
                @SecurityRequirement(
                        name="bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER

)
public class OpenApiConfig {
}
