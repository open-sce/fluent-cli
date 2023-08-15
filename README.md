<div align="center">
    <div style="flex-grow: 1; width: 50vw"> 
<a href="https://www.dominodatalab.com/" alt="Domino Data Lab">
   <img class="spinner" loading="lazy" height="80" width="116" src="https://www.dominodatalab.com/hubfs/NBM/domino-logo-spinner.webp" alt="Domino Data Logo - Graphic part">
   <img loading="lazy" height="80" src="https://www.dominodatalab.com/hubfs/NBM/domino-logo-text.webp" alt="Domino Data Logo - Text part">
</a>
    </div>
 
# Domino Data Lab Command Line Interface
</div>
<br>

[![License: GPL 3.0](https://img.shields.io/badge/License-GPL3-red.svg)](https://opensource.org/license/gpl-3-0/)
[![CI](https://github.com/open-sce/fluent-cli/actions/workflows/build.yml/badge.svg)](https://github.com/open-sce/fluent-cli/actions/workflows/build.yml)

Domino Data Lab Command Line Interface is a client used provision and control Domino.

# Requirements

To use this CLI you will need the following:

- JDK 11+
- Domino API Key to an active Domino instance

# Build

To build the project requires Apache Maven:

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

# Test
To test the CLI is working, run the following command to print out your current user information:

```shell
$ java -jar ./target/domino-cli.jar user current
```

If you are not using environment variables it would be:

```shell
$ java -jar ./target/domino-cli.jar -k YOUR_KEY -u https://domino.yourcompany.com/v4 user current
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

# Releasing

- Run `mvn versions:set -DgenerateBackupPoms=false -DnewVersion=5.5.1` to update all modules versions
- Commit and push the changes to GitHub
- In GitHub create a new Release titled `5.5.1` to tag this release
- Run `mvn clean deploy -Prelease` to push to Maven Central

# License

Licensed under the [GNU General Public License version 3](https://opensource.org/license/gpl-3-0/).

`SPDX-License-Identifier: GPL-3.0-or-later`

# Copyright

Domino and Domino Data Lab are © 2023 Domino Data Lab, Inc. Made in San Francisco. 
