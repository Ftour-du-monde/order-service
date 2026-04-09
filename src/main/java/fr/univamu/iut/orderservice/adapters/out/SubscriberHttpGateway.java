package fr.univamu.iut.orderservice.adapters.out;

import fr.univamu.iut.orderservice.domain.Subscriber;
import fr.univamu.iut.orderservice.exception.SubscriberNotFoundException;
import fr.univamu.iut.orderservice.usecase.port.SubscriberGateway;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Output Adapter: Makes HTTP calls to the external Subscriber/User Microservice.
 *
 * Role: Implements the SubscriberGateway to fetch data over an HTTP API. Validates that a user exists.
 * Architecture: Interface Adapters layer. Resolves the UseCase's need without the UseCase knowing it's HTTP.
 */
public class SubscriberHttpGateway implements SubscriberGateway {
    private final String baseUrl;
    private final HttpClient httpClient;
    private final Jsonb jsonb;

    public SubscriberHttpGateway(String baseUrl) {
        this.baseUrl = baseUrl;
        this.httpClient = HttpClient.newHttpClient();
        this.jsonb = JsonbBuilder.create();
    }

    @Override
    public Subscriber findSubscriberById(int subscriberId) {
        try {
            String url = baseUrl + "/utilisateurs/" + subscriberId;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 404) {
                throw new SubscriberNotFoundException(subscriberId);
            }
            if (response.statusCode() != 200) {
                throw new RuntimeException("Error fetching Subscriber API - HTTP Code: " + response.statusCode());
            }

            SubscriberApiResponse apiResponse = jsonb.fromJson(response.body(), SubscriberApiResponse.class);
            return new Subscriber(apiResponse.getId(), apiResponse.getNom(), apiResponse.getPrenom());

        } catch (SubscriberNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("HTTP error when fetching subscriber " + subscriberId, e);
        }
    }

    static class SubscriberApiResponse {
        private int id;
        private String nom;
        private String prenom;

        public SubscriberApiResponse() {}

        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public String getNom() { return nom; }
        public void setNom(String nom) { this.nom = nom; }
        public String getPrenom() { return prenom; }
        public void setPrenom(String prenom) { this.prenom = prenom; }
    }
}
