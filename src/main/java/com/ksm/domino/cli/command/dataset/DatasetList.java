package com.ksm.domino.cli.command.dataset;

import java.util.List;

import com.dominodatalab.pub.model.PaginatedDatasetRwEnvelopeV2;
import com.dominodatalab.pub.rest.DatasetRwApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine.Command;
import picocli.CommandLine;
import picocli.CommandLine.ParentCommand;

@Command(name = "list", header = "%n@|green List datasets available for user|@", sortOptions = false)
public class DatasetList extends AbstractDominoCommand {

    @ParentCommand
    private Dataset parent;

    @CommandLine.Option(names = {"--include"}, description = "project IDs to include%n", required = false, order = -2)
    private List<String> includeProjectIds;

    @CommandLine.Option(names = {"--exclude"}, description = "project IDs to exclude%n", required = false, order = -2)
    private List<String> excludeProjectIds;

    @CommandLine.Option(names = {"--project-info"}, description = "include project info for each dataset in the response%n", required = false, order = -2)
    private Boolean includeProjectInfo;

    @CommandLine.Option(names = {"--offset"}, description = "number of datasets to offset for request%n", required = false, order = -2)
    private Integer offset;

    @CommandLine.Option(names = {"--limit"}, description = "number of datasets to list for request%n", required = false, order = -2)
    private Integer limit;
   
    @Override
    public void execute() throws Exception {
        DatasetRwApi api = new DatasetRwApi(getPubClient(parent.domino));
        PaginatedDatasetRwEnvelopeV2 result = api.getDatasetsV2(null, excludeProjectIds, includeProjectIds, includeProjectInfo, offset, limit);
        output(result, parent.domino);
    }
}
