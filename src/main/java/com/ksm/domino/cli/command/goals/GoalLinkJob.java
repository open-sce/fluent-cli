package com.ksm.domino.cli.command.goals;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dominodatalab.api.model.DominoProjectManagementApiResponseMessage;
import com.dominodatalab.api.model.DominoProjectManagementWebLinkJobToGoalRequest;
import com.dominodatalab.api.rest.ProjectManagementApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine;
import picocli.CommandLine.ParentCommand;

@CommandLine.Command(name = "linkjob", header = "%n@|green Link a job to a project goal.|@")
public class GoalLinkJob extends AbstractDominoCommand {

    @ParentCommand
    private Goal parent;

    private static final String NAME = "goal linkjob";

    @CommandLine.Parameters(description = "@|blue Required Parameters:%n projectId=123%n goalId=456%n jobId=789%n|@")
    private final Map<String, String> parameters = new LinkedHashMap<>();

    @Override
    public void execute() throws Exception {
        String jobId = getRequiredParam(parameters, "jobId", NAME);

        DominoProjectManagementWebLinkJobToGoalRequest request = new DominoProjectManagementWebLinkJobToGoalRequest();
        request.setGoalId(getRequiredParam(parameters,
                    DominoProjectManagementWebLinkJobToGoalRequest.JSON_PROPERTY_GOAL_ID, NAME));
        request.setProjectId(getRequiredParam(parameters,
                    DominoProjectManagementWebLinkJobToGoalRequest.JSON_PROPERTY_PROJECT_ID, NAME));

        ProjectManagementApi api = new ProjectManagementApi(getApiClient(parent.domino));
        DominoProjectManagementApiResponseMessage response = api.linkJobToGoal(jobId, request, null);
        output(response, parent.domino);
    }
}
