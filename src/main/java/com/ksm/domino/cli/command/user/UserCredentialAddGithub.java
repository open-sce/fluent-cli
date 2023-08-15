package com.ksm.domino.cli.command.user;

import java.util.LinkedHashMap;
import java.util.Map;

import picocli.CommandLine.Command;

@Command(name = "github", header = "%n@|green Add GitHub credentials for the current user|@")
public class UserCredentialAddGithub extends AbstractGithubCommand {

    @Override
    protected Map<String, String> createProviderRequest() {
        Map<String, String> request = new LinkedHashMap<>();
        request.put(UserCredentialAdd.PROVIDER_REQUEST_KEY, UserCredentialAdd.GITHUB_PROVIDER);
        return request;
    }
    
}
