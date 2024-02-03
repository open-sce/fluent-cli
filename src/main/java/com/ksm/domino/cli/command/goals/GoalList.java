package com.ksm.domino.cli.command.goals;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.dominodatalab.api.model.DominoProjectsApiProjectGoal;
import com.dominodatalab.api.rest.ProjectsApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine;
import picocli.CommandLine.ParentCommand;

@CommandLine.Command(name = "list", header = "%n@|green Gets the current goals for a project.|@")
public class GoalList extends AbstractDominoCommand {

    @ParentCommand
    private Goal parent;

    private static final String NAME = "goal list";

    @CommandLine.Parameters(description = "@|blue Required Parameters:%n projectId=12345|@")
    private final Map<String, String> parameters = new LinkedHashMap<>();

    @Override
    public void execute() throws Exception {
        String projectId = getRequiredParam(parameters, "projectId", NAME);
        ProjectsApi projectsApi = new ProjectsApi(getApiClient(parent.domino));
        List<DominoProjectsApiProjectGoal> goals = projectsApi.getProjectGoals(projectId);
        output(goals, parent.domino);
    }
}