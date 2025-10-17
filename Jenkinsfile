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
        stage('Publish') {
            when {
                expression { env.BRANCH_NAME != 'develop' }
            }
            steps {
                script {
                    def profile = '-P dev'
                    if (env.TAG_NAME) {
                        profile = '-P prod'
                    }
                    container('jdk-17') {
                        sh "mvn -B --settings settings-jenkins.xml ${profile} deploy"
                    }
                }
            }
        }
        stage("UTs") {
            steps {
                container('jdk-17') {
                    sh 'mvn -B --settings settings-jenkins.xml verify -P run-unit-tests'
                }
            }
        }
        stage("ITs") {
            steps {
                container('dind') {
                    withDockerRegistry(credentialsId: 'private-registry', url: 'https://registry.dev.zextras.com') {
                        container('jdk-17') {
                            sh 'mvn -B --settings settings-jenkins.xml verify -P run-integration-tests'
                        }
                    }
                }
            }
        }
        stage('Coverage') {
            steps {
                container('jdk-17') {
                    sh 'mvn -B --settings settings-jenkins.xml verify -P generate-jacoco-full-report'
                    recordCoverage(tools: [[parser: 'JACOCO']],sourceCodeRetention: 'MODIFIED')
                }
            }
        }
        stage('Publish SNAPSHOT') {
            when {
              expression { env.BRANCH_NAME != 'develop' }
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
                    sh 'mvn -B --settings settings-jenkins.xml -Dchangelist= deploy'
                }
            }
        }
    }
}
