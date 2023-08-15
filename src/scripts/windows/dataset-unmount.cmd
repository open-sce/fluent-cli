@echo off
call set-env.cmd

REM Remove shared dataset froms project
%CLIENT% dataset unmount projectId="123" datasetId="456"
