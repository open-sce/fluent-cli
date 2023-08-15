@echo off
call set-env.cmd

REM Removes git credentials from the current user's account
%CLIENT% user delete-credentials credentialId="abe25ed6562019af"

