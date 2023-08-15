package com.ksm.domino.cli.command.job;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dominodatalab.api.model.DominoJobsInterfaceJob;
import com.dominodatalab.api.rest.JobsApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine;
import picocli.CommandLine.ParentCommand;

@CommandLine.Command(name = "get", header = "%n@|green Retrieves a single job by job id.|@")
public class JobGet extends AbstractDominoCommand {

    @ParentCommand
    private Job parent;

    private static final String NAME = "job get";

    @CommandLine.Parameters(description = "@|blue Required parameters:%n jobId=12345%n|@%n", mapFallbackValue = "")
    private final Map<String, String> parameters = new LinkedHashMap<>(3);

    @Override
    public void execute() throws Exception {
        String jobId = getRequiredParam(parameters, "jobId", NAME);
        JobsApi api = new JobsApi(getApiClient(parent.domino));
        DominoJobsInterfaceJob job = api.getJob(jobId);
        output(job, parent.domino);
    }
}
