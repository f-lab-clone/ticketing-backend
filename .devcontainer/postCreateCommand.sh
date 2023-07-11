#!/bin/bash

echo "postCreateCommand hook script is running"

git config --local commit.template commit-msg-template.txt
cp .github/hooks/prepare-commit-msg .git/hooks/prepare-commit-msg
cp .github/hooks/pre-commit .git/hooks/pre-commit
chmod +x .git/hooks/prepare-commit-msg
chmod +x .git/hooks/pre-commit