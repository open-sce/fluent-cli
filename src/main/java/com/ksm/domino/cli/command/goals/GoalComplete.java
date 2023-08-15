package com.ksm.domino.cli.command.goals;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dominodatalab.api.model.DominoProjectManagementWebLinkJobToGoalRequest;
import com.dominodatalab.api.model.DominoProjectsApiProjectGoal;
import com.dominodatalab.api.rest.ProjectsApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine;
import picocli.CommandLine.ParentCommand;

@CommandLine.Command(name = "complete", header = "%n@|green Mark a project goal as completed.|@")
public class GoalComplete extends AbstractDominoCommand {

    @ParentCommand
    private Goal parent;

    private static final String NAME = "goal complete";

    @CommandLine.Parameters(description = "@|blue Required Parameters:%n projectId=123%n goalId=456%n|@")
    private final Map<String, String> parameters = new LinkedHashMap<>();

    @Override
    public void execute() throws Exception {
        String goalId = getRequiredParam(parameters,
                    DominoProjectManagementWebLinkJobToGoalRequest.JSON_PROPERTY_GOAL_ID, NAME);
        String projectId = getRequiredParam(parameters,
                    DominoProjectManagementWebLinkJobToGoalRequest.JSON_PROPERTY_PROJECT_ID, NAME);

        ProjectsApi api = new ProjectsApi(getApiClient(parent.domino));
        DominoProjectsApiProjectGoal response = api.markProjectGoalComplete(projectId, goalId);
        output(response, parent.domino);
    }
}
