@echo off
call set-env.cmd

REM Lists all current users
%CLIENT% user list

REM Optional list of user identifiers to select the previously known users
REM %CLIENT% user list userId="123,456,789"

REM Optional filter for an exact user name
REM %CLIENT% user list userName="el100646"

REM  Optional filter for a user name (returns usernames starting with this query)
REM %CLIENT% user list query="el"
