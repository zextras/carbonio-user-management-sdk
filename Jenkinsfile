// SPDX-FileCopyrightText: 2022 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: AGPL-3.0-only

pipeline {
    agent {
        node {
            label 'openjdk11-agent-v1'
        }
    }
    environment {
        JAVA_OPTS="-Dfile.encoding=UTF8"
        LC_ALL="C.UTF-8"
        jenkins_build="true"
    }
    options {
        buildDiscarder(logRotator(numToKeepStr: '25'))
        timeout(time: 2, unit: 'HOURS')
    }
    stages {
        stage('Setup') {
            steps {
                withCredentials([file(credentialsId: 'jenkins-maven-settings.xml', variable: 'SETTINGS_PATH')]) {
                    sh "cp ${SETTINGS_PATH} settings-jenkins.xml"
                }
            }
        }
        stage('Check SNAPSHOT version') {
            when {
                allOf {
                    expression { env.BRANCH_NAME != "release" }
                    expression { env.BRANCH_NAME.contains("PR") }
                }
            steps {
                def projectVersion = "mvn help:evaluate -Dexpression=project.version -q -DforceStdout"
                if (!projectVersion.contains('-SNAPSHOT')) {
                   currentBuild.result = 'ABORTED'
                   error('The current version of the project is not a SNAPSHOT')
                }
            }
        }
        stage('Build') {
            steps {
                sh 'mvn -B --settings settings-jenkins.xml package'
            }
        }
        stage('Publish version') {
            when {
                buildingTag()
            }
            steps {
                sh 'mvn -B --settings settings-jenkins.xml deploy'
            }
        }
    }
}
