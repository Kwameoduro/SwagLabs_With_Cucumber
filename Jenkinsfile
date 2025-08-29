pipeline {
	agent {
		label "Jenkins-agent"
    }

    stages {
		stage('Checkout Code') {
			steps {
				checkout scm
            }
        }

        stage('Build Project') {
			steps {
				sh 'java -version'
                sh 'mvn test-compile'
            }
        }

        stage('Build Docker Image') {
			steps {
				echo ">>> Building Docker image selenium-cucumber-tests"
                sh 'docker build -t selenium-cucumber-tests .'
            }
        }

        stage('Run Tests in Docker') {
			stage('Chrome') {
				steps {
					echo ">>> Running Cucumber tests inside Docker container (Chrome)"
                    catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
						sh '''
                            docker run --rm selenium-cucumber-tests \
                            -v $WORKSPACE/allure-results/chrome:/app/allure-results \
                            -v $WORKSPACE:/app -w /app SwagLabs_With_Cucumber clean test
                        '''
                    }
                }
            }
        }
    }

    post {
		always {
			echo 'Publishing test results and reports...'

            // Archive all artifacts
            archiveArtifacts artifacts: 'target/**/*', allowEmptyArchive: true

            // Publish parsed Cucumber JSON report
            cucumber(
                buildStatus: 'UNSTABLE',
                fileIncludePattern: 'target/cucumber-reports/cucumber.json',
                sortingMethod: 'ALPHABETICAL'
            )

            // Publish HTML report
            publishHTML([
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'target/cucumber-reports',
                reportFiles: 'cucumber-html-report.html',
                reportName: 'Cucumber Test Report',
                reportTitles: 'BDD Test Results'
            ])
        }

        success {
			script {
				slackSend(
                    color: 'good',
                    message: """
🎉 *BUILD SUCCESS*
*Project:* SwagLabs With Cucumber
*Build:* #${env.BUILD_NUMBER}
*Duration:* ${currentBuild.durationString}
*Status:* All tests passed successfully

📊 *Reports Available:*
• Jenkins: ${env.BUILD_URL}
• Test Results: ${env.BUILD_URL}testReport/
• Cucumber Report: ${env.BUILD_URL}cucumber/

✅ Ready for deployment!
                    """.stripIndent()
                )

                emailext(
                    subject: "✅ Build Success - SwagLabs Cucumber Tests #${env.BUILD_NUMBER}",
                    body: """
<html>
<body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
    <div style="max-width: 600px; margin: 0 auto; padding: 20px;">
        <h2 style="color: #28a745; border-bottom: 2px solid #28a745; padding-bottom: 10px;">
            🎉 Build Successful
        </h2>
        ...
    </div>
</body>
</html>
                    """.stripIndent(),
                    mimeType: 'text/html',
                    to: "odurokwameee@gmail.com"
                )
            }
        }

        unstable {
			script {
				slackSend(
                    color: 'warning',
                    message: """
⚠️ *BUILD UNSTABLE*
*Project:* SwagLabs With Cucumber
*Build:* #${env.BUILD_NUMBER}
*Duration:* ${currentBuild.durationString}
*Status:* Some tests failed - requires attention

📊 *Reports Available:*
• Jenkins: ${env.BUILD_URL}
• Test Results: ${env.BUILD_URL}testReport/
• Cucumber Report: ${env.BUILD_URL}cucumber/

🔍 Please review failed tests before proceeding
                    """.stripIndent()
                )
                // (emailext block unchanged, omitted here for brevity)
            }
        }

        failure {
			script {
				slackSend(
                    color: 'danger',
                    message: """
🚨 *BUILD FAILED*
*Project:* SwagLabs With Cucumber
*Build:* #${env.BUILD_NUMBER}
*Duration:* ${currentBuild.durationString}
*Status:* Build process failed

📋 *Logs & Reports:*
• Jenkins: ${env.BUILD_URL}
• Console Output: ${env.BUILD_URL}console
• Build Artifacts: ${env.BUILD_URL}artifact/

🔧 Immediate attention required!
                    """.stripIndent()
                )
                // (emailext block unchanged, omitted here for brevity)
            }
        }
    }
}
