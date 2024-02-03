package com.ksm.domino.cli.command.project;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.dominodatalab.api.model.DominoCommonModelsEnvironmentVariable;
import com.dominodatalab.api.rest.ProjectsApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;
import picocli.CommandLine;
import picocli.CommandLine.ParentCommand;

@CommandLine.Command(name = "get-env", header = "%n@|green Retrieves all environment variables for a project|@")
public class ProjectGetEnv extends AbstractDominoCommand {

    @ParentCommand
    private Project parent;    
    
    private static final String NAME = "project get-env";

    @CommandLine.Parameters(description = "@|blue Required Parameters:%nprojectId=1234.....|@")
    private final Map<String, String> parameters = new LinkedHashMap<>();

    @Override
    public void execute() throws Exception {
        String projectId = getRequiredParam(parameters, "projectId", NAME);
        ProjectsApi projectsApi = new ProjectsApi(getApiClient(parent.domino));
        List<DominoCommonModelsEnvironmentVariable> result = projectsApi.getProjectEnvironmentVariables(projectId);
        output(result, parent.domino);
    }

}