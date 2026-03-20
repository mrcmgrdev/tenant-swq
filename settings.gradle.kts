plugins { id("org.danilopianini.gradle-pre-commit-git-hooks") version "2.1.10" }

gitHooks {
  commitMsg { conventionalCommits() }
  preCommit { tasks("spotlessCheck") }
  createHooks()
}

rootProject.name = "fh3"
