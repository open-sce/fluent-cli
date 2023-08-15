package com.ksm.domino.cli.command.job;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import com.dominodatalab.api.model.DominoJobsInterfaceLogsWithProblemSuggestion;
import com.dominodatalab.api.rest.LogsWithProblemSuggestionApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine;
import picocli.CommandLine.ParentCommand;

@CommandLine.Command(name = "logs", header = "%n@|green Get the suggestion when problem occurs in a job along with the logs.|@")
public class JobLogs extends AbstractDominoCommand {

    @ParentCommand
    private Job parent;    

    private static final String NAME = "job logs";

    @CommandLine.Parameters(description = "@|blue Required parameters:%n jobId=12345%n logType='console'%n limit=10000%n offset=0%n latestTimeNano=0%n |@%n", mapFallbackValue = "")
    private final Map<String, String> parameters = new LinkedHashMap<>(3);

    @Override
    public void execute() throws Exception {
        String jobId = getRequiredParam(parameters, "jobId", NAME);
        String logType = parameters.getOrDefault("logType", "console");
        BigDecimal limit = new BigDecimal(parameters.getOrDefault("limit", "10000"));
        BigDecimal offset = new BigDecimal(parameters.getOrDefault("offset", "0"));
        String latestTimeNano = parameters.getOrDefault("latestTimeNano", "0");
        LogsWithProblemSuggestionApi api = new LogsWithProblemSuggestionApi(getApiClient(parent.domino));
        DominoJobsInterfaceLogsWithProblemSuggestion log = api.getLogsWithProblemSuggestions(jobId, logType,
                    limit, offset, latestTimeNano);
        output(log, parent.domino);
    }
}
