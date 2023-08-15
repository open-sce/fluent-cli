package com.ksm.domino.cli.provider;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import picocli.CommandLine;

/**
 * Checks the environment variables for a default value first.
 */
public class EnvironmentVariableDefaultProvider implements CommandLine.IDefaultValueProvider {

    @Override
    public String defaultValue(CommandLine.Model.ArgSpec argSpec) {
        if (StringUtils.isBlank(argSpec.defaultValue())) {
            return null;
        }
        String defaultValue = StringUtils.trim(argSpec.defaultValue());
        String systemProperty = SystemUtils.getEnvironmentVariable(defaultValue, defaultValue);
        if (StringUtils.isNotBlank(systemProperty)) {
            return systemProperty;
        }
        return defaultValue;
    }
}
