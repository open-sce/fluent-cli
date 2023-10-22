package com.ksm.domino.cli.command.job;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.dominodatalab.api.invoker.ApiException;
import com.dominodatalab.api.model.*;
import com.dominodatalab.api.rest.DataMountApi;
import org.apache.commons.lang3.Validate;

import com.dominodatalab.api.rest.JobsApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine;
import picocli.CommandLine.ParentCommand;

@CommandLine.Command(name = "start", header = "%n@|green Start a new job.|@")
public class JobStart extends AbstractDominoCommand {

    @ParentCommand
    private Job parent;

    private static final String NAME = "job start";
    private static final String MAIN_REPO_REF_TYPE = "mainRepoGitRefType";
    private static final String MAIN_REPO_REF_VALUE = "mainRepoGitRefValue";

    @CommandLine.Parameters(description = "@|blue Parameters:%n projectId=12345%n environmentId=456%n mainRepoGitRefType=head%n mainRepoGitRefValue=xxx%n commandToRun='test.sh'%n commitId=abe5g43%n overrideHardwareTierId=xxx%n|@%n", mapFallbackValue = "")
    private final Map<String, String> parameters = new LinkedHashMap<>(6);

    @CommandLine.Option(names = {"--no-mount"}, description = "Do not mount external data volumes (default=false)")
    private boolean noMount;

    @Override
    public void execute() throws Exception {
        JobsApi api = new JobsApi(getApiClient(parent.domino));
        DominoJobsWebStartJobRequest request = createRequest();
        DominoJobsInterfaceJob job = api.startJob(request);
        output(job, parent.domino);
    }

    private DominoJobsWebStartJobRequest createRequest() throws ApiException {
        DominoJobsWebStartJobRequest request = new DominoJobsWebStartJobRequest();
        // required
        String projectId = getRequiredParam(parameters, DominoJobsWebStartJobRequest.JSON_PROPERTY_PROJECT_ID, NAME);
        request.setProjectId(projectId);
        request.setCommandToRun(
                    getRequiredParam(parameters, DominoJobsWebStartJobRequest.JSON_PROPERTY_COMMAND_TO_RUN, NAME));

        // optional
        request.setCommitId(parameters.get(DominoJobsWebStartJobRequest.JSON_PROPERTY_COMMIT_ID));
        request.setEnvironmentId(parameters.get(DominoJobsWebStartJobRequest.JSON_PROPERTY_ENVIRONMENT_ID));
        request.setOverrideHardwareTierId(
                    parameters.get(DominoJobsWebStartJobRequest.JSON_PROPERTY_OVERRIDE_HARDWARE_TIER_ID));
        request.setEnvironmentRevisionSpec(new DominoScheduledjobApiComputeClusterConfigSpecDtoComputeEnvironmentRevisionSpec("ActiveRevision"));

        if (parameters.containsKey(MAIN_REPO_REF_TYPE) || parameters.containsKey(MAIN_REPO_REF_VALUE)) {
            DominoProjectsApiRepositoriesReferenceDTO mainRepoGitRef = new DominoProjectsApiRepositoriesReferenceDTO();
            mainRepoGitRef.setType(parameters.get(MAIN_REPO_REF_TYPE));
            mainRepoGitRef.setValue(parameters.get(MAIN_REPO_REF_VALUE));

            Validate.notBlank(mainRepoGitRef.getType(),
                    String.format("Missing the required parameter '%s' when calling '%s' and '%s' is defined.", MAIN_REPO_REF_TYPE, NAME, MAIN_REPO_REF_VALUE));

            Validate.notBlank(mainRepoGitRef.getValue(),
                    String.format("Missing the required parameter '%s' when calling '%s' and '%s' is defined.", MAIN_REPO_REF_VALUE, NAME, MAIN_REPO_REF_TYPE));

            request.setMainRepoGitRef(mainRepoGitRef);
        }

        if (!noMount) {
            request.setExternalVolumeMounts(getExternalVolumeMounts(projectId));
        }

        // unused
        request.setComputeClusterProperties(null);
        request.setOnDemandSparkClusterProperties(null);

        return request;
    }

    private List<String> getExternalVolumeMounts(final String projectId) throws ApiException {
        DataMountApi api = new DataMountApi(getApiClient(parent.domino));
        return api.findDataMountsByProject(projectId)
                .stream()
                .map(DominoDatamountApiDataMountDto::getId)
                .collect(Collectors.toList());
    }
    
}
