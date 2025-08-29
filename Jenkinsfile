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

        stage('Run Tests') {
			steps {
				script {
					// Run tests but don't fail the build on test failures
                    // This allows Jenkins to process the test results
                    //try {
					sh 'mvn clean test -Dbrowser=chrome'
                    //} catch (Exception e) {
					//	echo "Some tests failed, but continuing to process results..."
                    //    echo "Error: ${e.getMessage()}"
                    //     Set build as unstable instead of failed
                        //currentBuild.result = 'FAILED'
                    //}
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
				//always {
				//	script {
				//		// Debug: List what's in the target directory
                //        sh '''
                //            echo "=== Target directory contents ==="
                //            ls -la target/cucumber-reports || echo "No target directory"
                //            echo "=== Surefire reports directory ==="
                //            ls -la target/surefire-reports/ || echo "No surefire-reports directory"
                //            echo "=== All XML files in target ==="
                //            find target -name "*.xml" -type f || echo "No XML files found"
                //        '''
                //
				//
                //        // Try to publish test results
                //        if (fileExists('target/surefire-reports/TEST-*.xml')) {
				//			junit 'target/surefire-reports/TEST-*.xml'
                //            archiveArtifacts artifacts: 'target/surefire-reports/**/*', allowEmptyArchive: true
                //        } else if (fileExists('target/surefire-reports/*.xml')) {
				//			junit 'target/surefire-reports/*.xml'
                //            archiveArtifacts artifacts: 'target/surefire-reports/**/*', allowEmptyArchive: true
                //        } else {
				//			echo "No JUnit XML test reports found"
                //            // Archive any available test reports
                //            archiveArtifacts artifacts: 'target/surefire-reports/**/*', allowEmptyArchive: true
                //        }
                //    }
                //}
            }
        }

        //stage('Publish Cucumber Report') {
		//	steps {
		//		script {
		//			if (fileExists('target/cucumber-reports')) {
		//				cucumber([
        //                    includeProperties: false,
        //                    jdk: '',
        //                    properties: [],
        //                    reportBuildPolicy: 'ALWAYS',
        //                    results: [[path: 'target/cucumber-reports']]
        //                ])
        //            } else {
		//				echo "No Cucumber results found at target/cucumber-reports"
        //            }
        //        }
        //    }
        //}


    }



