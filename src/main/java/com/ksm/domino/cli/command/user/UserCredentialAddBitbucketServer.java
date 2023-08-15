package com.ksm.domino.cli.command.user;

import java.util.LinkedHashMap;
import java.util.Map;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "bitbucket-server", header = "%n@|green Add credentials for a Bitbucket server at a specified domain|@")
public class UserCredentialAddBitbucketServer extends AbstractBitbucketCommand {

    @Option(names = { "-d", "--domain" }, description = "The domain of the target Bitbucket server", required = true)
    private String domain;

    @Override
    protected Map<String, String> createProviderRequest() {
        Map<String, String> request = new LinkedHashMap<>();
        request.put(UserCredentialAdd.PROVIDER_REQUEST_KEY, UserCredentialAdd.BITBUCKET_SERVER_PROVIDER);
        request.put(UserCredentialAdd.DOMAIN_REQUEST_KEY, domain);
        return request;
    }
    
}
