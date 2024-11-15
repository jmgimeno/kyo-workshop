# Scala.io 2024: Kyo Workshop

## Setup
1. Install SBT (Simple Build Tool). Instructions can be found [here](https://www.scala-sbt.org/download/).
2. Clone the repo and cd into the directory
3. Open any IDE, though I recommend VS Code + Metals:
  * [VS Code](https://code.visualstudio.com/)
    * [Scala Syntax Highlighting](https://marketplace.visualstudio.com/items?itemName=scala-lang.scala)
    * [Metals](https://marketplace.visualstudio.com/items?itemName=scalameta.metals)

```bash
git clone git@github.com:hearnadam/kyo-workshop.git &&
cd kyo-workshop
```

## Workshop Flow

The workshop is designed to be completed in order of the files, 00 -> 03.
Each file has a set of test suites, with a few exercises interspersed.

After completing a test, you can remove the `@@ ignore` to ensure the test is run. To run the tests, start an SBT shell with `sbt`, then run `run` in the shell. It will prompt you for which Main class to run.

## Solutions

Solutions can be found in the `solutions` branch of this repo. It will be kept up to date as exercises are added/removed.

## Contributions

If you find any issues, bugs, or have any suggestions, please open an issue or submit a PR!