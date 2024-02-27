package com.ksm.domino.cli.command.dataset;

import com.ksm.domino.cli.Domino;
import com.ksm.domino.cli.command.AbstractParentCommand;

import picocli.CommandLine.Command;
import picocli.CommandLine.ParentCommand;

@Command(name = "dataset",
        commandListHeading = "%nCommands:%n%nThe most commonly used @|bold 'dataset'|@ commands are:%n",
        header = "%n@|green Dataset related functions|@",
        subcommands = {
                DatasetCreate.class,
                DatasetMount.class,
                DatasetRename.class,
                DatasetSnapshot.class,
                DatasetUnmount.class
        })
public class Dataset extends AbstractParentCommand {
        @ParentCommand
        Domino domino; // picocli injects reference to parent command        
}
