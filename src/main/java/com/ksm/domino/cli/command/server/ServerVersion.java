package com.ksm.domino.cli.command.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.dominodatalab.api.invoker.ApiClient;
import com.dominodatalab.api.invoker.ApiException;
import com.dominodatalab.api.invoker.ApiResponse;
import com.dominodatalab.api.model.DominoVersion;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine;
import picocli.CommandLine.ParentCommand;

@CommandLine.Command(name = "version", header = "%n@|green Retrieves the remote version of the Domino server|@")
public class ServerVersion extends AbstractDominoCommand {

    @ParentCommand
    private Server parent;

    @Override
    public void execute() throws Exception {

        ApiResponse<DominoVersion> version = getDominoVersion();

        output(version.getData(), parent.domino);
    }

    /**
     * Queries Domino API directly for server version.
     * 
     * This method is based on generated OpenAPI code for the Domino APIs.
     */
    private ApiResponse<DominoVersion> getDominoVersion() throws ApiException {
        ApiClient client = getApiClient(parent.domino);

        // Version endpoint is not on the base path like defined API endpoints
        client.setBasePath("");

        HttpClient http = client.getHttpClient();
        HttpRequest.Builder versionRequestBuilder = HttpRequest.newBuilder();

        try {
            versionRequestBuilder.uri(URI.create(client.getBaseUri() + "/version"));

            versionRequestBuilder.header("Content-Type", "application/json");
            versionRequestBuilder.header("Accept", "application/json");
            versionRequestBuilder.method("GET", HttpRequest.BodyPublishers.noBody());

            HttpResponse<InputStream> versionResponse = http.send(versionRequestBuilder.build(),
                    HttpResponse.BodyHandlers.ofInputStream());

            if (versionResponse.statusCode() / 100 != 2) {
                throw getApiException("getServerVersion", versionResponse);
            }

            return new ApiResponse<DominoVersion>(
                    versionResponse.statusCode(),
                    versionResponse.headers().map(),
                    client.getObjectMapper().readValue(versionResponse.body(), DominoVersion.class));

        } catch (IOException e) {
            throw new ApiException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ApiException(e);
        } finally {
        }
    }

    private ApiException getApiException(String operationId, HttpResponse<InputStream> response) throws IOException {
        String body = response.body() == null ? null : new String(response.body().readAllBytes());
        String message = formatExceptionMessage(operationId, response.statusCode(), body);
        return new ApiException(response.statusCode(), message, response.headers(), body);
    }

    private String formatExceptionMessage(String operationId, int statusCode, String body) {
        if (body == null || body.isEmpty()) {
            body = "[no body]";
        }
        return operationId + " call failed with: " + statusCode + " - " + body;
    }
}
