RS Components UI tests

This project is dedicated to testing RS Components UI via Selenium/Java/Cucumber-JVM facilities.

[[quick]]
== Quick start

. Clone project
. Open in IDEA
. Create new *JUNIT* run/debug configuration with the below parameters
Class = "steps.Runner"
_VM options = "-Dcucumber.options="--tags @SearchByMenu"


You can use various **tags** like **~@Ignore** to exclude some scenarios from the test suit
**`mvn test -DcucumberOptions=" --tags ~@Filters2,@SearchByMenu"`**





