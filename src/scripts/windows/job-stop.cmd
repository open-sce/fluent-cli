@echo off
call set-env.cmd

REM Stops an existing job
%CLIENT% job stop projectId=12345 jobId=456 commitResults=true

