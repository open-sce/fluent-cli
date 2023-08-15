@echo off
call set-env.cmd

REM Deletes all of the current user's environment variables
%CLIENT% user envdelete

