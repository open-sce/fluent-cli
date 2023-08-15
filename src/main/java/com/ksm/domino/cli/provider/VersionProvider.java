package com.ksm.domino.cli.provider;

import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

import picocli.CommandLine;

public class VersionProvider implements CommandLine.IVersionProvider {

    public String[] getVersion() throws Exception {
        String[] version = new String[12];

        version[0] = "    ___                _             ";
        version[1] = "   /   \\___  _ __ ___ (_)_ __   ___  ";
        version[2] = "  / /\\ / _ \\| '_ ` _ \\| | '_ \\ / _ \\ ";
        version[3] = " / /_// (_) | | | | | | | | | | (_) | ";
        version[4] = "/___,' \\___/|_| |_| |_|_|_| |_|\\___/ ";
        version[5] = String.format("%n@|yellow Domino CLI %s|@", StringUtils.defaultIfEmpty(
                JarClassLoader.class.getPackage().getImplementationVersion(), "UNKNOWN"));
        version[6] = String.format("@|fg(240) Copyright %s, OpenSCE Collaborative|@", Calendar.getInstance().get(Calendar.YEAR));
        version[7] = String.format("@|fg(240) Java %s %s %s|@", SystemUtils.JAVA_RUNTIME_NAME, SystemUtils.JAVA_RUNTIME_VERSION,
                SystemUtils.JAVA_SPECIFICATION_VENDOR);
        version[8] = String.format("@|fg(240) OS %s %s %s|@", SystemUtils.OS_NAME, SystemUtils.OS_VERSION, SystemUtils.OS_ARCH);
        version[9] = "%n";
        return version;
    }

}
