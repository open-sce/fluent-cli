@echo off
call set-env.cmd

REM Link a job to a project goal.
%CLIENT% goal linkjob projectId=123456 goalId=654321 jobId=9876
