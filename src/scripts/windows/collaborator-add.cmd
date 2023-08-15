@echo off
call set-env.cmd

REM Adds a user or organization to Project as a collaborator
%CLIENT% collaborator add projectId="123" collaboratorId="hsimpson" role="Contributor"
