package fr.univamu.iut.orderservice.adapters.out;

import fr.univamu.iut.orderservice.domain.Menu;
import fr.univamu.iut.orderservice.exception.MenuNotFoundException;
import fr.univamu.iut.orderservice.usecase.port.MenuClientPort;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Output Adapter: HTTP client implementation for fetching Menus.
 *
 * Role: Submits an HTTP request to the external Menu service and returns mapped domain models.
 * Architecture: Interface Adapters layer. Implements MenuClientPort defined by the Application layer.
 */
public class MenuClientAdapter implements MenuClientPort {
    private final String baseUrl;
    private final HttpClient httpClient;
    private final Jsonb jsonb;

    public MenuClientAdapter(String baseUrl) {
        this.baseUrl = baseUrl;
        this.httpClient = HttpClient.newHttpClient();
        this.jsonb = JsonbBuilder.create();
    }

    @Override
    public Menu getMenuById(int menuId) {
        try {
            String url = baseUrl + "/menus/" + menuId;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 404) {
                throw new MenuNotFoundException(menuId);
            }
            if (response.statusCode() != 200) {
                throw new RuntimeException("Error Menus API - HTTP Code: " + response.statusCode());
            }

            MenuApiResponse menuApi = jsonb.fromJson(response.body(), MenuApiResponse.class);
            return new Menu(menuApi.getId(), menuApi.getNom(), menuApi.getPrixTotal());

        } catch (MenuNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error during call to Menus API for menu " + menuId, e);
        }
    }

    static class MenuApiResponse {
        private int id;
        private String nom;
        private double prixTotal;

        public MenuApiResponse() {}

        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public String getNom() { return nom; }
        public void setNom(String nom) { this.nom = nom; }
        public double getPrixTotal() { return prixTotal; }
        public void setPrixTotal(double prixTotal) { this.prixTotal = prixTotal; }
    }
}
