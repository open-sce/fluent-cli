package com.ksm.domino.cli.command.collaborator;

import com.ksm.domino.cli.Domino;
import com.ksm.domino.cli.command.AbstractParentCommand;

import picocli.CommandLine.Command;
import picocli.CommandLine.ParentCommand;

@Command(name = "collaborator", commandListHeading = "%nCommands:%n%nThe most commonly used @|bold 'collaborator'|@ commands are:%n", header = "%n@|green Collaborator related functions|@", subcommands = {
                CollaboratorAdd.class,
                CollaboratorRemove.class
})
public class Collaborator extends AbstractParentCommand {

        @ParentCommand
        Domino domino; // picocli injects reference to parent command
}
