package com.ksm.domino.cli.command.dataset;

import org.apache.commons.lang3.StringUtils;

import com.dominodatalab.pub.model.DatasetRwEnvelopeV1;
import com.dominodatalab.pub.model.DatasetRwMetadataV1;
import com.dominodatalab.pub.rest.DatasetRwApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine.Command;
import picocli.CommandLine;
import picocli.CommandLine.ParentCommand;

@Command(name = "rename", header = "%n@|green Rename a dataset|@", sortOptions = false)
public class DatasetRename extends AbstractDominoCommand {

    @ParentCommand
    private Dataset parent;

    @CommandLine.Option(names = {"--datasetId"}, description = "ID of the dataset to update%n", required = true, order = -3)
    private String datasetId;

    @CommandLine.Option(names = {"--name"}, description = "New name of the dataset%n", required = true, order = -3)
    private String datasetName;

    @CommandLine.Option(names = {"--description"}, description = "New description of the dataset%n", order = -2)
    private String datasetDescription;

    @Override
    public void execute() throws Exception {
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
