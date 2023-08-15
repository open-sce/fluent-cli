package com.ksm.domino.cli.command.job;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dominodatalab.api.model.DominoJobsInterfaceJob;
import com.dominodatalab.api.model.DominoJobsWebJobStopOperationRequest;
import com.dominodatalab.api.rest.JobsApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine;
import picocli.CommandLine.ParentCommand;

@CommandLine.Command(name = "stop", header = "%n@|green Stops an existing job.|@")
public class JobStop extends AbstractDominoCommand {

    @ParentCommand
    private Job parent;    

    private static final String NAME = "job stop";

    @CommandLine.Parameters(description = "@|blue Parameters:%n projectId=12345%n jobId=456%n commitResults=true%n|@%n", mapFallbackValue = "")
    private final Map<String, String> parameters = new LinkedHashMap<>(6);

    @Override
    public void execute() throws Exception {
        JobsApi api = new JobsApi(getApiClient(parent.domino));
        DominoJobsWebJobStopOperationRequest request = createRequest();
        DominoJobsInterfaceJob job = api.stopJob(request);
        output(job, parent.domino);
    }

    private DominoJobsWebJobStopOperationRequest createRequest() {
        DominoJobsWebJobStopOperationRequest request = new DominoJobsWebJobStopOperationRequest();
        // required
        request.setProjectId(
                    getRequiredParam(parameters, DominoJobsWebJobStopOperationRequest.JSON_PROPERTY_PROJECT_ID, NAME));
        request.setProjectId(
                    getRequiredParam(parameters, DominoJobsWebJobStopOperationRequest.JSON_PROPERTY_JOB_ID, NAME));
        request.setCommitResults(Boolean.parseBoolean(
                    parameters.getOrDefault(DominoJobsWebJobStopOperationRequest.JSON_PROPERTY_COMMIT_RESULTS,
                                "true")));

        return request;
    }
}
