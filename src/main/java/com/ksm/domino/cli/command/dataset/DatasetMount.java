package com.ksm.domino.cli.command.dataset;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Parameters;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dominodatalab.api.model.DominoDatasetrwApiSharedDatasetRwEntryDto;
import com.dominodatalab.api.rest.DatasetRwApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine.ParentCommand;

@Command(name = "mount", header = "%n@|green Add shared dataset to project|@")
public class DatasetMount extends AbstractDominoCommand {

    @ParentCommand
    private Dataset parent;

    private static final String NAME = "dataset mount";

    @Parameters(description = "@|blue Required parameters:%n projectId=123%n datasetId=456%n|@%n", mapFallbackValue = "")
    private final Map<String, String> parameters = new LinkedHashMap<>(2);

    @Override
    public void execute() throws Exception {
        String projectId = getRequiredParam(parameters,
                    DominoDatasetrwApiSharedDatasetRwEntryDto.JSON_PROPERTY_PROJECT_ID, NAME);
        String datasetId = getRequiredParam(parameters, "datasetId", NAME);
        DatasetRwApi api = new DatasetRwApi(getApiClient(parent.domino));
        DominoDatasetrwApiSharedDatasetRwEntryDto result = api.addSharedDatasetRwEntry(projectId, datasetId);
        output(result, parent.domino);
    }
}
