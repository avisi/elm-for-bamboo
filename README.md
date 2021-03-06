---

# ! Add-on was Deprecated and removed from Marketplace.

If you want to use this add-on, please build it from the sources and upload the JAR in your Bamboo instance.

---

# Elm for Bamboo

Add Elm support to Bamboo. This Atlassian Bamboo add-on is available on the [marketplace](https://marketplace.atlassian.com/plugins/nl.avisi.bamboo.plugins.elm-for-bamboo/server/overview).
It is hosted via the [Avisi](http://addons.avisi.com/) Atlassian account, but the project is open source.
If you encounter issues you can create them in this Github project or in the Avisi [service desk](https://avisi-support.atlassian.net/servicedesk/customer/portal/18).
Pull requests are welcome.

Implemented tasks:

* **Elm Test Runner** Runs `elm-test` and stores the output.
* **Elm Test Parser** Parses the output from the _Elm Test Runner_ task and integrates with the default Bamboo test results.
* **Elm Format Checker** Verifies if source code is conform to `elm-format`.

## Tasks

### Elm Test Runner

The Elm Test Runner will run `elm-test` automatically. At this moment the add-on assumes that `elm-test` is available at `./node_modules/.bin/elm-test` and `elm-make` is available at `./node_modules/.bin/elm-make` (`elm-make` is required for `elm-test`).
To parse the results of the test runner later on in the build, a test output file should be specified.

> In the future we will probably make the executables configurable. But it is always a good practice to make a Bamboo host agnostic and thus add `elm` and `elm-test` to a `package.json` so that these can be downloaded by the agent and versions do not collide with other project in Bamboo instance.

An example task configuration would look like:

![Sample Elm Test Runner Task](resources/screenshots/sample-elm-test-runner-task.png?raw=true "Sample Elm Test Runner Task")

### Elm Test Parser

The Elm Test Runner will collect the test results, which are stored by _Elm Test Runner_ and add these to Bamboo.
When you configure this task, make sure that the `Test Output File` configuration of this task is equal to the one specified at the _Elm Test Runner_ task.

An example task configuration would look like:

![Sample Elm Test Parser Task](resources/screenshots/sample-elm-test-parser-task.png?raw=true "Sample Elm Test Parser Task")

### Elm Format Checker

The Elm Format Checker will verify if the source code verifies the style defined by `elm-format`.
You can configure the path to `elm-format` (we do not rely on a npm module, since it is not there [yet](https://github.com/avh4/elm-format/pull/288)). If `elm-format` is not available on the Bamboo agent and you want to download anyway, maybe you are interested in using [elm-format-download](https://github.com/stil4m/elm-format-download).

The _Paths to validate with elm-format_ defaults to `src`. If you want to validate multiple paths, please use a comma separated list (`src,tests`).

![Sample Elm Format Task](resources/screenshots/sample-elm-format-task.png?raw=true "Sample Elm Format Task")

## Build

```
atlas-mvn package
```

## License

This is free and unencumbered software released into the public domain. See LICENSE for more details.
