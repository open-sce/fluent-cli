@echo off
call set-env.cmd

REM Sets environment variables for a project using dynamic variable assignments
%CLIENT% project set-env projectId=12345 EXTERNAL_API_HOSTNAME=localhost EXTERNAL_API_VERSION=v2
