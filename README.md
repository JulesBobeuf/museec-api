# Museec API

Museec is an application that allows users to find/seek musicians for their Bands.

## Tests

To run tests, use the following command :

	gradle test -i

Do not run tests using JUnit5. Standalone JUnit will not find the mapstructs mappers implementations

To send reports on Sonarqube :
	
	gradle sonar -D "sonar.login=<token>"
	
	
to build the jar of the application with builds : dev, qual, prod

	gradle clean bootJar -Penv=dev
	gradle clean bootJar -Penv=qual
	gradle clean bootJar -Penv=prod