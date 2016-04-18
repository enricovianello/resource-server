# OSIAM resource server [![Circle CI](https://circleci.com/gh/osiam/resource-server.svg?style=svg)](https://circleci.com/gh/osiam/resource-server)

The resource server holds the SCIM based user data.

## Documentation

The documentation is contained in this repo and can be
found [here](docs/README.md).

## Usage

Prerequisites:

- `osiam` MySQL database exists and it's empty
- check [`resource-server.properties`](https://github.com/enricovianello/resource-server/blob/iam-rs/src/main/resources/resource-server.properties) MySQL user info and make it matches with your MySQL installation.

Launch:

```
mvn clean spring-boot:run
```
