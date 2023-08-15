@echo off
call set-env.cmd

REM Retrieves list environment variables for a project
%CLIENT% project get-env projectId=12345
