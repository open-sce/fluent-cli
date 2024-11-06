package com.ksm.domino.cli.command.user;

import com.ksm.domino.cli.Domino;
import com.ksm.domino.cli.command.AbstractParentCommand;

import picocli.CommandLine.Command;
import picocli.CommandLine.ParentCommand;

@Command(name = "user",
        commandListHeading = "%nCommands:%n%nThe most commonly used @|bold 'user'|@ commands are:%n",
        header = "%n@|green User related functions|@",
        subcommands = {
                UserCurrent.class,
                UserList.class,
                UserEnvList.class,
                UserEnvDelete.class,
                UserCredentialAdd.class,
                UserCredentialDelete.class,
                UserCredentialList.class,
        })
public class User extends AbstractParentCommand {
        @ParentCommand
        Domino domino; // picocli injects reference to parent command   
}
