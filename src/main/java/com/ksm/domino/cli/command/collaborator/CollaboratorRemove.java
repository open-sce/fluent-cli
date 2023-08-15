package com.ksm.domino.cli.command.collaborator;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Parameters;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dominodatalab.api.model.DominoNucleusProjectModelsCollaborator;
import com.dominodatalab.api.rest.ProjectsApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine.ParentCommand;

@Command(name = "remove", header = "%n@|green Remove a collaborator from a Project|@")
public class CollaboratorRemove extends AbstractDominoCommand {

    @ParentCommand
    private Collaborator parent;

    private static final String NAME = "collaborator remove";

    @Parameters(description = "@|blue Required parameters:%n projectId=123%n collaboratorId=hsimpson%n|@%n", mapFallbackValue = "")
    private final Map<String, String> parameters = new LinkedHashMap<>(3);

    @Override
    public void execute() throws Exception {
        // validate parameters
        String projectId = getRequiredParam(parameters, "projectId", NAME);
        String collaboratorId = getRequiredParam(parameters,
                    DominoNucleusProjectModelsCollaborator.JSON_PROPERTY_COLLABORATOR_ID, NAME);

        // execute the API
        final ProjectsApi api = new ProjectsApi(getApiClient(parent.domino));
        api.removeCollaborator(projectId, collaboratorId);
        output(String.format("Collaborator %s successfully removed from project %s", collaboratorId, projectId), parent.domino);
    }
}
