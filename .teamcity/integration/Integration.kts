package integration

import common.python.buildPythonPackage
import common.python.publishPythonPackageToHosted
import common.templates.NexusDockerLogin
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.DslContext
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.triggers.vcs

class Integration(dockerToolsTag: String) : BuildType({
    templates(NexusDockerLogin)

    id("Integration")
    name = "Integration"

    vcs {
        root(DslContext.settingsRoot)
    }

    triggers {
        vcs {
            branchFilter = """
                +:*
            """.trimIndent()
        }
    }

    steps {
        buildPythonPackage(dockerToolsTag)
        publishPythonPackageToHosted(dockerToolsTag)
    }

    features {
        perfmon {
        }
    }
})