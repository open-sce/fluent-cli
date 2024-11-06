package com.ksm.domino.cli.command.dataset;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.dominodatalab.api.model.DominoDatasetrwApiDatasetRwGrant;
import com.dominodatalab.api.model.DominoDatasetrwApiDatasetRwGrant.TargetRoleEnum;
import com.dominodatalab.api.model.DominoDatasetrwApiDatasetRwViewDto;
import com.dominodatalab.api.model.DominoDatasetrwWebCreateDatasetRequest;
import com.dominodatalab.api.rest.DatasetRwApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine.Command;
import picocli.CommandLine;
import picocli.CommandLine.ParentCommand;

@Command(name = "create", header = "%n@|green Create dataset for project|@")
public class DatasetCreate extends AbstractDominoCommand {

    @ParentCommand
    private Dataset parent;

    @CommandLine.Option(names = {"--project-id"}, description = "ID of the project%n", required = true, order = -2)
    private String projectId;

    @CommandLine.Option(names = {"--name"}, description = "Name of the dataset%n", required = true, order = -2)
    private String datasetName;

    @CommandLine.Option(names = {"--description"}, description = "Description of the dataset%n", required = false, order = -2)
    private String datasetDescription;

    @CommandLine.Option(names = {"--owners"}, description = "Usernames to assign as owners of the new dataset", required = false, order = -2)
    private List<String> owners;
    
    @CommandLine.Option(names = {"--editors"}, description = "Usernames to assign as editors to the new dataset", required = false, order = -2)
    private List<String> editors;
    
    @CommandLine.Option(names = {"--readers"}, description = "Usernames to assign as readers to the new dataset", required = false, order = -2)
    private List<String> readers;

    @Override
    public void execute() throws Exception {

        DominoDatasetrwWebCreateDatasetRequest request = new DominoDatasetrwWebCreateDatasetRequest();
        request.projectId(projectId);
        request.datasetName(datasetName);
        request.description(datasetDescription);
        ArrayList<DominoDatasetrwApiDatasetRwGrant> grants = new ArrayList<>();

        owners.forEach(aggregateGrants(grants, TargetRoleEnum.DATASET_RW_OWNER));
        editors.forEach(aggregateGrants(grants, TargetRoleEnum.DATASET_RW_EDITOR));
        readers.forEach(aggregateGrants(grants, TargetRoleEnum.DATASET_RW_READER));
        request.setGrants(grants);
        request.usedForModelMonitoring(Boolean.FALSE);

        DatasetRwApi api = new DatasetRwApi(getApiClient(parent.domino));
        DominoDatasetrwApiDatasetRwViewDto result = api.createDataset(request);
        output(result, parent.domino);
    }

    /**
     * Creates a Consumer method to add a user to a list of grants as the target role.
     * 
     * To be passed via {@link Iterable#forEach forEach}.
     */
    private Consumer<String> aggregateGrants(List<DominoDatasetrwApiDatasetRwGrant> grants, TargetRoleEnum targetRole) {
        return id -> {
            DominoDatasetrwApiDatasetRwGrant grant = new DominoDatasetrwApiDatasetRwGrant();
            grant.setTargetId(id);
            grant.setTargetRole(targetRole);
            grants.add(grant);
        };
    }
}
