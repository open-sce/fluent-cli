package com.ksm.domino.cli.command;

import java.net.URI;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.dominodatalab.client.DominoApiClient;
import com.dominodatalab.client.DominoPublicClient;
import com.dominodatalab.client.TrustAllManager;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.StreamReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.toml.TomlMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ksm.domino.cli.Domino;

import picocli.CommandLine.Option;

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
        } catch (com.dominodatalab.api.invoker.ApiException|com.dominodatalab.pub.invoker.ApiException ex) {
            ExceptionUtils.printRootCauseStackTrace(ex);
            throw new RuntimeException(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static ObjectMapper explicitOM() {
        JsonMapper mapper = JsonMapper.builder()
            .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
            .enable(StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION)
            .build();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
        mapper.enable(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS);
        mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        JavaTimeModule module = new JavaTimeModule();
        mapper.registerModule(module);
        return mapper;
    }

    /**
     * Create the API Client for accessing Domino over HTTP.
     *
     * @return the {@link com.dominodatalab.api.invoker.ApiClient}
     */
    public com.dominodatalab.api.invoker.ApiClient getApiClient(Domino domino) {
        HttpClient.Builder httpClient = HttpClient.newBuilder().sslContext(TrustAllManager.createSslContext());
        com.dominodatalab.api.invoker.ApiClient client = DominoApiClient.createApiClient();
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
     * Create the API Client for accessing Domino over HTTP.
     *
     * @return the {@link com.dominodatalab.pub.invoker.ApiClient}
     */
    public com.dominodatalab.pub.invoker.ApiClient getPubClient(Domino domino) {
        HttpClient.Builder httpClient = HttpClient.newBuilder().sslContext(TrustAllManager.createSslContext());
        com.dominodatalab.pub.invoker.ApiClient client = DominoPublicClient.createApiClient();
        client.setHttpClientBuilder(httpClient);
        client.setReadTimeout(Duration.ofSeconds(domino.timeoutSeconds));
        client.updateBaseUri(domino.apiUrl);
        //TODO ObjectMapper is overridden, this should ideally be controlled from the domino-java-client
        client.setObjectMapper(explicitOM());
        client.setRequestInterceptor(builder -> builder.setHeader("X-Domino-Api-Key", domino.apiKey));

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
                mapper = DominoApiClient.createDefaultObjectMapper();
                break;
        }
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
