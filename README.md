# Music Matcher API

## Tests

To run tests, use the following command :

	gradle test -i

Do not run tests using JUnit5. Standalone JUnit will not find the mapstructs mappers implementations

To send reports on Sonarqube :
	
	gradle sonar -D "sonar.login=f1985977d96e16596dcd11f9aff4237eeb64d032"