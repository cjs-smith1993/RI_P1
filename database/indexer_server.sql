DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS projects;
DROP TABLE IF EXISTS fields;
DROP TABLE IF EXISTS batches;
DROP TABLE IF EXISTS record_values;

CREATE TABLE users
(
	id		integer		not null	primary key	autoincrement,
	username	text		not null	unique,
	password	text		not null,
	lastname	text		not null,
	firstname	text		not null,
	email		text		not null,
	indexedrecords	integer		not null	default 0,
	batch_id	integer
);

CREATE TABLE projects
(
	id		integer		not null	primary key	autoincrement,
	title		text		not null	unique,
	recordsperimage	integer		not null,
	firstycoord	integer		not null,
	recordheight	integer		not null
);

CREATE TABLE fields
(
	id		integer		not null	primary key	autoincrement,
	title		text		not null,
	xcoord		integer		not null,
	width		integer		not null,
	helphtml	text		not null,
	knowndata	text,
	field_num	integer		not null,
	project_id	integer		not null
);

CREATE TABLE batches
(
	id		integer		not null	primary key	autoincrement,
	file		text		not null	unique,
	project_id	integer		not null,
	user_id		integer		not null,
	status		integer		not null	default 0
);

CREATE TABLE record_values
(
	id		integer		not null	primary key	autoincrement,
	record_value		text,
	record_num	integer		not null,
	field_num	integer		not null,
	batch_id	integer		not null
);
