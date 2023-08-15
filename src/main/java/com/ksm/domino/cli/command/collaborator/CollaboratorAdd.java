package com.ksm.domino.cli.command.collaborator;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Parameters;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.text.WordUtils;

import com.dominodatalab.api.model.DominoNucleusProjectModelsCollaborator;
import com.dominodatalab.api.rest.ProjectsApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine.ParentCommand;

@Command(name = "add", header = "%n@|green Adds a user or organization to Project as a collaborator|@")
public class CollaboratorAdd extends AbstractDominoCommand {

    @ParentCommand
    private Collaborator parent;

    private static final String NAME = "collaborator add";

    @Parameters(description = "@|blue Required parameters:%n projectId=123%n collaboratorId=hsimpson%n role=Contributor%n|@%n", mapFallbackValue = "")
    private final Map<String, String> parameters = new LinkedHashMap<>(3);

    @Override
    public void execute() throws Exception {
        // validate parameters
        String projectId = getRequiredParam(parameters, "projectId", NAME);
        String collaboratorId = getRequiredParam(parameters,
                    DominoNucleusProjectModelsCollaborator.JSON_PROPERTY_COLLABORATOR_ID, NAME);
        String role = WordUtils.capitalize(getRequiredParam(parameters, "role", NAME));

        // create the model object
        final DominoNucleusProjectModelsCollaborator collaborator = new DominoNucleusProjectModelsCollaborator();
        collaborator.setCollaboratorId(collaboratorId);
        collaborator.setProjectRole(DominoNucleusProjectModelsCollaborator.ProjectRoleEnum.fromValue(role));

        // execute the API
        final ProjectsApi api = new ProjectsApi(getApiClient(parent.domino));
        final DominoNucleusProjectModelsCollaborator result = api.addCollaborator(projectId, collaborator);
        output(result, parent.domino);
    }
}
