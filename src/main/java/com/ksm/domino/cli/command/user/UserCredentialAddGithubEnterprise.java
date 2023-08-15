package com.ksm.domino.cli.command.user;

import java.util.LinkedHashMap;
import java.util.Map;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "github-enterprise", header = "%n@|green Add credentials for Github Enterprise at a specified domain|@")
public class UserCredentialAddGithubEnterprise extends AbstractGithubCommand {

    @Option(names = { "-d", "--domain" }, description = "The domain of the target Github Enterprise server", required = true)
    private String domain;
        
    @Override
    protected Map<String, String> createProviderRequest() {
        Map<String, String> request = new LinkedHashMap<>();
        request.put(UserCredentialAdd.PROVIDER_REQUEST_KEY, UserCredentialAdd.GITHUB_ENTERPRISE_PROVIDER);
        request.put(UserCredentialAdd.DOMAIN_REQUEST_KEY, domain);
        return request;
    }
    
}
