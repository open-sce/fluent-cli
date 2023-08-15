package com.ksm.domino.cli.command.project;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import com.dominodatalab.api.model.DominoNucleusProjectModelsForkOrCopyProject;
import com.dominodatalab.api.model.DominoNucleusProjectModelsForkOrCopyProject.VisibilityEnum;
import com.dominodatalab.api.model.DominoNucleusProjectModelsProject;
import com.dominodatalab.api.model.DominoProjectsApiCopiedGitRepoMetadata;
import com.dominodatalab.api.model.DominoProjectsApiCopyGitRequest;
import com.dominodatalab.api.model.DominoProjectsApiLinkedGitRepoMetadata;
import com.dominodatalab.api.model.DominoProjectsApiNewTagsDTO;
import com.dominodatalab.api.rest.ProjectsApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine;
import picocli.CommandLine.ParentCommand;

@CommandLine.Command(name = "copy", header = "%n@|green Copies an existing project|@")
public class ProjectCopy extends AbstractDominoCommand {

    @ParentCommand
    private Project parent;

    private static final String NAME = "project copy";
    private static final String PARAM_COPY_PROJECT_ID = "projectToCopyId";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_DESCRIPTION = "description";
    private static final String PARAM_OWNER_ID = "ownerId";
    private static final String PARAM_CREDENTIAL_ID = "credentialId";
    private static final String PARAM_IMPORTED_CREDENTIAL_ID = "importedCredentialId";
    private static final String PARAM_LINK_REPO_URL = "linkRepoUrl";
    private static final String PARAM_REPO_NAME = "repoName";
    private static final String PARAM_REPO_OWNER = "repoOwner";

    @CommandLine.Parameters(description = "@|blue Required Parameters:%nname=projectName%nownerId=6125...%nprojectToCopyId=1235...%ncredentialId=6d321...%n%nOptional Parameters:%ndescription=\"A project description\"%nimportedCredentialId=6e124...%nlinkRepoUrl=https://github.com/...%nrepoName=domino-project%nrepoOwner=user|@")
    private final Map<String, String> parameters = new LinkedHashMap<>();

    @Override
    public void execute() throws Exception {

        String projectToCopyId = getRequiredParam(parameters, PARAM_COPY_PROJECT_ID, NAME);
        String projectName = getRequiredParam(parameters, PARAM_NAME, NAME);
        String ownerId = getRequiredParam(parameters, PARAM_OWNER_ID, NAME);
        String credentialId = getRequiredParam(parameters, PARAM_CREDENTIAL_ID, NAME);

        String description = parameters.getOrDefault(PARAM_DESCRIPTION, StringUtils.EMPTY);
        String importedCredentialId = parameters.getOrDefault(PARAM_IMPORTED_CREDENTIAL_ID, credentialId);

        // link existing repo
        Optional<String> linkRepoUrl = Optional.ofNullable(parameters.get(PARAM_LINK_REPO_URL));

        // create new repo
        Optional<String> repoName = Optional.ofNullable(parameters.get(PARAM_REPO_NAME));
        Optional<String> repoOwner = Optional.ofNullable(parameters.get(PARAM_REPO_OWNER));

        DominoProjectsApiCopyGitRequest gitObject = new DominoProjectsApiCopyGitRequest();
        gitObject.setCredentialId(credentialId);
        gitObject.setImportedGitReposCredentialId(importedCredentialId);
        
        // - enforce link xor new repo
        if (linkRepoUrl.isPresent() && repoName.isEmpty() && repoOwner.isEmpty()) {
            gitObject.setLinkSpec(new DominoProjectsApiLinkedGitRepoMetadata().uri(linkRepoUrl.get()));
        } else if (repoName.isPresent()) {
            Validate.notBlank(repoOwner.orElse(null), "Parameter 'repoOwner' is required if 'repoName' is specified");
            DominoProjectsApiCopiedGitRepoMetadata copySpec = new DominoProjectsApiCopiedGitRepoMetadata();
            copySpec.setIsPrivate(true);
            copySpec.setRepoName(repoName.get());
            copySpec.setOwnerName(repoOwner.get());
            gitObject.setCopySpec(copySpec);
        } else {
            throw new IllegalArgumentException("Must specify to link an existing git repo or copy from the template project's template repository: use parameter 'linkRepoUrl' or parameters 'repoName' and 'repoOwner'");
        }

        // Create base request - assume Private, no tags or collaborators (can be added after)
        DominoNucleusProjectModelsForkOrCopyProject request = new DominoNucleusProjectModelsForkOrCopyProject();
        request.setName(projectName);
        request.setDescription(description);
        request.setOwnerId(ownerId);
        request.setVisibility(VisibilityEnum.PRIVATE);
        request.setTags(new DominoProjectsApiNewTagsDTO());
        request.setCopyGitRequest(gitObject);

        ProjectsApi projectsApi = new ProjectsApi(getApiClient(parent.domino));

        DominoNucleusProjectModelsProject project = projectsApi.copyProject(projectToCopyId, request);
        output(project, parent.domino);

    }
}
