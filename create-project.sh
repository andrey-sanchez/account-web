#!/usr/bin/env bash

ARCHETYPE_ARTIFACT_ID=maven-archetype-quickstart
GROUP_ID=com.bankito.account
ARTIFACT_ID=account-web
VERSION=1.0

mvn archetype:generate \
  -DinteractiveMode=false \
  -DarchetypeArtifactId=$ARCHETYPE_ARTIFACT_ID \
  -DgroupId=$GROUP_ID \
  -DartifactId=$ARTIFACT_ID \
  -Dversion=$VERSION

mv ./$ARTIFACT_ID/* ./
rm -rf ./$ARTIFACT_ID