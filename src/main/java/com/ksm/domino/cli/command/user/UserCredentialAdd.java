package com.ksm.domino.cli.command.user;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.ksm.domino.cli.command.AbstractParentCommand;

import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;
import picocli.CommandLine.ScopeType;

@CommandLine.Command(
    name = "add-credentials", 
    header = "%n@|green Commands for adding credentials to various Git providers|@",
    subcommands = {
        UserCredentialAddBitbucket.class,
        UserCredentialAddBitbucketServer.class,
        UserCredentialAddGithub.class,
        UserCredentialAddGithubEnterprise.class,
        UserCredentialAddGitlab.class,
        UserCredentialAddGitlabEnterprise.class,
        UserCredentialAddGenericGit.class
    }
)

public class UserCredentialAdd extends AbstractParentCommand {

    @ParentCommand
    User user; 

    static final String BITBUCKET_PROVIDER = "bitbucket";
    static final String BITBUCKET_SERVER_PROVIDER = "bitbucketServer";
    static final String GITHUB_PROVIDER = "github";
    static final String GITHUB_ENTERPRISE_PROVIDER = "githubEnterprise";
    static final String GITLAB_PROVIDER = "gitLab";
    static final String GITLAB_ENTERPRISE_PROVIDER = "gitlabEnterprise";
    static final String GENERIC_PROVIDER = "unknown";

    static final String CREDENTIAL_NAME_REQUEST_KEY = "name";
    static final String PROVIDER_REQUEST_KEY = "gitServiceProvider";
    static final String DOMAIN_REQUEST_KEY = "domain";
    static final String ACCESS_TYPE_REQUEST_KEY = "accessType";
    static final String CREDENTIAL_TYPE_REQUEST_KEY = "type";
    static final String TOKEN_REQUEST_KEY = "token";
    static final String SSH_REQUEST_KEY = "key";
    static final String SSH_PASSPHRASE_REQUEST_KEY = "passphrase";
    static final String USERNAME_REQUEST_KEY = "username";
    static final String PASSWORD_REQUEST_KEY = "password";
    static final String USERPASS_ACCESS_TYPE = "password";

    static final String SSH_DTO_TYPE = "SshGitCredentialDto";
    static final String TOKEN_DTO_TYPE = "TokenGitCredentialDto";
    static final String USERPASS_DTO_TYPE = "PasswordGitCredentialDto";

    @Option(names = { "-n", "--name" }, description = "The name of the new credential.", required = true, scope = ScopeType.INHERIT)
    String nameOption;       

    static class SSHCredential {
        @CommandLine.Option(names= { "--privateKey" }, required = true, description = "Private key file")
        File privateKey;
        @CommandLine.Option(names={ "--passphrase" }, required = false, description = "Private key passphrase")
        String passphrase;
    }

    static class UserPassCredential {
        @CommandLine.Option(names= { "--username" }, required = true, description = "Username")
        String username;
        @CommandLine.Option(names= { "--password" }, required = true, description = "Password or PAT")
        String password;
    }

    static class TokenCredential {
        @CommandLine.Option(names= { "--pat" }, required = true, description = "Personal access token (PAT)")
        String token;
    }

    static Map<String, String> mapSSHCredentials(SSHCredential credential) throws IOException {
        Map<String, String> request = new HashMap<String, String>();
        request.put(CREDENTIAL_TYPE_REQUEST_KEY, SSH_DTO_TYPE);
        request.put(ACCESS_TYPE_REQUEST_KEY, SSH_REQUEST_KEY);
        String privateKey = FileUtils.readFileToString(credential.privateKey, StandardCharsets.UTF_8);
        request.put(SSH_REQUEST_KEY, privateKey);
        if (null != credential.passphrase){
            request.put(SSH_PASSPHRASE_REQUEST_KEY, credential.passphrase);
        }
        return request;
    }

    static Map<String, String> mapUserPassCredentials(UserPassCredential credential) {
        Map<String, String> request = new HashMap<String, String>();
        request.put(CREDENTIAL_TYPE_REQUEST_KEY, USERPASS_DTO_TYPE);
        request.put(ACCESS_TYPE_REQUEST_KEY, USERPASS_ACCESS_TYPE);
        request.put(USERNAME_REQUEST_KEY, credential.username);
        request.put(PASSWORD_REQUEST_KEY, credential.password);
        return request;
    }    

    static Map<String, String> mapTokenCredentials(TokenCredential credential) {
        Map<String, String> request = new HashMap<String, String>();
        request.put(CREDENTIAL_TYPE_REQUEST_KEY, TOKEN_DTO_TYPE);
        request.put(ACCESS_TYPE_REQUEST_KEY, TOKEN_REQUEST_KEY);
        request.put(TOKEN_REQUEST_KEY, credential.token);
        return request;
    }        

}
