package com.ksm.domino.cli.command.dataset;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.dominodatalab.pub.model.DatasetRwEnvelopeV1;
import com.dominodatalab.pub.model.DatasetRwMetadataV1;
import com.dominodatalab.pub.rest.DatasetRwApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

@Command(name = "rename", header = "%n@|green Rename a dataset|@")
public class DatasetRename extends AbstractDominoCommand {

    @ParentCommand
    private Dataset parent;

    private static final String NAME = "dataset remame";
    
    private static final String PARAM_DATASET_ID = "datasetId";
    private static final String PARAM_DATASET_NAME = "name";
    private static final String PARAM_DATASET_DESCRIPTION = "description";

    @Parameters(description = "@|blue Required parameters:%n datasetId=123%n name=supplemental%n%nOptional Parameters:%ndescription=\"Extraneous Metadata\"%n|@%n", mapFallbackValue = "")
    private final Map<String, String> parameters = new LinkedHashMap<>(2);

    @Override
    public void execute() throws Exception {
        String datasetId = getRequiredParam(parameters, PARAM_DATASET_ID, NAME);
        String datasetName = getRequiredParam(parameters, PARAM_DATASET_NAME, NAME);

        String datasetDescription = parameters.getOrDefault(PARAM_DATASET_DESCRIPTION, StringUtils.EMPTY);

        DatasetRwMetadataV1 request = new DatasetRwMetadataV1();
        request.name(datasetName);
        if (!StringUtils.isBlank(datasetDescription)) {
            request.description(datasetDescription);
        }

        DatasetRwApi api = new DatasetRwApi(getPubClient(parent.domino));
        DatasetRwEnvelopeV1 result = api.updateDataset(datasetId, request);
        output(result, parent.domino);
    }
}
