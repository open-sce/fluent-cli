package com.ksm.domino.cli.command.project;

import java.util.LinkedHashMap;
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
    private final DominoCommonModelsEnvironmentVariables variables = new DominoCommonModelsEnvironmentVariables();

    @Override
    public void execute() throws Exception {
        String projectId = getRequiredParam(parameters, "projectId", NAME);
        parameters.remove("projectId");

        // Build list of envvars to set
        parameters.entrySet().stream().forEach(this::addVarToList);

        ProjectsApi projectsApi = new ProjectsApi(getApiClient(parent.domino));
        DominoCommonModelsEnvironmentVariables result = projectsApi.setProjectEnvironmentVariables(projectId, variables);

        output(result, parent.domino);
    }

    /**
     * Creates a Domino environment variable from a parameter entry and adds it to the variables list
     */
    private void addVarToList(Entry<String, String> parameter) {
        final DominoCommonModelsEnvironmentVariable variableDefinition = new DominoCommonModelsEnvironmentVariable();

        variableDefinition.setName(parameter.getKey());
        variableDefinition.setValue(parameter.getValue());

        // Add new variable to variables list
        variables.addVarsItem(variableDefinition);
    }
}
