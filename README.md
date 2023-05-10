`How to configure application:` 
- Go to https://start.spring.io/
Project -> Maven 
Language -> Java
- use latest spring boot relased version(that does not have snapshot)
- use installed java version
- Dependencies: 
Spring Web, Devtools, Actuator


`Install mysql on docker`
- docker run --detach --env MYSQL_ROOT_PASSWORD=dummypassword --env MYSQL_USER=social-media-user --env MYSQL_PASSWORD=dummypassword --env MYSQL_DATABASE=social-media-database --name mysql --publish 3306:3306 mysql:8-oracle
