hibernate4-eventcache
=====================

Test cascade merge on multiple jpa providers:

Merging same entity from differents path in an object graph.

Hibernate 4 does not work in this case since this commit:
* https://hibernate.atlassian.net/browse/HHH-6848
* https://github.com/hibernate/hibernate-orm/pull/208
