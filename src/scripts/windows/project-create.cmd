@echo off
call set-env.cmd

REM Create a project
%CLIENT% project create name=CliMadeThis ownerId=61254658786c341fee11f032

REM Optional set description
REM %CLIENT% project create name=CliMadeThis ownerId=61254658786c341fee11f032 description="Description in quotes"

REM Optional add collaborators
REM %CLIENT% project create name=CliMadeThis ownerId=61254658786c341fee11f032 collaboratorIds=61ef32f985729d7d9ea5694e,6177f554b5b36338bae30f97,61df11d209752a55626e4c98,61df120e53fc7c655799f8db

REM Optional specify main repository location (credentialId required if repository location specified)
REM %CLIENT% project create name=CliMadeThis9001  ownerId=61254658786c341fee11f032 mainRepoUrl=https://mygithub.gsk.com/mlh18390/movie-ratings-pivot credentialId=61de3aee53fc7c655799f823