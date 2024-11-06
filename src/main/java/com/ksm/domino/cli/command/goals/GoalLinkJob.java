package com.ksm.domino.cli.command.goals;

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

    @CommandLine.Option(names = {"--goal-id"}, description = "ID of the goal to link to%n", required = true, order = -2)
    private String goalId;

    @CommandLine.Option(names = {"--job-id"}, description = "ID of the job to be linked%n", required = true, order = -2)
    private String jobId;

    @Override
    public void execute() throws Exception {
        DominoProjectManagementWebLinkJobToGoalRequest request = new DominoProjectManagementWebLinkJobToGoalRequest();
        request.setGoalId(goalId);

        ProjectManagementApi api = new ProjectManagementApi(getApiClient(parent.domino));
        DominoProjectManagementApiResponseMessage response = api.linkJobToGoal(jobId, request);
        output(response, parent.domino);
    }
}
