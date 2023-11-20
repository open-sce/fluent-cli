package com.ksm.domino.cli.command;

import com.dominodatalab.api.invoker.ApiClient;
import com.dominodatalab.api.invoker.ApiException;
import com.dominodatalab.client.TrustAllManager;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.toml.TomlMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ksm.domino.cli.Domino;
import org.apache.commons.lang3.StringUtils;
import picocli.CommandLine.Option;

import java.net.URI;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Map;

/**
 * Abstract base class that any command that needs to access Domino should extend.
 */
public abstract class AbstractDominoCommand implements Runnable {
    /**
     * Default target path of the Domino API
     */
    public static String DEFAULT_DOMINO_API_BASE_PATH = "/v4";

    @Option(names = {"-h", "--help"}, usageHelp = true, description = "Print usage help and exit.")
    private boolean usageHelpRequested;

    /**
     * Method that executes this command.
     *
     * @throws Exception if any error occurs
     */
    public abstract void execute() throws Exception;

    @Override
    public void run() {
        try {
            execute();
        } catch (ApiException ex) {
            throw new RuntimeException(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Create the API Client for accessing Domino over HTTP.
     *
     * @return the {@link ApiClient}
     */
    public ApiClient getApiClient(Domino domino) {
        HttpClient.Builder httpClient = HttpClient.newBuilder().sslContext(TrustAllManager.createSslContext());
        ApiClient client = new ApiClient();
        client.setHttpClientBuilder(httpClient);
        client.setReadTimeout(Duration.ofSeconds(domino.timeoutSeconds));
        client.updateBaseUri(domino.apiUrl);
        client.setRequestInterceptor(builder -> builder.setHeader("X-Domino-Api-Key", domino.apiKey));

        String basePath = URI.create(client.getBaseUri()).getRawPath();
        if (StringUtils.isBlank(basePath)) {
            client.setBasePath(DEFAULT_DOMINO_API_BASE_PATH);
        }
        return client;
    }

    /**
     * Output this result to the console.
     *
     * @param o      object to output to console
     * @param domino the root Domino command, required for its common options
     * @throws JsonProcessingException if any error occurs
     */
    public void output(Object o, Domino domino) throws JsonProcessingException {
        if (o == null) {
            return;
        }

        if (o instanceof String) {
            System.out.println(o);
            return;
        }

        ObjectMapper mapper;
        switch (domino.outputFormat) {
            case TEXT:
                mapper = new TomlMapper();
                break;
            case XML:
                mapper = new XmlMapper();
                break;
            case JSON:
            default:
                // default to JSON mapper
                mapper = new ObjectMapper();
                break;
        }
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
        mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        mapper.registerModule(new JavaTimeModule());
        String result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
        System.out.println(result);
    }

    public String getRequiredParam(Map<String, String> parameters, String parameterName, String command) {
        String param = parameters.get(parameterName);
        if (StringUtils.isBlank(param)) {
            throw new IllegalArgumentException(
                    String.format("Missing the required parameter '%s' when calling '%s'.", parameterName, command));
        }
        return param;
    }
}