package com.ksm.domino.cli.command.user;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dominodatalab.api.invoker.ApiClient;
import com.dominodatalab.api.model.DominoCommonUserPerson;
import com.dominodatalab.api.rest.GitCredentialsApi;
import com.dominodatalab.api.rest.UsersApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine;
import picocli.CommandLine.ParentCommand;

@CommandLine.Command(name = "delete-credentials", header = "%n@|green Removes git credentials from the current user's account|@")
public class UserCredentialDelete extends AbstractDominoCommand {

    @ParentCommand
    private User parent;

    private static final String NAME = "user delete-credentials";

    @CommandLine.Parameters(description = "@|blue Required Parameters:%ncredentialId=5614...|@")
    private final Map<String, String> parameters = new LinkedHashMap<>();

    @Override
    public void execute() throws Exception {
        String credentialsId = getRequiredParam(parameters, "credentialId", NAME);

        ApiClient apiClient = getApiClient(parent.domino);

        UsersApi api = new UsersApi(apiClient);
        DominoCommonUserPerson user = api.getCurrentUser();

        GitCredentialsApi gitApi = new GitCredentialsApi(apiClient);
        String userId = user.getId();

        gitApi.deleteGitCredential(userId, credentialsId);

        output(String.format("Credential %s successfully removed for user %s", credentialsId, userId), parent.domino);
    }
}
