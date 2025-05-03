package br.com.diegosneves.tabnewsserver.dto;

import java.time.LocalDateTime;

public record HealthCheckDTO(
        String status,
        LocalDateTime timestamp,
        String version
) {

    public static HealthCheckDTO of(String status, LocalDateTime timestamp, String version) {
        return new HealthCheckDTO(status, timestamp, version);
    }

    public static HealthCheckDTO of(String status, String version) {
        return HealthCheckDTO.of(status, LocalDateTime.now(), version);
    }

}
