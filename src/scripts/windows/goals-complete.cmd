@echo off
call set-env.cmd

REM Mark a project goal as completed.
%CLIENT% goal complete projectId=123456 goalId=654321
