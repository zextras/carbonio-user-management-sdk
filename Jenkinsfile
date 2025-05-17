// SPDX-FileCopyrightText: 2022 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: AGPL-3.0-only

pipeline {
    agent {
        node {
            label 'zextras-v1'
        }
    }
    environment {
        JAVA_OPTS='-Dfile.encoding=UTF8'
        LC_ALL='C.UTF-8'
        jenkins_build='true'
    }
    parameters {
        booleanParam defaultValue: false, description: 'Whether to upload the SNAPSHOT artifact', name: 'SNAPSHOT'
    }
    options {
        buildDiscarder(logRotator(numToKeepStr: '25'))
        timeout(time: 2, unit: 'HOURS')
    }
    stages {
        stage('Setup') {
            steps {
                withCredentials([file(credentialsId: 'jenkins-maven-settings.xml', variable: 'SETTINGS_PATH')]) {
                    sh 'cp $SETTINGS_PATH settings-jenkins.xml'
                }
            }
        }
        stage('Build') {
            steps {
                container('jdk-17') {
                    sh 'mvn -B --settings settings-jenkins.xml package'
                }
            }
        }
        stage('Publish SNAPSHOT') {
            when {
                allOf {
                    expression { params.SNAPSHOT == true }
                    expression { env.BRANCH_NAME != 'release' }
                }
            }
            steps {
                container('jdk-17') {
                    sh 'mvn -B --settings settings-jenkins.xml deploy'
                }
            }
        }
        stage('Publish version') {
            when {
                buildingTag()
            }
            steps {
                container('jdk-17') {
                    sh 'mvn -B --settings settings-jenkins.xml deploy'
                }
            }
        }
    }
}
