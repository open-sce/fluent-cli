package com.ksm.domino.cli.command.run;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.dominodatalab.api.model.DominoScheduledjobApiLegacyScheduledRunDTO;
import com.dominodatalab.api.rest.ScheduledRunsApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine;
import picocli.CommandLine.ParentCommand;

@CommandLine.Command(name = "list", header = "%n@|green Retrieves a list of runs for a user|@")
public class RunList extends AbstractDominoCommand {

    @ParentCommand
    private Run parent;

    private static final String NAME = "run list";

    @CommandLine.Parameters(description = "@|blue Required parameters:%n userId=hsimpson%n|@%n", mapFallbackValue = "")
    private final Map<String, String> parameters = new LinkedHashMap<>(3);

    @Override
    public void execute() throws Exception {
        String userId = getRequiredParam(parameters, "userId", NAME);
        ScheduledRunsApi api = new ScheduledRunsApi(getApiClient(parent.domino));
        List<DominoScheduledjobApiLegacyScheduledRunDTO> runs = api.listScheduledRuns(userId);
        output(runs, parent.domino);
    }
}
