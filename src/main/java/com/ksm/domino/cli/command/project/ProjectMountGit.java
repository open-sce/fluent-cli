package com.ksm.domino.cli.command.project;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.Validate;

import com.dominodatalab.api.model.DominoProjectsApiRepositoriesGitRepositoryDTO;
import com.dominodatalab.api.model.DominoProjectsApiRepositoriesReferenceDTO;
import com.dominodatalab.api.rest.ProjectsApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine;
import picocli.CommandLine.ParentCommand;

@CommandLine.Command(name = "mount-git", header = "%n@|green Mounts a secondary git repository to a project|@")
public class ProjectMountGit extends AbstractDominoCommand {
    
    @ParentCommand
    private Project parent;    

    private static final String NAME = "project mount-git";
    private static final String REPOSITORY_PROVIDER = "githubEnterprise";

    @CommandLine.Parameters(description = "@|blue Required Parameters:%nprojectId=1234.....%nmainRepoUrl=https://mygithub.gsk.com/mudid/repo-name%ncredentialId=61de.....%nOptional Parameters:%nrepoProvider=githubEnterprise%nrefType=tag%nrefValue=v1.1.0|@")
    private final Map<String, String> parameters = new LinkedHashMap<>();

    @Override
    public void execute() throws Exception {
        String projectId = getRequiredParam(parameters, "projectId", NAME);

        String mainRepoUrl = getRequiredParam(parameters, "mainRepoUrl", NAME);
        String repoProvider = parameters.getOrDefault("repoProvider", REPOSITORY_PROVIDER);
        String credentialId = getRequiredParam(parameters, DominoProjectsApiRepositoriesGitRepositoryDTO.JSON_PROPERTY_CREDENTIAL_ID, NAME);

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

        DominoProjectsApiRepositoriesGitRepositoryDTO gitRepository = new DominoProjectsApiRepositoriesGitRepositoryDTO();
        gitRepository.setUri(mainRepoUrl);
        gitRepository.setServiceProvider(repoProvider);
        gitRepository.setCredentialId(credentialId);
        gitRepository.setRef(ref);

        ProjectsApi projectsApi = new ProjectsApi(getApiClient(parent.domino));
        DominoProjectsApiRepositoriesGitRepositoryDTO repository = projectsApi.addGitRepoWithHttpInfo(projectId, gitRepository).getData();

        output(repository, parent.domino);
    }
}
