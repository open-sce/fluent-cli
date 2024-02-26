package com.ksm.domino.cli.command.project;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.Validate;

import com.dominodatalab.pub.model.CopyProjectSpecV1;
import com.dominodatalab.pub.model.DeepCopyGitRepoSpecV1;
import com.dominodatalab.pub.model.GitCodeRepoSpecV1;
import com.dominodatalab.pub.model.ProjectCopyResultEnvelopeV1;
import com.dominodatalab.pub.model.ProjectVisibilityV1;
import com.dominodatalab.pub.model.ProviderRepoVisibilityV1;
import com.dominodatalab.pub.model.ReferenceCopyGitRepoSpecV1;
import com.dominodatalab.pub.rest.ProjectsApi;
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
    private static final String PARAM_OWNER_ID = "ownerId";
    private static final String PARAM_CREDENTIAL_ID = "credentialId";
    private static final String PARAM_IMPORTED_CREDENTIAL_ID = "importedCredentialId";
    private static final String PARAM_LINK_REPO_URL = "linkRepoUrl";
    private static final String PARAM_REPO_NAME = "repoName";
    private static final String PARAM_REPO_OWNER = "repoOwner";
    private static final String PARAM_COPY_DATASETS = "copyDatasets";

    @CommandLine.Parameters(description = "@|blue Required Parameters:%nname=projectName%nownerId=6125...%nprojectToCopyId=1235...%ncredentialId=6d321...%n%nOptional Parameters:%nimportedCredentialId=6e124...%nlinkRepoUrl=https://github.com/...%nrepoName=domino-project%nrepoOwner=user%ncopyDatasets|@")
    private final Map<String, String> parameters = new LinkedHashMap<>();

    @Override
    public void execute() throws Exception {

        String projectToCopyId = getRequiredParam(parameters, PARAM_COPY_PROJECT_ID, NAME);
        String projectName = getRequiredParam(parameters, PARAM_NAME, NAME);
        String ownerId = getRequiredParam(parameters, PARAM_OWNER_ID, NAME);
        String credentialId = getRequiredParam(parameters, PARAM_CREDENTIAL_ID, NAME);
        Boolean copyDatasets = parameters.containsKey(PARAM_COPY_DATASETS);

        String importedCredentialId = parameters.getOrDefault(PARAM_IMPORTED_CREDENTIAL_ID, credentialId);

        // link existing repo
        Optional<String> linkRepoUrl = Optional.ofNullable(parameters.get(PARAM_LINK_REPO_URL));

        // create new repo
        Optional<String> repoName = Optional.ofNullable(parameters.get(PARAM_REPO_NAME));
        Optional<String> repoOwner = Optional.ofNullable(parameters.get(PARAM_REPO_OWNER));

        GitCodeRepoSpecV1 repoSpec = new GitCodeRepoSpecV1();
        repoSpec.setCredentialId(credentialId);

        // - enforce link xor new repo
        if (linkRepoUrl.isPresent() && repoName.isEmpty() && repoOwner.isEmpty()) {
            ReferenceCopyGitRepoSpecV1 refSpec = new ReferenceCopyGitRepoSpecV1();
            refSpec.setMainRepoUrl(linkRepoUrl.get());

            repoSpec.setReferenceCopy(refSpec);
        } else if (repoName.isPresent()) {
            Validate.notBlank(repoOwner.orElse(null), "Parameter 'repoOwner' is required if 'repoName' is specified");
            DeepCopyGitRepoSpecV1 copySpec = new DeepCopyGitRepoSpecV1();
            copySpec.setVisibility(ProviderRepoVisibilityV1.PRIVATE);
            copySpec.setNewRepoName(repoName.get());
            copySpec.setNewRepoOwnerName(repoOwner.get());

            repoSpec.setDeepCopy(copySpec);
        } else {
            throw new IllegalArgumentException("Must specify to link an existing git repo or copy from the template project's template repository: use parameter 'linkRepoUrl' or parameters 'repoName' and 'repoOwner'");
        }

        // Create base request - assume Private, no tags or collaborators (can be added after)
        CopyProjectSpecV1 request = new CopyProjectSpecV1();
        request.setName(projectName);
        request.setCopyDatasets(copyDatasets);
        request.setOwnerId(ownerId);
        request.setVisibility(ProjectVisibilityV1.PRIVATE);
        request.setGitCodeRepoSpec(repoSpec);
        request.setImportedGitReposCredentialId(importedCredentialId);

        ProjectsApi projectsApi = new ProjectsApi(getPubClient(parent.domino));

        ProjectCopyResultEnvelopeV1 project = projectsApi.copyProject(projectToCopyId, request);
        output(project, parent.domino);

    }
}