post {
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

        <div style="background-color: #d4edda; border: 1px solid #c3e6cb; border-radius: 5px; padding: 15px; margin: 20px 0;">
            <h3 style="margin-top: 0; color: #155724;">Build Information</h3>
            <ul style="margin: 0;">
                <li><strong>Project:</strong> SwagLabs With Cucumber</li>
                <li><strong>Build Number:</strong> #${env.BUILD_NUMBER}</li>
                <li><strong>Duration:</strong> ${currentBuild.durationString}</li>
                <li><strong>Status:</strong> All tests executed successfully</li>
            </ul>
        </div>

        <div style="background-color: #f8f9fa; border: 1px solid #dee2e6; border-radius: 5px; padding: 15px; margin: 20px 0;">
            <h3 style="margin-top: 0; color: #495057;">📊 Available Reports</h3>
            <ul style="margin: 0;">
                <li><a href="${env.BUILD_URL}" style="color: #007bff; text-decoration: none;">📋 Jenkins Build Details</a></li>
                <li><a href="${env.BUILD_URL}testReport/" style="color: #007bff; text-decoration: none;">🧪 JUnit Test Results</a></li>
                <li><a href="${env.BUILD_URL}cucumber/" style="color: #007bff; text-decoration: none;">📈 Cucumber Test Report</a></li>
            </ul>
        </div>

        <p style="margin: 20px 0; padding: 15px; background-color: #e7f3ff; border-left: 4px solid #007bff; border-radius: 3px;">
            <strong>✅ Status:</strong> The SwagLabs with Cucumber has completed successfully. All endpoints are functioning as expected.
        </p>

        <p style="color: #6c757d; font-size: 0.9em; margin-top: 30px; text-align: center;">
            Automated by Jenkins CI/CD Pipeline<br>
            Generated on ${new Date().toString()}
        </p>
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

            emailext(
                subject: "⚠️ Build Unstable - SwagLabs With Cucumber Tests #${env.BUILD_NUMBER}",
                body: """
<html>
<body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
    <div style="max-width: 600px; margin: 0 auto; padding: 20px;">
        <h2 style="color: #ffc107; border-bottom: 2px solid #ffc107; padding-bottom: 10px;">
            ⚠️ Build Unstable
        </h2>

        <div style="background-color: #fff3cd; border: 1px solid #ffeeba; border-radius: 5px; padding: 15px; margin: 20px 0;">
            <h3 style="margin-top: 0; color: #856404;">Build Information</h3>
            <ul style="margin: 0;">
                <li><strong>Project:</strong> SwagLabs With Cucumber Test Suite</li>
                <li><strong>Build Number:</strong> #${env.BUILD_NUMBER}</li>
                <li><strong>Duration:</strong> ${currentBuild.durationString}</li>
                <li><strong>Status:</strong> Tests completed with some failures</li>
            </ul>
        </div>

        <div style="background-color: #f8f9fa; border: 1px solid #dee2e6; border-radius: 5px; padding: 15px; margin: 20px 0;">
            <h3 style="margin-top: 0; color: #495057;">📊 Detailed Reports</h3>
            <ul style="margin: 0;">
                <li><a href="${env.BUILD_URL}" style="color: #007bff; text-decoration: none;">📋 Jenkins Build Details</a></li>
                <li><a href="${env.BUILD_URL}testReport/" style="color: #007bff; text-decoration: none;">🧪 JUnit Test Results (Failed Tests)</a></li>
                <li><a href="${env.BUILD_URL}cucumber/" style="color: #007bff; text-decoration: none;">📈 Cucumber Test Report (Detailed Analysis)</a></li>
            </ul>
        </div>

        <div style="background-color: #fff3cd; border: 1px solid #ffeeba; border-radius: 5px; padding: 15px; margin: 20px 0;">
            <h3 style="margin-top: 0; color: #856404;">🔍 Next Steps</h3>
            <ol style="margin: 0;">
                <li>Review the failed test cases in the Cucumber report</li>
                <li>Check API endpoints for any issues</li>
                <li>Verify test data and environment configuration</li>
                <li>Fix failing tests and re-run the pipeline</li>
            </ol>
        </div>

        <p style="margin: 20px 0; padding: 15px; background-color: #fff3cd; border-left: 4px solid #ffc107; border-radius: 3px;">
            <strong>⚠️ Action Required:</strong> Some SwagLabs With Cucumnber tests have failed. Please review the reports and address the issues before deployment.
        </p>

        <p style="color: #6c757d; font-size: 0.9em; margin-top: 30px; text-align: center;">
            Automated by Jenkins CI/CD Pipeline<br>
            Generated on ${new Date().toString()}
        </p>
    </div>
</body>
</html>
                """.stripIndent(),
                mimeType: 'text/html',
                to: "odurokwameee@gmail.com"
            )
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

            emailext(
                subject: "🚨 Build Failed - SwagLabs With Cucumber Tests #${env.BUILD_NUMBER}",
                body: """
<html>
<body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
    <div style="max-width: 600px; margin: 0 auto; padding: 20px;">
        <h2 style="color: #dc3545; border-bottom: 2px solid #dc3545; padding-bottom: 10px;">
            🚨 Build Failed
        </h2>

        <div style="background-color: #f8d7da; border: 1px solid #f5c6cb; border-radius: 5px; padding: 15px; margin: 20px 0;">
            <h3 style="margin-top: 0; color: #721c24;">Build Information</h3>
            <ul style="margin: 0;">
                <li><strong>Project:</strong> SwagLabs With Cucumber Test Suite</li>
                <li><strong>Build Number:</strong> #${env.BUILD_NUMBER}</li>
                <li><strong>Duration:</strong> ${currentBuild.durationString}</li>
                <li><strong>Status:</strong> Build process failed to complete</li>
            </ul>
        </div>

        <div style="background-color: #f8f9fa; border: 1px solid #dee2e6; border-radius: 5px; padding: 15px; margin: 20px 0;">
            <h3 style="margin-top: 0; color: #495057;">🔍 Debug Resources</h3>
            <ul style="margin: 0;">
                <li><a href="${env.BUILD_URL}" style="color: #007bff; text-decoration: none;">📋 Jenkins Build Details</a></li>
                <li><a href="${env.BUILD_URL}console" style="color: #007bff; text-decoration: none;">📋 Console Output (Full Logs)</a></li>
                <li><a href="${env.BUILD_URL}artifact/" style="color: #007bff; text-decoration: none;">📁 Build Artifacts</a></li>
            </ul>
        </div>

        <div style="background-color: #f8d7da; border: 1px solid #f5c6cb; border-radius: 5px; padding: 15px; margin: 20px 0;">
            <h3 style="margin-top: 0; color: #721c24;">🔧 Troubleshooting Steps</h3>
            <ol style="margin: 0;">
                <li>Check the console output for compilation errors</li>
                <li>Verify all dependencies are available</li>
                <li>Ensure test environment is accessible</li>
                <li>Review recent code changes</li>
                <li>Contact the development team if issues persist</li>
            </ol>
        </div>

        <p style="margin: 20px 0; padding: 15px; background-color: #f8d7da; border-left: 4px solid #dc3545; border-radius: 3px;">
            <strong>🚨 Critical:</strong> The build process has failed. Immediate attention is required to restore the CI/CD pipeline.
        </p>

        <p style="color: #6c757d; font-size: 0.9em; margin-top: 30px; text-align: center;">
            Automated by Jenkins CI/CD Pipeline<br>
            Generated on ${new Date().toString()}
        </p>
    </div>
</body>
</html>
                """.stripIndent(),
                mimeType: 'text/html',
                to: "odurokwameee@gmail.com"
            )
        }
    }
}
}