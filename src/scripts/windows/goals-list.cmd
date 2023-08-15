@echo off
call set-env.cmd

REM Gets the current goals for a project
%CLIENT% goal list projectId=123456
