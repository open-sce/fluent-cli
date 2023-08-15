package com.ksm.domino.cli.command.user;

import java.util.LinkedHashMap;
import java.util.Map;

import picocli.CommandLine.Command;

@Command(name = "bitbucket", header = "%n@|green Add bitbucket.org credentials for the current user|@")
public class UserCredentialAddBitbucket extends AbstractBitbucketCommand {

    @Override
    protected Map<String, String> createProviderRequest() {
        Map<String, String> request = new LinkedHashMap<>();
        request.put(UserCredentialAdd.PROVIDER_REQUEST_KEY, UserCredentialAdd.BITBUCKET_PROVIDER);
        return request;
    }
    
}
