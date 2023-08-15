package com.ksm.domino.cli.command.dataset;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.dominodatalab.api.model.DominoDatasetrwApiDatasetRwGrant;
import com.dominodatalab.api.model.DominoDatasetrwApiDatasetRwGrant.TargetRoleEnum;
import com.dominodatalab.api.model.DominoDatasetrwApiDatasetRwViewDto;
import com.dominodatalab.api.model.DominoDatasetrwWebCreateDatasetRequest;
import com.dominodatalab.api.rest.DatasetRwApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

@Command(name = "create", header = "%n@|green Create dataset for project|@")
public class DatasetCreate extends AbstractDominoCommand {

    @ParentCommand
    private Dataset parent;

    private static final String NAME = "dataset create";

    @Parameters(description = "@|blue Required parameters:%n projectId=123%n name=supplemental%n%nOptional Parameters:%ndescription=\"Extraneous Metadata\"%n|@%n", mapFallbackValue = "")
    private final Map<String, String> parameters = new LinkedHashMap<>(2);

    @Override
    public void execute() throws Exception {
        String projectId = getRequiredParam(parameters,
                    DominoDatasetrwApiDatasetRwViewDto.JSON_PROPERTY_PROJECT_ID, NAME);
        String datasetName = getRequiredParam(parameters, "name", NAME);

        String datasetDescription = parameters.getOrDefault("description", StringUtils.EMPTY);

        DominoDatasetrwWebCreateDatasetRequest request = new DominoDatasetrwWebCreateDatasetRequest();
        request.projectId(projectId);
        request.datasetName(datasetName);
        request.description(datasetDescription);
        ArrayList<DominoDatasetrwApiDatasetRwGrant> grants = new ArrayList<>();

        String collaboratorId = parameters.get("collaboratorIds");
        List<String> collaboratorIds;
        if (StringUtils.isNotBlank(collaboratorId)) {
            collaboratorIds = List.of(StringUtils.split(parameters.get("collaboratorIds"), ','));
            collaboratorIds.forEach(id -> {
                DominoDatasetrwApiDatasetRwGrant grant = new DominoDatasetrwApiDatasetRwGrant();
                grant.setTargetId(id);
                grant.setTargetRole(TargetRoleEnum.DATASETRWEDITOR);
                grants.add(grant);
            });
        }
        request.setGrants(grants);
        request.usedForModelMonitoring(Boolean.FALSE);

        DatasetRwApi api = new DatasetRwApi(getApiClient(parent.domino));
        DominoDatasetrwApiDatasetRwViewDto result = api.createDataset(request);
        output(result, parent.domino);
    }
}
