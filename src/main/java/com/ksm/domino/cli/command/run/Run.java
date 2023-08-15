package com.ksm.domino.cli.command.run;

import com.ksm.domino.cli.Domino;
import com.ksm.domino.cli.command.AbstractParentCommand;

import picocli.CommandLine;
import picocli.CommandLine.ParentCommand;

@CommandLine.Command(name = "run",
            commandListHeading = "%nCommands:%n%nThe most commonly used @|bold 'run'|@ commands are:%n",
            header = "%n@|green Run related functions|@",
            subcommands = {
                        RunRecent.class,
                        RunList.class
            })
public class Run extends AbstractParentCommand {
    @ParentCommand
    Domino domino; // picocli injects reference to parent command        
}
