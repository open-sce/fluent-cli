package com.ksm.domino.cli.provider;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import picocli.CommandLine;

/**
 * Checks the environment variables for a default value first. The environment variables can be a comma separated
 * list of values and this will try each in the order they are listed until we find one configured.
 * If none found it will return NULL or the original value passed in.
 */
public class EnvironmentVariableDefaultProvider implements CommandLine.IDefaultValueProvider {

    @Override
    public String defaultValue(CommandLine.Model.ArgSpec argSpec) {
        String defaultValue = argSpec.defaultValue();
        if (StringUtils.isBlank(defaultValue)) {
            return null;
        }

        // split the CSV list of values and return the first one that is set
        for (String value : StringUtils.split(defaultValue, ',')) {
            String trimmedValue = StringUtils.trim(value);
            String systemProperty = SystemUtils.getEnvironmentVariable(trimmedValue, StringUtils.EMPTY);
            if (StringUtils.isNotBlank(systemProperty)) {
                return systemProperty;
            }
        }

        return defaultValue;
    }
}