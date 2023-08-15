package com.ksm.domino.cli.command.job;

import com.ksm.domino.cli.Domino;
import com.ksm.domino.cli.command.AbstractParentCommand;

import picocli.CommandLine;
import picocli.CommandLine.ParentCommand;

@CommandLine.Command(name = "job",
            commandListHeading = "%nCommands:%n%nThe most commonly used @|bold 'job'|@ commands are:%n",
            header = "%n@|green Job related functions|@",
            subcommands = {
                        JobGet.class,
                        JobLogs.class,
                        JobStart.class,
                        JobStop.class,
                        JobRestart.class,
                        JobUpdate.class
            })
public class Job extends AbstractParentCommand {
    @ParentCommand
    Domino domino; // picocli injects reference to parent command        
}
