package com.ksm.domino.cli.provider;

import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * Launcher to classload the libs inside this JAR. This shields the uber jar from any other dependencies loaded outside
 * if this JAR is used as a library inside another Java application.
 */
public class Launcher {

   public static void main(String[] args) {
      final JarClassLoader jcl = new JarClassLoader();
      try {
         jcl.invokeMain("com.ksm.domino.cli.Domino", args);
      }
      catch (final Throwable e) {
         System.err.println(ExceptionUtils.getRootCauseMessage(e));
      }
   }

}
