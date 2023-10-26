pipeline {
<<<<<<< Updated upstream
    agent any

    stages {
        stage('Hello') {
            steps {
                echo 'Hello World'
            }
        }
    }
}
=======
    agent { docker { image 'maven:3.9.4-eclipse-temurin-17-alpine' } }
    stages {
        stage('build') {
            steps {
                sh 'mvn --version'
            }
        }
    }
}
>>>>>>> Stashed changes
