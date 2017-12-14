node('local') {
    try {

        stage('Init') {
            env.JAVA_HOME="${tool 'JDK 8u102'}"
            env.PATH="${env.JAVA_HOME}/bin:${env.PATH}"
            sh 'java -version'
            tool 'sbt 0.13.15'
            checkout scm
            sh 'git submodule update --init --recursive'
        }

        stage('Build & Test') {
            sh "${tool name: 'sbt 0.13.15', type: 'org.jvnet.hudson.plugins.SbtPluginBuilder$SbtInstallation'}/bin/sbt -no-colors clean scalafmt coverage test coverageReport coverageAggregate makeSite biopetGenerateReadme assembly"
            sh "java -jar target/scala-2.11/*-assembly-*.jar -h"
            sh "git diff --exit-code || (echo \"ERROR: Git changes detected, please regenerate the readme and run scalafmt with: sbt biopetGenerateReadme scalafmt\" && exit 1)"
        }

        stage('Results') {
            step([$class: 'ScoveragePublisher', reportDir: 'target/scala-2.11/scoverage-report/', reportFile: 'scoverage.xml'])
            junit '**/test-output/junitreports/*.xml'
        }

        if (env.BRANCH_NAME == 'develop') stage('Publish') {
            sh "${tool name: 'sbt 0.13.15', type: 'org.jvnet.hudson.plugins.SbtPluginBuilder$SbtInstallation'}/bin/sbt -no-colors publishSigned ghpagesPushSite"
        }

        if (currentBuild.result == null || "SUCCESS" == currentBuild.result) {
            currentBuild.result = "SUCCESS"
            slackSend(color: '#00FF00', message: "${currentBuild.result}: Job '${env.JOB_NAME} #${env.BUILD_NUMBER}' (<${env.BUILD_URL}|Open>)", channel: '#biopet-bot', teamDomain: 'lumc', tokenCredentialId: 'lumc')
        } else {
            slackSend(color: '#FFFF00', message: "${currentBuild.result}: Job '${env.JOB_NAME} #${env.BUILD_NUMBER}' (<${env.BUILD_URL}|Open>)", channel: '#biopet-bot', teamDomain: 'lumc', tokenCredentialId: 'lumc')
        }
    } catch (e) {

        if (currentBuild.result == null || "FAILED" == currentBuild.result) {
            currentBuild.result = "FAILED"
            slackSend(color: '#FF0000', message: "${currentBuild.result}: Job '${env.JOB_NAME} #${env.BUILD_NUMBER}' (<${env.BUILD_URL}|Open>)", channel: '#biopet-bot', teamDomain: 'lumc', tokenCredentialId: 'lumc')
        } else {
            slackSend(color: '#FFFF00', message: "${currentBuild.result}: Job '${env.JOB_NAME} #${env.BUILD_NUMBER}' (<${env.BUILD_URL}|Open>)", channel: '#biopet-bot', teamDomain: 'lumc', tokenCredentialId: 'lumc')
        }

        junit '**/test-output/junitreports/*.xml'

        throw e
    }

}
