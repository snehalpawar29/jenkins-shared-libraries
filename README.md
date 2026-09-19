# 🔧 Jenkins Shared Libraries

![Jenkins](https://img.shields.io/badge/Jenkins-CI%2FCD-D24939?logo=jenkins&logoColor=white)
![Groovy](https://img.shields.io/badge/Groovy-Shared%20Library-4298B8?logo=apachegroovy&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Integration-2496ED?logo=docker&logoColor=white)
![Kubernetes](https://img.shields.io/badge/Kubernetes-Deployment-326CE5?logo=kubernetes&logoColor=white)
![Trivy](https://img.shields.io/badge/Trivy-Security%20Scanning-1904DA)

A collection of reusable **Jenkins Shared Library** steps written in Groovy for simplifying and standardizing common CI/CD pipeline tasks.

The library is organized under the Jenkins Shared Library `vars/` convention, allowing these reusable steps to be called from Jenkins pipelines instead of repeatedly defining the same pipeline logic.

---

## 📌 Overview

Jenkins Shared Libraries help reduce duplicated pipeline code by moving commonly used CI/CD operations into reusable functions.

This repository contains reusable pipeline steps for areas including:

- 🐳 Docker image building
- 📦 Docker image pushing
- 🧪 Test execution
- 🔍 Trivy security scanning
- ☸️ Kubernetes manifest updates
- 🧹 Jenkins workspace cleanup
- 📊 Report generation
- 👋 Basic reusable pipeline examples

---

# 🏗️ Library Structure

```text
jenkins-shared-libraries/
│
└── vars/
    ├── clean_ws.groovy
    ├── clone.groovy
    ├── docker_build.groovy
    ├── docker_push.groovy
    ├── generate_reports.groovy
    ├── hello.groovy
    ├── run_tests.groovy
    ├── trivy_scan.groovy
    └── update_k8s_manifests.groovy
````

---

# 🧩 Available Shared Steps

| Shared Step            | Purpose                                |
| ---------------------- | -------------------------------------- |
| `clean_ws`             | Workspace cleanup                      |
| `clone`                | Source-code cloning step               |
| `docker_build`         | Docker image build step                |
| `docker_push`          | Docker image push step                 |
| `generate_reports`     | Report generation step                 |
| `hello`                | Basic reusable Jenkins library example |
| `run_tests`            | Test execution step                    |
| `trivy_scan`           | Container security scanning step       |
| `update_k8s_manifests` | Kubernetes manifest update step        |

> The exact parameters and implementation of each step are defined in the corresponding `.groovy` file under `vars/`.

---

# 🔄 CI/CD Workflow

These shared steps can be combined to create a reusable CI/CD workflow such as:

```text
              Source Repository
                     │
                     ▼
                ┌─────────┐
                │  Clone  │
                └────┬────┘
                     │
                     ▼
                ┌─────────┐
                │  Tests  │
                └────┬────┘
                     │
                     ▼
             ┌──────────────┐
             │ Docker Build │
             └──────┬───────┘
                    │
                    ▼
             ┌─────────────┐
             │ Trivy Scan  │
             └──────┬──────┘
                    │
                    ▼
             ┌─────────────┐
             │ Docker Push │
             └──────┬──────┘
                    │
                    ▼
          ┌──────────────────────┐
          │ Update K8s Manifests │
          └───────────┬──────────┘
                      │
                      ▼
              Kubernetes Deploy
```

This provides a reusable structure for Jenkins pipelines while keeping individual Jenkinsfiles smaller and easier to maintain.

---

# ⚙️ Jenkins Shared Library Concept

A Jenkins Shared Library allows commonly used pipeline logic to be stored in a separate Git repository and reused across multiple Jenkins pipelines.

Instead of repeatedly writing pipeline logic:

```text
Project A Jenkinsfile
Project B Jenkinsfile
Project C Jenkinsfile
```

common functionality can be maintained centrally:

```text
                 Jenkins Shared Library
                         │
          ┌──────────────┼──────────────┐
          ▼              ▼              ▼
      Project A      Project B      Project C
      Pipeline       Pipeline       Pipeline
```

This makes common CI/CD operations easier to reuse and maintain.

---

# 📁 `vars/` Directory

The repository uses Jenkins' `vars/` convention for defining globally accessible pipeline steps.

Each Groovy file represents a reusable pipeline step:

```text
vars/
├── clean_ws.groovy
├── clone.groovy
├── docker_build.groovy
├── docker_push.groovy
├── generate_reports.groovy
├── hello.groovy
├── run_tests.groovy
├── trivy_scan.groovy
└── update_k8s_manifests.groovy
```

For example:

```text
docker_build.groovy
```

provides the reusable `docker_build` step.

Similarly:

```text
trivy_scan.groovy
```

provides the reusable `trivy_scan` step.

---

# 🚀 Using the Library in Jenkins

After configuring this repository as a Jenkins Global Shared Library, the reusable steps can be called from Jenkins pipelines.

A pipeline can then use shared steps instead of keeping all implementation logic directly inside the Jenkinsfile.

Example structure:

```groovy
@Library('jenkins-shared-libraries') _

pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                docker_build()
            }
        }

        stage('Security Scan') {
            steps {
                trivy_scan()
            }
        }
    }
}
```

> The example above illustrates the shared-library usage pattern. The exact arguments required by `docker_build` and `trivy_scan` depend on the implementations currently stored in this repository.

---

# 🔐 Security Integration

The library includes:

```text
trivy_scan.groovy
```

which is intended for integrating **Trivy container security scanning** into Jenkins pipelines.

This allows container images to be checked as part of the CI/CD workflow before continuing with subsequent deployment stages.

---

# ☸️ Kubernetes Integration

The library also contains:

```text
update_k8s_manifests.groovy
```

which provides a reusable step related to updating Kubernetes manifests as part of the deployment workflow.

This can help connect the CI pipeline with Kubernetes-based application deployment.

---

# 🐳 Docker Integration

Docker-related reusable steps include:

```text
docker_build.groovy
docker_push.groovy
```

These separate the Docker build and image publishing operations from individual Jenkinsfiles.

Typical workflow:

```text
Source Code
    │
    ▼
