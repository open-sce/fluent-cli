@echo off
call set-env.cmd

REM Retrieves a job by its id
%CLIENT% job get jobId=1234567

