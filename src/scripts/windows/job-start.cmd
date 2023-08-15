@echo off
call set-env.cmd

REM Starts a new job
%CLIENT% job start projectId=12345 environmentId=456 mainRepoGitRefType=head commandToRun="test.sh"

