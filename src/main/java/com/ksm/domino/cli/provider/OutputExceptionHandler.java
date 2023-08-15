package com.ksm.domino.cli.provider;

import org.apache.commons.lang3.exception.ExceptionUtils;

import picocli.CommandLine;

/**
 * Handle CLI exceptions by only printing the core error and not the stack trace.
 */
public class OutputExceptionHandler
            implements CommandLine.IExecutionExceptionHandler {

    @Override
    public int handleExecutionException(Exception e, CommandLine commandLine, CommandLine.ParseResult parseResult) {
        String error = ExceptionUtils.getRootCauseMessage(e);
        boolean isParamException = e instanceof IllegalArgumentException;
        if (isParamException) {
            error = e.getMessage();
        }
        commandLine.getErr().println(commandLine.getColorScheme().errorText(error));

        // print help on missing params
        if (isParamException) {
            commandLine.usage(commandLine.getErr());
        }
        return commandLine.getCommandSpec().exitCodeOnExecutionException();
    }
}
