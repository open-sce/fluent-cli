@echo off
call set-env.cmd

REM  Retrieves projects dependency graph for a user, and optionally for a specific project
%CLIENT% user project-dependency-graph

REM Owner username of the project, if dependency graph is being requested for a specific project. (optional)
REM  Project name for which a dependency graph is being requested. When not provided, the dependency graph is for all projects for the current user. (optional)
REM %CLIENT% user project-dependency-graph  userName="el100646" projectName="quick-start"
