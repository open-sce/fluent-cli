package com.ksm.domino.cli.command.user;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dominodatalab.api.model.DominoNucleusGatewayUsersModelsProjectsDependencyGraph;
import com.dominodatalab.api.rest.UsersApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

@Command(name = "project-dependency-graph", header = "%n@|green Retrieves projects dependency graph for a user, and optionally for a specific project|@")
public class UserProjectDependencyGraph extends AbstractDominoCommand {

    @ParentCommand
    private User parent;    

    @Parameters(description = "@|blue Optional parameters:%n userName=hsimpson%n projectName=test|@%n", mapFallbackValue = "")
    private final Map<String, String> parameters = new LinkedHashMap<>(3);

    @Override
    public void execute() throws Exception {
        String userName = parameters.get("userName");
        String projectName = parameters.get("projectName");

        UsersApi api = new UsersApi(getApiClient(parent.domino));
        DominoNucleusGatewayUsersModelsProjectsDependencyGraph graph = api.projectsDependencyGraph(userName, projectName);
        output(graph, parent.domino);
    }
}
