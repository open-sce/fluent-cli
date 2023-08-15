package com.ksm.domino.cli.command.datasource;

import com.ksm.domino.cli.Domino;
import com.ksm.domino.cli.command.AbstractParentCommand;

import picocli.CommandLine.Command;
import picocli.CommandLine.ParentCommand;

@Command(name = "datasource",
        commandListHeading = "%nCommands:%n%nThe most commonly used @|bold 'datasource'|@ commands are:%n",
        header = "%n@|green DataSource related functions|@",
        subcommands = {
                DataSourceMount.class
        })
public class DataSource extends AbstractParentCommand {

        @ParentCommand
        Domino domino; // picocli injects reference to parent command
}
