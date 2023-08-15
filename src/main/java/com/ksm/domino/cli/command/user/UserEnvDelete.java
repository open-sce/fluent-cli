package com.ksm.domino.cli.command.user;

import com.dominodatalab.api.model.DominoCommonModelsEnvironmentVariables;
import com.dominodatalab.api.rest.UsersApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine.Command;
import picocli.CommandLine.ParentCommand;

@Command(name = "envdelete", header = "%n@|green Deletes all of the current user's environment variables|@")
public class UserEnvDelete extends AbstractDominoCommand {

    @ParentCommand
    private User parent;

    @Override
    public void execute() throws Exception {
        UsersApi api = new UsersApi(getApiClient(parent.domino));
        DominoCommonModelsEnvironmentVariables vars = api.deleteUserEnvironmentVariables();
        output(vars, parent.domino);
    }
}
