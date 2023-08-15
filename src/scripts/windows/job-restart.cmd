@echo off
call set-env.cmd

REM Restarts an existing job
%CLIENT% job restart projectId=12345 jobId=456 shouldUseOriginalInputCommit=true

