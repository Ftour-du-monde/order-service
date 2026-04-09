package fr.univamu.iut.orderservice.dto;

/**
 * Standardized DTO for error responses.
 *
 * Role: Ensures consistent structure for HTTP API error payloads.
 * Architecture: Interface Adapters layer.
 */
public class ErrorResponse {
    private String error;

    public ErrorResponse() {}
    public ErrorResponse(String error) { this.error = error; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
}
