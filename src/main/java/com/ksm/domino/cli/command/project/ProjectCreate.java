package com.ksm.domino.cli.command.project;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import com.dominodatalab.api.model.DominoNucleusProjectModelsNewProject;
import com.dominodatalab.api.model.DominoNucleusProjectModelsProject;
import com.dominodatalab.api.model.DominoProjectsApiCollaboratorDTO;
import com.dominodatalab.api.model.DominoProjectsApiNewTagsDTO;
import com.dominodatalab.api.model.DominoProjectsApiProjectGitRepositoryTemp;
import com.dominodatalab.api.model.DominoProjectsApiRepositoriesReferenceDTO;
import com.dominodatalab.api.rest.ProjectsApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine;
import picocli.CommandLine.ParentCommand;

@CommandLine.Command(name = "create", header = "%n@|green Creates a new project|@")
public class ProjectCreate extends AbstractDominoCommand {

    @ParentCommand
    private Project parent;    

    private static final String NAME = "project create";
    private static final String REPOSITORY_PROVIDER = "github";

    @CommandLine.Parameters(description = "@|blue Required Parameters:%nname=projectName%nownerId=6125...%n%nOptional Parameters:%ndescription=\"A project description\"%nmainRepoUrl=https://github.com/...%ncredentialId=61de...%nrepoProvider=github%ncollaborators=123,456,789|@")
    private final Map<String, String> parameters = new LinkedHashMap<>();

    @Override
    public void execute() throws Exception {
        String projectName = getRequiredParam(parameters, "name", NAME);
        String ownerId = getRequiredParam(parameters, "ownerId", NAME);
        String description = parameters.getOrDefault("description", "");

        DominoProjectsApiNewTagsDTO tags = new DominoProjectsApiNewTagsDTO();
        tags.setTagNames(new ArrayList<>());

        List<DominoProjectsApiCollaboratorDTO> collaboratorDTOS = new ArrayList<>();

        String collaboratorId = parameters.get("collaboratorIds");
        List<String> collaboratorIds;
        if (StringUtils.isNotBlank(collaboratorId)) {
            collaboratorIds = List.of(StringUtils.split(parameters.get("collaboratorIds"), ','));
            collaboratorIds.forEach(id -> {
                DominoProjectsApiCollaboratorDTO collaborator = new DominoProjectsApiCollaboratorDTO();
                collaborator.setCollaboratorId(id);
                collaborator.setProjectRole(DominoProjectsApiCollaboratorDTO.ProjectRoleEnum.CONTRIBUTOR);
                collaboratorDTOS.add(collaborator);
            });
        }

        DominoNucleusProjectModelsNewProject newProject = new DominoNucleusProjectModelsNewProject()
                    .name(projectName)
                    .description(description)
                    .visibility(DominoNucleusProjectModelsNewProject.VisibilityEnum.PRIVATE)
                    .ownerId(ownerId)
                    .tags(tags)
                    .collaborators(collaboratorDTOS);

        if (parameters.containsKey("mainRepoUrl")) {
            String mainRepoUrl = parameters.get("mainRepoUrl");
            String repoProvider = parameters.getOrDefault("repoProvider", REPOSITORY_PROVIDER);
            String credentialId = parameters.get("credentialId");
            Validate.notBlank(credentialId, "Parameter 'credentialId' is required if 'mainRepoUrl' is specified");

            DominoProjectsApiRepositoriesReferenceDTO defaultRef = new DominoProjectsApiRepositoriesReferenceDTO();
            defaultRef.setType("head");

            DominoProjectsApiProjectGitRepositoryTemp gitRepositoryTemp = new DominoProjectsApiProjectGitRepositoryTemp();
            gitRepositoryTemp.setUri(mainRepoUrl);
            gitRepositoryTemp.setServiceProvider(repoProvider);
            gitRepositoryTemp.setCredentialId(credentialId);
            gitRepositoryTemp.setDefaultRef(defaultRef);

            newProject.setMainRepository(gitRepositoryTemp);
        }

        ProjectsApi projectsApi = new ProjectsApi(getApiClient(parent.domino));
        DominoNucleusProjectModelsProject project = projectsApi.createProject(newProject);

        output(project, parent.domino);
    }
}
