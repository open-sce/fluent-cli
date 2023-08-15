package com.ksm.domino.cli.command.user;

import java.util.LinkedHashMap;
import java.util.Map;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "gitlab-enterprise", header = "%n@|green Add credentials for Gitlab Enterprise at a specified domain|@")
public class UserCredentialAddGitlabEnterprise extends AbstractGithubCommand {

    @Option(names = { "-d", "--domain" }, description = "The domain of the target Gitlab Enterprise server", required = true)
    private String domain;

    @Override
    protected Map<String, String> createProviderRequest() {
        Map<String, String> request = new LinkedHashMap<>();
        request.put(UserCredentialAdd.PROVIDER_REQUEST_KEY, UserCredentialAdd.GITLAB_ENTERPRISE_PROVIDER);
        request.put(UserCredentialAdd.DOMAIN_REQUEST_KEY, domain);
        return request;
    }
    
}
