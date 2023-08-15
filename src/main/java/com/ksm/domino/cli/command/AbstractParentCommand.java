package com.ksm.domino.cli.command;

import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Spec;

/**
 * Abstract base command that all parent commands should extend which will print the "help" output
 * if this command is called with no options.
 */
public abstract class AbstractParentCommand implements Runnable {

    @Spec
    CommandLine.Model.CommandSpec spec;

    @Option(names = {"-h", "--help"}, usageHelp = true, description = "Print usage help and exit.")
    private boolean usageHelpRequested;

    /**
     * Print the help if this command is called directly.
     */
    @Override
    public void run() {
        // if the command was invoked without subcommand, show the usage help
        spec.commandLine().usage(System.err);
    }
}
