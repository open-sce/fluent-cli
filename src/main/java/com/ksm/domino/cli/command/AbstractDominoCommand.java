package com.ksm.domino.cli.command;

import java.net.URI;
import java.time.Duration;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.dominodatalab.client.DominoApiClient;
import com.dominodatalab.client.DominoPublicClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.toml.TomlMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
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

    /**
     * Create the API Client for accessing Domino over HTTP.
     *
     * @return the {@link com.dominodatalab.api.invoker.ApiClient}
     */
    public com.dominodatalab.api.invoker.ApiClient getApiClient(Domino domino) {
        com.dominodatalab.api.invoker.ApiClient client = DominoApiClient.createApiClient();
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
        com.dominodatalab.pub.invoker.ApiClient client = DominoPublicClient.createApiClient();
        client.setReadTimeout(Duration.ofSeconds(domino.timeoutSeconds));
        client.updateBaseUri(domino.apiUrl);
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
