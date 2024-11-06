package com.ksm.domino.cli.command.goals;

import com.ksm.domino.cli.Domino;
import com.ksm.domino.cli.command.AbstractParentCommand;

import picocli.CommandLine;
import picocli.CommandLine.ParentCommand;

@CommandLine.Command(name = "goal",
            commandListHeading = "%nCommands:%n%nThe most commonly used 'goal' commands are:%n",
            header = "%n@|green Goal related functions|@",
            subcommands = {
                        GoalComplete.class,
                        GoalLinkJob.class
            })
public class Goal extends AbstractParentCommand {
    @ParentCommand
    Domino domino; // picocli injects reference to parent command        
}
