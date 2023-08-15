@echo off
call set-env.cmd

REM Retrieves the current user's environment variables
%CLIENT% user envlist

