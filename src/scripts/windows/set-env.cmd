REM 
REM Domino Command Line Interface 
REM Copyright 2022 KSM Technology Partners LLC
REM https://github.com/ksmpartners/domino-cli
REM
REM Description: 
REM Domino Data Lab Command Line Interface is a client to used provision and control Domino.
REM Example .CMD files are included demonstrating how to call each function.
REM 
REM usage: domino-cli -- help
REM  
REM NOTE: Please customize to match your environment
REM


REM JAVA should contain a pointer to a Java 11+ JRE \bin\java.exe executable
set JAVA=java

REM MEMARGS Java Virutal Machine memory arguments
set MEMARGS=-Xms64m -Xmx256m

REM CLASSPATH location of the cli.jar 
set CLASSPATH=../../../target/domino-cli.jar

REM You can configure the socket timeout (in seconds) here to be longer if you are getting timeouts on certain commands.
set TIMEOUT=-t 90

REM CLIENT is the command line created and used by all other .cmd files in this directory
set CLIENT=%JAVA% %MEMARGS% -jar %CLASSPATH% %TIMEOUT%

