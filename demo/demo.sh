#!/bin/bash

# Function to display script usage
usage() {
  echo "Usage: $0 -n <project name>"
  exit 1
}

# Parse command-line options using GetOpt
while getopts ":n:p:" opt; do
  case $opt in
    n) project_name=$OPTARG;;
    \?) echo "Invalid option -$OPTARG" >&2
        usage;;
    :) echo "Option -$OPTARG requires an argument." >&2
       usage;;
  esac
done

# Check if all required parameters are provided
if [[ -z $project_name ]]; then
  echo "Missing required -n parameter(s)." >&2
  usage
fi

printf "Getting Domino user ID..."
domino_user_id=$(domino user current | jq -r '.id')
printf "%s\n" $domino_user_id

printf "Getting Github credentials ID..."
github_credential_id=$(domino user credentials | jq -r '.[] | select(.name=="KSMPartners Github") | .id')
printf "%s\n" $github_credential_id

printf "Creating project %s with ID..." "$project_name"
domino_project_id=$(domino project create name="$project_name" ownerId=$domino_user_id mainRepoUrl="https://github.com/ksmpartners/mathworks.git" repoProvider=github credentialId=$github_credential_id | jq -r '.id')
printf "%s\n" $domino_project_id

printf "Starting job to count primes to %s with ID..." "$primes_max"
domino_job_id=$(domino job start projectId=$domino_project_id commandToRun='python3 count-primes.py 3000' | jq -r '.id')
printf "%s\n" $domino_job_id

# domino job get jobId=$domino_job_id