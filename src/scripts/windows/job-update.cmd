@echo off
call set-env.cmd

REM Update a job setting its new name
%CLIENT% job update jobId=1234567 name="New Name"

