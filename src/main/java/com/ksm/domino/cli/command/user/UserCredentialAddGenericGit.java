package com.ksm.domino.cli.command.user;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dominodatalab.api.invoker.ApiClient;
import com.dominodatalab.api.model.DominoCommonUserPerson;
import com.dominodatalab.api.model.DominoServerAccountApiGitCredentialAccessorDto;
import com.dominodatalab.api.rest.GitCredentialsApi;
import com.dominodatalab.api.rest.UsersApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;

@Command(name = "generic", header = "%n@|green Add credentials for a generic git server at a specified domain|@")
public class UserCredentialAddGenericGit extends AbstractDominoCommand {
    
    @ParentCommand
    private UserCredentialAdd userCredentialAdd; 

    static class AcceptedGenericCredentials {
        @ArgGroup(exclusive = false, multiplicity = "1") 
        UserCredentialAdd.SSHCredential sshCredential;

        @ArgGroup(exclusive = false, multiplicity = "1")
        private UserCredentialAdd.TokenCredential tokenCredential;

        @ArgGroup(exclusive = false, multiplicity = "1")
        private UserCredentialAdd.UserPassCredential userPassCredential;        
    }      

    @Option(names = { "-d", "--domain" }, description = "The domain of the target git server", required = true)
    private String domain;

    @ArgGroup(exclusive = true, multiplicity = "1") 
    AcceptedGenericCredentials credentials;    

    @Override
    public void execute() throws Exception {
        Map<String, String> request =  new LinkedHashMap<>();
        request.put(UserCredentialAdd.PROVIDER_REQUEST_KEY, UserCredentialAdd.GENERIC_PROVIDER);
        request.put(UserCredentialAdd.DOMAIN_REQUEST_KEY, domain);
        request.put(UserCredentialAdd.CREDENTIAL_NAME_REQUEST_KEY, userCredentialAdd.nameOption);
        if (null != credentials.sshCredential){
            request.putAll(UserCredentialAdd.mapSSHCredentials(credentials.sshCredential));
        } else if (null != credentials.tokenCredential) {
            request.putAll(UserCredentialAdd.mapTokenCredentials(credentials.tokenCredential));
        } else if (null != credentials.userPassCredential){
            request.putAll(UserCredentialAdd.mapUserPassCredentials(credentials.userPassCredential));
        }

        ApiClient apiClient = getApiClient(userCredentialAdd.user.domino);
        UsersApi api = new UsersApi(apiClient);

        DominoCommonUserPerson user = api.getCurrentUser();

        GitCredentialsApi gitApi = new GitCredentialsApi(apiClient);

        DominoServerAccountApiGitCredentialAccessorDto result = gitApi.addGitCredential(user.getId(), request);

        output(result, userCredentialAdd.user.domino);
    }
    
}
