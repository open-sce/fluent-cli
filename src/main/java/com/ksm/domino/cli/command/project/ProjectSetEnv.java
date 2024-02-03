package com.ksm.domino.cli.command.project;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.dominodatalab.api.model.DominoCommonModelsEnvironmentVariable;
import com.dominodatalab.api.model.DominoCommonModelsEnvironmentVariables;
import com.dominodatalab.api.rest.ProjectsApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine;
import picocli.CommandLine.ParentCommand;

@CommandLine.Command(name = "set-env", header = "%n@|green Sets one or more environment variables for a project|@")
public class ProjectSetEnv extends AbstractDominoCommand {

    @ParentCommand
    private Project parent;    

    private static final String NAME = "project set-env";

    @CommandLine.Parameters(description = "@|blue Required Parameters:%nprojectId=1234.....%nDynamic Variable Assignments:%nENV_NAME=env-value|@")
    private final Map<String, String> parameters = new LinkedHashMap<>();

    @Override
    public void execute() throws Exception {
        String projectId = getRequiredParam(parameters, "projectId", NAME);
        parameters.remove("projectId");

        List<DominoCommonModelsEnvironmentVariable> results = new ArrayList<>();
        ProjectsApi projectsApi = new ProjectsApi(getApiClient(parent.domino));

        // iterator over all variables updating 1 at a time
        for (Entry<String, String> param : parameters.entrySet()) {
            final DominoCommonModelsEnvironmentVariable variableDefinition = new DominoCommonModelsEnvironmentVariable();

            variableDefinition.setName(param.getKey());
            variableDefinition.setValue(param.getValue());

            DominoCommonModelsEnvironmentVariable envVar = projectsApi.upsertProjectEnvironmentVariable(projectId, variableDefinition);
            results.add(envVar);
        }

        output(results, parent.domino);
    }
}