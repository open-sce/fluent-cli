package com.ksm.domino.cli.command.server;

import com.ksm.domino.cli.Domino;
import com.ksm.domino.cli.command.AbstractParentCommand;

import picocli.CommandLine;
import picocli.CommandLine.ParentCommand;

@CommandLine.Command(name = "server",
            commandListHeading = "%nCommands:%n%nThe most commonly used @|bold 'server'|@ commands are:%n",
            header = "%n@|green Server related functions|@",
            subcommands = {
                        ServerVersion.class
            })
public class Server extends AbstractParentCommand {
    @ParentCommand
    Domino domino; // picocli injects reference to parent command       
}
