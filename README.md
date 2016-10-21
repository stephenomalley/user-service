RestAPI for simple user model
================================

[![build status](https://gitlab.com/stephenomalley/user-service/badges/master/build.svg)](https://gitlab.com/stephenomalley/user-service/commits/master)
-----

| Method | URI | Data | HTTP Code | Response (in JSON) |
| ------ | --- | ---- | --------- | ------------------ |
| GET  | /users/id |  | 200 | {"id": 1,"name": "somalley"}    |
| GET  | /users/   |  | 200 | [{"id": 1,"name": "somalley"},] |
| POST | /users/   |  | 201 | {"id": 1,"name": "somalley"}    |

CHANGELOG
=========

## Version 1.0

- App base


TODO
====
* Need to refresh homepage after post is made
* Need to add update end point
* Need to add delete end point
* Fix tests
* Add angular tests
* Add Tests for create functionality (Duplicate)
* Resolve issues with creating test database in unit tests

