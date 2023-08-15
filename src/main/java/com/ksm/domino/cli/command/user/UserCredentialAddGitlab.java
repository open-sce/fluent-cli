package com.ksm.domino.cli.command.user;

import java.util.LinkedHashMap;
import java.util.Map;

import picocli.CommandLine.Command;

@Command(name = "gitlab", header = "%n@|green Add GitLab credentials for the current user|@")
public class UserCredentialAddGitlab extends AbstractGithubCommand {

    @Override
    protected Map<String, String> createProviderRequest() {
        Map<String, String> request = new LinkedHashMap<>();
        request.put(UserCredentialAdd.PROVIDER_REQUEST_KEY, UserCredentialAdd.GITLAB_PROVIDER);
        return request;
    }
    
}
