package com.ksm.domino.cli.command.user;

import java.util.Map;

import com.dominodatalab.api.invoker.ApiClient;
import com.dominodatalab.api.model.DominoCommonUserPerson;
import com.dominodatalab.api.model.DominoServerAccountApiGitCredentialAccessorDto;
import com.dominodatalab.api.rest.GitCredentialsApi;
import com.dominodatalab.api.rest.UsersApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.ParentCommand;

abstract class AbstractGithubCommand extends AbstractDominoCommand {

    protected abstract Map<String, String> createProviderRequest();

    @ParentCommand
    private UserCredentialAdd userCredentialAdd; 

    static class AcceptedGithubCredentials {
        @ArgGroup(exclusive = false, multiplicity = "1") 
        UserCredentialAdd.SSHCredential sshCredential;

        @ArgGroup(exclusive = false, multiplicity = "1")
        private UserCredentialAdd.TokenCredential tokenCredential;
    }    

    @ArgGroup(exclusive = true, multiplicity = "1") 
    AcceptedGithubCredentials credentials;

    @Override
    public void execute() throws Exception {
        Map<String, String> request = createProviderRequest();
        request.put(UserCredentialAdd.CREDENTIAL_NAME_REQUEST_KEY, userCredentialAdd.nameOption);
        if (null != credentials.sshCredential){
            request.putAll(UserCredentialAdd.mapSSHCredentials(credentials.sshCredential));
        } else if (null != credentials.tokenCredential) {
            request.putAll(UserCredentialAdd.mapTokenCredentials(credentials.tokenCredential));
        }

        ApiClient apiClient = getApiClient(userCredentialAdd.user.domino);
        UsersApi api = new UsersApi(apiClient);

        DominoCommonUserPerson user = api.getCurrentUser();

        GitCredentialsApi gitApi = new GitCredentialsApi(apiClient);

        DominoServerAccountApiGitCredentialAccessorDto result = gitApi.addGitCredential(user.getId(), request);

        output(result, userCredentialAdd.user.domino);
    }    
}
