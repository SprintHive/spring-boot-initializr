#!/bin/bash

export REPO_NAME={{name}}
CREATE_REPO=true
CREATE_PIPELINE=true

while true; do
    read -p "Create a bitbucket repository and push this project to it? " yn
    case $yn in
        [Yy]* ) break;;
        [Nn]* ) CREATE_REPO=false; break;;
        * ) echo "Please answer yes or no.";;
    esac
done

if [  "$CREATE_REPO" = true ]; then
  echo "Fetching credentials from vault.."
  BITBUCKET_USER=$(vault read -field=username secret/ops/sprinthive/logins/bitbucket-initializr)
  BITBUCKET_PASS=$(vault read -field=password secret/ops/sprinthive/logins/bitbucket-initializr)

  REPO_EXISTS=$(curl -I -XGET -u $BITBUCKET_USER:$BITBUCKET_PASS https://api.bitbucket.org/2.0/repositories/sprinthive/$REPO_NAME 2>/dev/null | grep "HTTP/2 200")
  if [ -z "$REPO_EXISTS" ]; then
    echo "Creating Bitbucket Repository $REPO_NAME"
    curl -X POST -u $BITBUCKET_USER:$BITBUCKET_PASS https://api.bitbucket.org/2.0/repositories/sprinthive/$REPO_NAME -H 'Content-Type: application/json' -d '{"scm": "git", "project": {"key": "SPRIN"}, "is_private": true}'

    git init
    git checkout -b dev
    git add .
    git rm --cached $0
    git commit -m "Initial import"
    git remote add origin git@bitbucket.org:sprinthive/$REPO_NAME.git
    git push -u origin dev
  else
    echo "Repository already exists.. aborting"
  fi
fi

echo
while true; do
    read -p "Create a Jenkins pipeline for this project? " yn
    case $yn in
        [Yy]* ) break;;
        [Nn]* ) CREATE_PIPELINE=false; break;;
        * ) echo "Please answer yes or no.";;
    esac
done

if [  "$CREATE_PIPELINE" = true ]; then
  read -r -d '' TEMPLATE << EOL
  <?xml version='1.1' encoding='UTF-8'?>
  <flow-definition plugin="workflow-job@2.19">
    <description></description>
    <keepDependencies>false</keepDependencies>
    <properties>
      <org.jenkinsci.plugins.workflow.job.properties.PipelineTriggersJobProperty>
        <triggers>
          <com.cloudbees.jenkins.plugins.BitBucketTrigger plugin="bitbucket@1.1.8">
            <spec/>
          </com.cloudbees.jenkins.plugins.BitBucketTrigger>
        </triggers>
      </org.jenkinsci.plugins.workflow.job.properties.PipelineTriggersJobProperty>
    </properties>
    <definition class="org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition" plugin="workflow-cps@2.48">
      <scm class="hudson.plugins.git.GitSCM" plugin="git@3.8.0">
        <configVersion>2</configVersion>
        <userRemoteConfigs>
          <hudson.plugins.git.UserRemoteConfig>
            <url>https://bitbucket.org/sprinthive/\${REPO_NAME}.git</url>
            <credentialsId>bitbucket</credentialsId>
          </hudson.plugins.git.UserRemoteConfig>
        </userRemoteConfigs>
        <branches>
          <hudson.plugins.git.BranchSpec>
            <name>*/\${BRANCH}</name>
          </hudson.plugins.git.BranchSpec>
        </branches>
        <doGenerateSubmoduleConfigurations>false</doGenerateSubmoduleConfigurations>
        <submoduleCfg class="list"/>
        <extensions/>
      </scm>
      <scriptPath>Jenkinsfile</scriptPath>
      <lightweight>true</lightweight>
    </definition>
    <triggers/>
    <disabled>false</disabled>
  </flow-definition>
EOL

  export BRANCH=dev
  export JENKINS_ENDPOINT="https://jenkins.honeypot.sprinthive.tech"

  echo "Fetching credentials from vault.."
  export JENKINS_CREDS="admin:$(kubectl get secret -n infra cicd-jenkins -o jsonpath='{.data.jenkins-admin-password}' | base64 -D)"
  BITBUCKET_USER=$(vault read -field=username secret/ops/sprinthive/logins/bitbucket-jenkins)
  BITBUCKET_PASS=$(vault read -field=password secret/ops/sprinthive/logins/bitbucket-jenkins)

  JOB_SPEC=$(echo "$TEMPLATE" | perl -p -e 's/\$\{([^}]+)\}/defined $ENV{$1} ? $ENV{$1} : $&/eg')

  CRUMB=$(curl -s "$JENKINS_ENDPOINT/crumbIssuer/api/xml?xpath=concat(//crumbRequestField,\":\",//crumb)" -u $JENKINS_CREDS)
  JOB_EXISTS=$(curl -I -XGET "$JENKINS_ENDPOINT/job/$REPO_NAME/" -u $JENKINS_CREDS  -H "$CRUMB" 2>/dev/null | grep "HTTP/1.1 200 OK")
  if [ -z "$JOB_EXISTS" ]; then
    echo "Creating Jenkins Pipeline"
    curl -s -XPOST "$JENKINS_ENDPOINT/createItem?name=$REPO_NAME" -u $JENKINS_CREDS --data-binary "$JOB_SPEC" -H "Content-Type:text/xml" -H "$CRUMB"
  else
    echo "Jenkins pipeline already exists"
  fi

  HOOK_EXISTS=$(curl -u $BITBUCKET_USER:$BITBUCKET_PASS https://api.bitbucket.org/2.0/repositories/sprinthive/$REPO_NAME/hooks -H 'Content-Type: application/json' 2>/dev/null | grep "Jenkins Build Trigger")
  if [ -z "$HOOK_EXISTS" ]; then
    echo "Creating Jenkins Webhook"
    curl -X POST -u $BITBUCKET_USER:$BITBUCKET_PASS https://api.bitbucket.org/2.0/repositories/sprinthive/$REPO_NAME/hooks -H 'Content-Type: application/json' -d '{"description": "Jenkins Build Trigger", "url": "https://jenkins.honeypot.sprinthive.tech/bitbucket-hook/", "active": true, "events": ["repo:push"]}' > /dev/null 2>&1
  else
    echo "Jenkins Webhook was already present"
  fi

fi

echo
while true; do
    read -p "Delete this script now that its presumably no longer needed? " yn
    case $yn in
        [Yy]* ) rm $0; exit;;
        [Nn]* ) exit;;
        * ) echo "Please answer yes or no.";;
    esac
done
