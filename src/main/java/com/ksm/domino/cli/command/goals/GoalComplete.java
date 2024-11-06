package com.ksm.domino.cli.command.goals;

import com.dominodatalab.api.model.DominoProjectsApiProjectGoal;
import com.dominodatalab.api.rest.ProjectsApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine;
import picocli.CommandLine.ParentCommand;

@CommandLine.Command(name = "complete", header = "%n@|green Mark a project goal as completed.|@")
public class GoalComplete extends AbstractDominoCommand {

    @ParentCommand
    private Goal parent;

    @CommandLine.Option(names = {"--project-id"}, description = "ID of the project%n", required = true, order = -2)
    private String projectId;

    @CommandLine.Option(names = {"--goal-id"}, description = "ID of the goal to complete%n", required = true, order = -2)
    private String goalId;

    @Override
    public void execute() throws Exception {
        ProjectsApi api = new ProjectsApi(getApiClient(parent.domino));
        DominoProjectsApiProjectGoal response = api.markProjectGoalComplete(projectId, goalId);
        output(response, parent.domino);
    }
}
