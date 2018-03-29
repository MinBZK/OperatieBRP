node('migr') {
    stage('Checkout') {
        // Get the code
        checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'LocalBranch', localBranch: 'master']], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '41a3ccc6-713d-4688-824d-b948355d06da', url: 'ssh://fac-git.modernodam.nl/operatiebrp-code.git']]])

        // Get the Maven tool.
        mvnHome = tool name: 'Maven3.3.9', type: 'maven'
        mvnRepo = pwd() + '/../../maven-repositories/' + env.EXECUTOR_NUMBER + '/'
        mvnCommand = '\'' + mvnHome + '/bin/mvn\' -D\'maven.repo.local=' + mvnRepo + '\' --batch-mode --errors'
    }

    // Start tracking concurrent builds
    milestone label: 'Build', ordinal: 1

    stage('Build') {
        // Clean jacoco coverage files
        sh "${mvnCommand} -pl :jacoco-clean clean:clean"

        // Compile and install the code (libraries)
        sh "${mvnCommand} clean install -DskipTests -T4"
    }

    stage('Unit tests') {
        // May run a maximum of 2 hours
        timeout(time: 2, unit: 'HOURS') {
            // Execute tests
            parallel failFast: false,
                    algemeenBranch: {
                        sh "${mvnCommand} -Dmaven.test.failure.ignore verify -DskipITs -P!modules-brp,!modules-build,!modules-deployment,!modules-distributie,!modules-migratie,coverage"
                    },
                    brpBranch: {
                        sh "${mvnCommand} -Dmaven.test.failure.ignore verify -DskipITs -P!modules-algemeen,!modules-build,!modules-deployment,!modules-distributie,!modules-migratie,coverage,inmemory"
                    },
                    deploymentBranch: {
                        sh "${mvnCommand} -Dmaven.test.failure.ignore verify -DskipITs -P!modules-algemeen,!modules-brp,!modules-build,!modules-distributie,!modules-migratie,coverage"
                    },
                    distributieBranch: {
                        sh "${mvnCommand} -Dmaven.test.failure.ignore verify -DskipITs -P!modules-algemeen,!modules-brp,!modules-build,!modules-deployment,!modules-migratie,coverage"
                    },
                    migratieBranch: {
                        sh "${mvnCommand} -Dmaven.test.failure.ignore verify -DskipITs -P!modules-algemeen,!modules-brp,!modules-build,!modules-deployment,!modules-distributie,coverage"
                    }
            //sh "${mvnCommand} -Dmaven.test.failure.ignore verify -Pcoverage,inmemory,dataaccess-postgres"
        }

        // Collect jUnit reports
        junit '**/target/surefire-reports/*.xml'
    }

    stage('Integration tests') {
        // May run a maximum of 2 hours
        timeout(time: 2, unit: 'HOURS') {
            // Execute tests
            parallel failFast: false,
                    algemeenBranch: {
                        sh "${mvnCommand} -Dmaven.test.failure.ignore failsafe:integration-test -P!modules-brp,!modules-build,!modules-deployment,!modules-distributie,!modules-migratie,coverage"
                    },
                    brpBranch: {
                        sh "${mvnCommand} -Dmaven.test.failure.ignore failsafe:integration-test -P!modules-algemeen,!modules-build,!modules-deployment,!modules-distributie,!modules-migratie,coverage,inmemory,dataaccess-postgres"
                    },
                    deploymentBranch: {
                        sh "${mvnCommand} -Dmaven.test.failure.ignore failsafe:integration-test -P!modules-algemeen,!modules-brp,!modules-build,!modules-distributie,!modules-migratie,coverage"
                    },
                    distributieBranch: {
                        sh "${mvnCommand} -Dmaven.test.failure.ignore failsafe:integration-test -P!modules-algemeen,!modules-brp,!modules-build,!modules-deployment,!modules-migratie,coverage"
                    },
                    migratieBranch: {
                        sh "${mvnCommand} -Dmaven.test.failure.ignore failsafe:integration-test -P!modules-algemeen,!modules-brp,!modules-build,!modules-deployment,!modules-distributie,coverage"
                    }
            //sh "${mvnCommand} -Dmaven.test.failure.ignore verify -Pcoverage,inmemory,dataaccess-postgres"
        }

        // Collect jUnit reports
        junit '**/target/failsafe-reports/*.xml'
    }

    lock(inversePrecedence: true, resource: 'operatie-brp-regressie') {
        stage('Regressie') {
            // May run a maximum of 1 hour
            timeout(time: 1, unit: 'HOURS') {
                // Execute regression tests
                parallel failFast: false,
                        afnemersindicatieBranch: {
                            echo 'Regressietest afnemersindicatie (voor coverage in SonarQube)'
                            sh "${mvnCommand} -Dmaven.test.failure.ignore verify -Pregressie-coverage,coverage,inmemory -pl :migr-test-afnemersindicatie"
                        },
                        autorisatieBranch: {
                            echo 'Regressietest autorisatie (voor coverage in SonarQube)'
                            sh "${mvnCommand} -Dmaven.test.failure.ignore verify -Pregressie,coverage,inmemory -pl :migr-test-autorisatie"
                        },
                        persoonDatabaseBranch: {
                            echo 'Regressietest persoon-database (voor coverage in SonarQube)'
                            sh "${mvnCommand} -Dmaven.test.failure.ignore verify -Pregressie,coverage,inmemory -pl :migr-test-persoon-database"
                        },
                        persoonNaarBrpBranch: {
                            echo 'Regressietest persoon-naarbrp (voor coverage in SonarQube)'
                            sh "${mvnCommand} -Dmaven.test.failure.ignore verify -Pregressie-coverage,coverage,inmemory -pl :migr-test-persoon-naarbrp"
                        },
                        persoonPreconditieBranch: {
                            echo 'Regressietest persoon-precondities (voor coverage in SonarQube)'
                            sh "${mvnCommand} -Dmaven.test.failure.ignore verify -Pregressie-coverage,coverage,inmemory -pl :migr-test-persoon-preconditie"
                        }
            }
        }

        // Cancel all older builds that have not reached this step (and are probably waiting for the lock)
        milestone label: 'Regressie', ordinal: 30
    }
}
