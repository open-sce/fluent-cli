# Domino Data Lab Fluent CLI

[![License: GPL 3.0](https://img.shields.io/badge/License-GPL3-red.svg?style=for-the-badge)](https://opensource.org/license/gpl-3-0/)
[![Quarkus](https://img.shields.io/badge/quarkus-power-blue?logo=quarkus&style=for-the-badge)](https://github.com/quarkusio/quarkus)
[![CI](https://img.shields.io/github/actions/workflow/status/open-sce/fluent-cli/build.yml?branch=main&logo=GitHub&style=for-the-badge)](https://github.com/open-sce/fluent-cli/actions/workflows/build.yml)

Domino Data Lab Fluent CLI (Command Line Interface) is a client used provision and control Domino.

# Features

The Fluent CLI offers numerous features and advantages compared to the Domino-provided CLI, including, but not limited to:

- Ability to output results as plain text, JSON, or XML
- Zero dependency native executable (macOS, Windows, Linux)
- Detailed context-sensitive help for each command
- Easily [script and chain](https://raw.githubusercontent.com/open-sce/fluent-cli/main/demo/demo.sh) commands together in your favorite shell (Bash, PowerShell, etc)

# Requirements

To use this CLI you will need to download the binary for your operating system such as Windows, Linux, or macOS. We provide pre-built binaries that have no other requirements found
on our [Releases](https://github.com/open-sce/fluent-cli/releases) page.

- Binary for your OS found here: https://github.com/open-sce/fluent-cli/releases
- Domino API Key to an active Domino instance

# Domino Settings

To connect to Domino you will need at least the URL of where your Domino installation is and a personal API key to
access the API. You can set your variables either as environment variables `DOMINO_API_KEY` and `DOMINO_API_URL` or use
the command line parameters `-k` and `-u`.

### Linux

To add environment variables in Linux so that they are always available upon login, you can modify the shell configuration file for your user. The specific file you need to edit may vary depending on the shell you are using (e.g., Bash, Zsh). Here are the general steps to add environment variables:

1. Identify the shell you are using: Open a terminal and type `echo $SHELL`. This will display the path to the current shell binary.

2. Open the shell configuration file: Use a text editor to open the configuration file for your shell. Here are some common configuration files for popular shells:

   - Bash: `~/.bashrc` or `~/.bash_profile`
   - Zsh: `~/.zshrc` or `~/.zprofile`
   - Fish: `~/.config/fish/config.fish`

3. Add environment variable assignments: Open the shell configuration file in a text editor and add the following lines at the end:

```shell
export DOMINO_API_KEY="your_api_key"
export DOMINO_API_URL="your_api_url"
```

Replace `"your_api_key"` and `"your_api_url"` with the actual values you want to assign to the environment variables.

4. Save and close the file.

5. Apply the changes: To make the environment variables available in your current terminal session, run the following command in the terminal:

```shell
source ~/.bashrc
```

Replace `~/.bashrc` with the actual path to your shell configuration file if you are using a different file.

Now, the environment variables `DOMINO_API_KEY` and `DOMINO_API_URL` will be set every time you log in to your Linux system or open a new terminal session.

### Windows

To add your environment variables in Windows Powershell you can add them like this:

```powershell
[Environment]::SetEnvironmentVariable("DOMINO_API_KEY", "your_api_key", "User")
[Environment]::SetEnvironmentVariable("DOMINO_API_URL", "your_api_url", "User")
```

Replace `"your_api_key"` and `"your_api_url"` with the actual values you want to assign to the environment variables.

# Domino Workspace

If you're operating within a Domino Workspace, accessing and utilizing the CLI requires minimal setup. Being within the workspace ensures that the CLI seamlessly integrates with your existing API_KEY and API_URL, eliminating the need for additional configuration. As a result, the CLI functions effortlessly right from the start.

## Download

Within your Domino Workspace, initiate a Terminal session and execute commands akin to the ones below to download and extract the CLI:

```shell
# Download the file
$ wget https://github.com/open-sce/fluent-cli/releases/download/3.0.0/domino-cli-3.0.0-linux-x86_64.tar.gz

# Unzip the downloaded file
$ tar -xvf domino-cli-3.0.0-linux-x86_64.tar.gz

# Move the unzipped cli to current directory
$ mv domino-cli-3.0.0-linux-x86_64/bin/domino-cli .
```

# Test

To test the CLI is working, run the following command to print out your current user information:

```shell
$ ./domino-cli user current
```

If you are not using environment variables it would be:

```shell
$ ./domino-cli -k YOUR_KEY -u https://domino.yourcompany.com/v4 user current
```

**Output:**

```json
{
  "firstName": "Homer",
  "lastName": "Simpson",
  "fullName": "Homer Simpson",
  "id": "6124ffbfa7db86282dde302a",
  "userName": "hs12345",
  "email": "homer.simpson@springfield.org"
}
```

# Help

![image](https://user-images.githubusercontent.com/4399574/155019857-986e31e4-abc0-4eda-9e96-3ed39c746119.png)

# Build

Building the project necessitates Apache Maven. You have the option to assemble it as a JAR file, requiring a JVM for execution, or as a standalone executable, needing no additional dependencies to run.

## JVM Build

```shell
$ mvn clean package
```

To test the CLI:

```shell
$ java -jar ./target/domino-cli.jar --version

    ___                _
   /   \___  _ __ ___ (_)_ __   ___
  / /\ / _ \| '_ ` _ \| | '_ \ / _ \
 / /_// (_) | | | | | | | | | | (_) |
/___,' \___/|_| |_| |_|_|_| |_|\___/

Domino CLI 2.0.0-SNAPSHOT
Copyright 2023, OpenSCE Collaborative
Java OpenJDK Runtime Environment 11.0.18+10 Oracle Corporation
OS Windows 10 10.0 amd64
```

## Native Executable Build

Quarkus has excellent [instructions for building native executables](https://quarkus.io/guides/building-native-image).

### Linux

For Linux follow the [instructions for building native executables](https://quarkus.io/guides/building-native-image) which may require you to installed GraalVM.  
Once you have everything installed you can build a Linux native executable with:

```shell
mvn clean package -Pnative
```

### Windows

To build a native executable on Windows will require you install the [Visual Studio 2017 Visual C++ Build Tools](https://aka.ms/vs/15/release/vs_buildtools.exe) or if you already have full Visual Studio installed.
Once you have everything installed you can build a Windows native executable with:

```shell
mvn clean package -Pnative
```

You will find the fully portable executable in `/target/domino-cli-2.0.0.exe` which you can rename and move to any other Windows machine.

# Releasing

- Click on GitHub Actions
- Find the Release action and execute the action manually
- It will ask for Release version and next SNAPSHOT version. Simply fill out and it is all automated!

# License

Licensed under the [GNU General Public License version 3](https://opensource.org/license/gpl-3-0/).

`SPDX-License-Identifier: GPL-3.0-or-later`

# Copyright

Domino and Domino Data Lab are © 2023 Domino Data Lab, Inc. Made in San Francisco.
