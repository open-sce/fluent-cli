package com.ksm.domino.cli;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

public class DominoTest extends AbstractCliTest {

    @Test
    void version() {
        // Arrange
        String[] args = {"--version"};
        Domino mixed = new Domino();
        CommandLine cl = new CommandLine(mixed);

        // Act
        int exitCode = cl.execute(args);

        // Assert
        Assertions.assertEquals(0, exitCode);
        String output = outputStreamCaptor.toString().trim();
        boolean result = StringUtils.containsIgnoreCase(output, "Domino CLI UNKNOWN");
        Assertions.assertTrue(result, output);
    }
}