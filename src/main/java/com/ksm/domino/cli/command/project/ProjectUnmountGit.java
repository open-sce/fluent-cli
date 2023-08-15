package com.ksm.domino.cli.command.project;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dominodatalab.api.rest.ProjectsApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine;
import picocli.CommandLine.ParentCommand;

@CommandLine.Command(name = "unmount-git", header = "%n@|green Unmounts a secondary git repository from a project|@")
public class ProjectUnmountGit extends AbstractDominoCommand {

    @ParentCommand
    private Project parent;    

    private static final String NAME = "project unmount-git";

    @CommandLine.Parameters(description = "@|blue Required Parameters:%nprojectId=1234.....%nrepositoryId=4062.....|@")
    private final Map<String, String> parameters = new LinkedHashMap<>();

    @Override
    public void execute() throws Exception {
        String projectId = getRequiredParam(parameters, "projectId", NAME);
        String repositoryId = getRequiredParam(parameters, "repositoryId", NAME);

        ProjectsApi projectsApi = new ProjectsApi(getApiClient(parent.domino));
        projectsApi.archiveGitRepoWithHttpInfo(projectId, repositoryId);
        output(String.format("Repository %s successfully removed from project %s", repositoryId, projectId), parent.domino);
    }
}
