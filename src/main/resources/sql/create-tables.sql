create table temperatures (
	"id" serial not null,
	"brew_id" integer not null,
	"timestamp" timestamp not null,
	"temperature" numeric(4, 2) not null
);

create table tokens (
	"id" serial not null,
	"token" varchar(64) not null
);

create table brews (
	"id" serial not null,
	"name" varchar(255) not null
);