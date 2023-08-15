package com.ksm.domino.cli.command.project;

import com.ksm.domino.cli.Domino;
import com.ksm.domino.cli.command.AbstractParentCommand;

import picocli.CommandLine;
import picocli.CommandLine.ParentCommand;

@CommandLine.Command(name = "project",
            commandListHeading = "%nCommands:%n%nThe most commonly used 'project' commands are:%n",
            header = "%n@|green Project related functions|@",
            subcommands = {
                        ProjectCreate.class,
                        ProjectCopy.class,
                        ProjectMountGit.class,
                        ProjectUnmountGit.class,
                        ProjectUpdateGit.class,
                        ProjectSetEnv.class,
                        ProjectGetEnv.class
            })
public class Project extends AbstractParentCommand {
    @ParentCommand
    Domino domino; // picocli injects reference to parent command       
}
