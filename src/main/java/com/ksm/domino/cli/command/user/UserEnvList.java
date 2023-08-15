package com.ksm.domino.cli.command.user;

import com.dominodatalab.api.model.DominoCommonModelsEnvironmentVariables;
import com.dominodatalab.api.rest.UsersApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine.Command;
import picocli.CommandLine.ParentCommand;

@Command(name = "envlist", header = "%n@|green Retrieves the current user's environment variables|@")
public class UserEnvList extends AbstractDominoCommand {

    @ParentCommand
    private User parent;    

    @Override
    public void execute() throws Exception {
        UsersApi api = new UsersApi(getApiClient(parent.domino));
        DominoCommonModelsEnvironmentVariables vars = api.listUserEnvironmentVariables();
        output(vars, parent.domino);
    }
}
