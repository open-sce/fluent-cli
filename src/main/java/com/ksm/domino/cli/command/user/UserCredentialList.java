package com.ksm.domino.cli.command.user;

import java.util.List;

import com.dominodatalab.api.model.DominoCommonUserPerson;
import com.dominodatalab.api.model.DominoServerAccountApiGitCredentialAccessorDto;
import com.dominodatalab.api.rest.GitCredentialsApi;
import com.dominodatalab.api.rest.UsersApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine;
import picocli.CommandLine.ParentCommand;

@CommandLine.Command(name = "credentials", header = "%n@|green Retrieves the current user's git credentials|@")
public class UserCredentialList extends AbstractDominoCommand {

    @ParentCommand
    private User parent;
        
    @Override
    public void execute() throws Exception {
        UsersApi api = new UsersApi(getApiClient(parent.domino));
        DominoCommonUserPerson user = api.getCurrentUser();

        GitCredentialsApi gitApi = new GitCredentialsApi(getApiClient(parent.domino));
        List<DominoServerAccountApiGitCredentialAccessorDto> credentials = gitApi.getGitCredentials(user.getId());
        output(credentials, parent.domino);
    }
}
