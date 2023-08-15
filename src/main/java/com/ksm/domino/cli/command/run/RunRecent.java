package com.ksm.domino.cli.command.run;

import java.util.List;

import com.dominodatalab.api.model.DominoCommonRunInterfacesRunMonolithDTO;
import com.dominodatalab.api.rest.RunsApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine;
import picocli.CommandLine.ParentCommand;

@CommandLine.Command(name = "recent", header = "%n@|green Retrieves the recently executed runs|@")
public class RunRecent extends AbstractDominoCommand {
    
    @ParentCommand
    private Run parent;    

    @Override
    public void execute() throws Exception {
        RunsApi api = new RunsApi(getApiClient(parent.domino));
        List<DominoCommonRunInterfacesRunMonolithDTO> runs = api.listRecentRuns();
        output(runs, parent.domino);
    }
}
