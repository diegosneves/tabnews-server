package br.com.diegosneves.tabnewsserver.controller;

import br.com.diegosneves.tabnewsserver.dto.HealthCheckDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
@Tag(name = "Health Check")
public class HealthCheckController {

    private static final String HEALTH_CHECK_STATUS_UP = "UP";
    private static final String HEALTH_CHECK_OPERATION_SUMMARY = "Health Check";
    private static final String HEALTH_CHECK_DESCRIPTION = "Verifica o status da aplicação e retorna informações de versão";

    private final String appVersion;

    public HealthCheckController(@Value("${application.version}") String appVersion) {
        this.appVersion = appVersion;
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = HEALTH_CHECK_OPERATION_SUMMARY, description = HEALTH_CHECK_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aplicação funcionando normalmente", content = @Content(schema = @Schema(implementation = HealthCheckDTO.class)))
    })
    public ResponseEntity<HealthCheckDTO> checkHealth() {
        final var response = HealthCheckDTO.of(HEALTH_CHECK_STATUS_UP, this.appVersion);
        return ResponseEntity.ok(response);
    }

}
