package com.ksm.domino.cli.command.dataset;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dominodatalab.api.model.DominoDatasetrwApiDatasetRwSnapshotSummaryDto;
import com.dominodatalab.api.rest.DatasetRwApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

@Command(name = "snapshot", header = "%n@|green Retrieves a snapshot of a dataset|@")
public class DatasetSnapshot extends AbstractDominoCommand {
    
    @ParentCommand
    private Dataset parent;

    private static final String NAME = "dataset snapshot";

    @Parameters(description = "@|blue Required parameters:%n snapshotId=456%n|@%n", mapFallbackValue = "")
    private final Map<String, String> parameters = new LinkedHashMap<>(1);

    @Override
    public void execute() throws Exception {
        String snapshotId = getRequiredParam(parameters, "snapshotId", NAME);
        DatasetRwApi api = new DatasetRwApi(getApiClient(parent.domino));
        DominoDatasetrwApiDatasetRwSnapshotSummaryDto result = api.getSnapshot(snapshotId);
        output(result, parent.domino);
    }
}
