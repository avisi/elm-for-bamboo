# Elm for Bamboo

Add Elm support to Bamboo. This Atlassian Bamboo add-on is available on the marketplace.

Implemented tasks

* **Elm Test Runner** Runs `elm-test` and stores the output.
* **Elm Test Parser** Parses the output from the *Elm Test Runner* task and integrates with the default Bamboo test results.
* **Elm Format Checker** Verifies if source code is conform to `elm-format`.

## Tasks

### Elm Test Runner

The Elm Test Runner will run `elm-test` automatically. At this moment the add-on assumes that `elm-test` is available at `./node_modules/.bin/elm-test` and `elm-make` is available at `./node_modules/.bin/elm-make` (`elm-make` is required for `elm-test`).
To parse the results of the test runner later on in the build, a test output file should be specified.

An example task configuration would look like:

![Alt text](resources/screenshots/sample-elm-test-runner-task.png?raw=true "Sample Elm Test Runner Task")


### Elm Test Parser

TODO

### Elm Format Checker

TODO

## License

This is free and unencumbered software released into the public domain.

See LICENSE for more details.
