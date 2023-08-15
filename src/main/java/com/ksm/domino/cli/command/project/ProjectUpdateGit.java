package com.ksm.domino.cli.command.project;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.Validate;

import com.dominodatalab.api.model.DominoProjectsApiRepositoriesReferenceDTO;
import com.dominodatalab.api.rest.ProjectsApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine;
import picocli.CommandLine.ParentCommand;

@CommandLine.Command(name = "update-git", header = "%n@|green Updates a git repository ref in a project|@")
public class ProjectUpdateGit extends AbstractDominoCommand {

    @ParentCommand
    private Project parent;    

    private static final String NAME = "project update-git";

    @CommandLine.Parameters(description = "@|blue Required Parameters:%nprojectId=1234.....%nrepositoryId=2502.....%nOptional Parameters:%nrefType=tag%nrefValue=v1.1.0|@")
    private final Map<String, String> parameters = new LinkedHashMap<>();

    @Override
    public void execute() throws Exception {
        String projectId = getRequiredParam(parameters, "projectId", NAME);
        String repositoryId = getRequiredParam(parameters, "repositoryId", NAME);

        DominoProjectsApiRepositoriesReferenceDTO ref = new DominoProjectsApiRepositoriesReferenceDTO();
        String refType = parameters.get("refType");
        String refValue = parameters.get("refValue");
        if (refType == null) {
            ref.setType("head");
        } else {
            Validate.notBlank(refType, "Parameter 'refType' is required if 'refValue' is specified");
            Validate.notBlank(refValue, "Parameter 'refValue' is required if 'refType' is specified");

            ref.setType(refType);
            ref.setValue(refValue);
        }

        ProjectsApi projectsApi = new ProjectsApi(getApiClient(parent.domino));
        projectsApi.updateGitRepositoryDefaultRefWithHttpInfo(projectId, repositoryId, ref);

        output(String.format("Repository %s successfully updated for project %s", repositoryId, projectId), parent.domino);
    }
}