Docker Build
    │
    ▼
Security Scan
    │
    ▼
Docker Push
```

---

# 🧪 Testing & Reporting

The library contains dedicated steps for:

```text
run_tests.groovy
generate_reports.groovy
```

This allows testing and report generation logic to be reused across Jenkins pipelines.

---

# 🧹 Workspace Management

The repository also includes:

```text
clean_ws.groovy
```

for reusable Jenkins workspace cleanup functionality.

---

# 🎯 Benefits

Using shared pipeline steps provides several benefits:

* ♻️ Reusable CI/CD logic
* 🧹 Cleaner Jenkinsfiles
* 🔧 Centralized pipeline functionality
* 📦 Consistent build and deployment processes
* 🔍 Reusable security scanning
* ☸️ Kubernetes deployment integration
* 🐳 Reusable Docker operations
* 🧪 Standardized testing workflows

---

# 🧠 DevOps Concepts Demonstrated

This project demonstrates hands-on understanding of:

* Jenkins Shared Libraries
* Jenkins Pipeline
* Groovy
* CI/CD automation
* Docker
* Container security scanning
* Trivy
* Kubernetes
* Infrastructure/application deployment workflows
* Reusable pipeline design

---

# 📂 Repository Structure

```text
jenkins-shared-libraries/
│
└── vars/
    │
    ├── clean_ws.groovy
    ├── clone.groovy
    ├── docker_build.groovy
    ├── docker_push.groovy
    ├── generate_reports.groovy
    ├── hello.groovy
    ├── run_tests.groovy
    ├── trivy_scan.groovy
    └── update_k8s_manifests.groovy
```

---

# 👨‍💻 Author

## Snehal Pawar

**Aspiring DevOps Engineer | AWS | Docker | Kubernetes | Terraform | Jenkins**

GitHub: [snehalpawar29](https://github.com/snehalpawar29)

---

⭐ If you find this repository useful, consider giving it a star.
