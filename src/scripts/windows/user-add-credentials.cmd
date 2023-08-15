@echo off
call set-env.cmd

REM Adds SSH private key git credentials to the current user's account
%CLIENT% user add-credentials name="gh-ssh-ed" keyFile="C:\Users\kjhoerr\.ssh\id_ed25519" repoProvider="github"

REM Adds Personal access token git credentials to the current user's account
REM %CLIENT% user add-credentials name="gh-pat" token="ghp_394diojw9aji4f93" providerUrl="ghe.foo.com"
