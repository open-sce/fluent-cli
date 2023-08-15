@echo off
call set-env.cmd

REM Add shared dataset to project
%CLIENT% dataset mount projectId="123" datasetId="456"
