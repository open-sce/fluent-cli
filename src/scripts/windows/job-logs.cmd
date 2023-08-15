@echo off
call set-env.cmd

REM Get the suggestion when problem occurs in a job along with the logs.
%CLIENT% job logs jobId=12345 logType="console" limit=10000 offset=0 latestTimeNano=0

