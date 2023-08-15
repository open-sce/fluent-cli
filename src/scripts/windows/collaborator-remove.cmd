@echo off
call set-env.cmd

REM Removes a user or organization from Project
%CLIENT% collaborator remove projectId="123" collaboratorId="hsimpson"
