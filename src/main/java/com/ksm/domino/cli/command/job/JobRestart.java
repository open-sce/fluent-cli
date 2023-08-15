package com.ksm.domino.cli.command.job;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dominodatalab.api.model.DominoJobsInterfaceJob;
import com.dominodatalab.api.model.DominoJobsWebJobRestartOperationRequest;
import com.dominodatalab.api.rest.JobsApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine;
import picocli.CommandLine.ParentCommand;

@CommandLine.Command(name = "restart", header = "%n@|green Restarts an existing job.|@")
public class JobRestart extends AbstractDominoCommand {

    @ParentCommand
    private Job parent;    

    private static final String NAME = "job restart";

    @CommandLine.Parameters(description = "@|blue Parameters:%n projectId=12345%n jobId=456%n shouldUseOriginalInputCommit=true%n|@%n", mapFallbackValue = "")
    private final Map<String, String> parameters = new LinkedHashMap<>(6);

    @Override
    public void execute() throws Exception {
        JobsApi api = new JobsApi(getApiClient(parent.domino));
        DominoJobsWebJobRestartOperationRequest request = createRequest();
        DominoJobsInterfaceJob job = api.restartJob(request);
        output(job, parent.domino);
    }

    private DominoJobsWebJobRestartOperationRequest createRequest() {
        DominoJobsWebJobRestartOperationRequest request = new DominoJobsWebJobRestartOperationRequest();
        // required
        request.setProjectId(
                    getRequiredParam(parameters, DominoJobsWebJobRestartOperationRequest.JSON_PROPERTY_PROJECT_ID,
                                NAME));
        request.setProjectId(
                    getRequiredParam(parameters, DominoJobsWebJobRestartOperationRequest.JSON_PROPERTY_JOB_ID, NAME));
        request.setShouldUseOriginalInputCommit(Boolean.parseBoolean(
                    parameters.getOrDefault(
                                DominoJobsWebJobRestartOperationRequest.JSON_PROPERTY_SHOULD_USE_ORIGINAL_INPUT_COMMIT,
                                "true")));

        return request;
    }
}
