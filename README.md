# Require4Testing
Fallstudie 2 im Modul: Programmierung von industriellen Informationssysteme mit Java EE

Stammdaten Sql für relationale Datenbank
## Sequences für Tabellen
### Requirement
```sql
CREATE SEQUENCE IF NOT EXISTS public."REQUIREMENT_REQUIREMENTID_seq"
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1
    OWNED BY requirement.requirementid;
```

### Testcase
```sql
CREATE SEQUENCE IF NOT EXISTS public."TESTCASE_TESTCASEID_seq"
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1
    OWNED BY testcase.testcaseid;
```
### Testrun
```sql
CREATE SEQUENCE IF NOT EXISTS public."TESTRUN_TESTRUNID_seq"
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1
    OWNED BY testrun.testrunid;
```
### Teststep
```sql
CREATE SEQUENCE IF NOT EXISTS public."TESTSTEP_TESTSTEPID_seq"
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
```
### Users
```sql
CREATE SEQUENCE IF NOT EXISTS public."USER_Id_seq"
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1
    OWNED BY users.userid;
```
### Usertype
```sql
CREATE SEQUENCE IF NOT EXISTS public."USERTYPES_USERTYPEID_seq"
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1
    OWNED BY usertype.usertypeid;
```


## Tabellen - Create
### Requirement
```sql
CREATE TABLE IF NOT EXISTS public.requirement
(
    requirementid integer NOT NULL DEFAULT nextval('"REQUIREMENT_REQUIREMENTID_seq"'::regclass),
    title character varying(255) COLLATE pg_catalog."default",
    description character varying(255) COLLATE pg_catalog."default",
    done boolean,
    testuser_id character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT "REQUIREMENT_pkey" PRIMARY KEY (requirementid)
)
```
### Testcase
```sql
CREATE TABLE IF NOT EXISTS public.testcase
(
    testcaseid integer NOT NULL DEFAULT nextval('"TESTCASE_TESTCASEID_seq"'::regclass),
    description character varying(255) COLLATE pg_catalog."default",
    requirement_id integer,
    testrun_id integer,
    status character varying(255) COLLATE pg_catalog."default",
    requirementtitle character varying(255) COLLATE pg_catalog."default",
    user_id integer,
    CONSTRAINT "TESTCASE_pkey" PRIMARY KEY (testcaseid),
    CONSTRAINT fk6ysvpjyjwp8e4v6xsri8ougkb FOREIGN KEY (requirement_id)
        REFERENCES public.requirement (requirementid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk9s8xwn53dembyjiroujy5if4k FOREIGN KEY (testrun_id)
        REFERENCES public.testrun (testrunid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
```
### Testrun
```sql
CREATE TABLE IF NOT EXISTS public.testrun
(
    testrunid integer NOT NULL DEFAULT nextval('"TESTRUN_TESTRUNID_seq"'::regclass),
    runnr integer,
    requirement_id integer,
    user_id integer,
    requirementtitle character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT "TESTRUN_pkey" PRIMARY KEY (testrunid),
    CONSTRAINT fkodscgbbb5onywlq7682nfpmwo FOREIGN KEY (requirement_id)
        REFERENCES public.requirement (requirementid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
```
### Teststep
```sql
CREATE TABLE IF NOT EXISTS public.teststep
(
    teststepid integer NOT NULL DEFAULT nextval('"TESTSTEP_TESTSTEPID_seq"'::regclass),
    testcase_id integer,
    title character varying(255) COLLATE pg_catalog."default",
    testcasedescription character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT teststep_pkey PRIMARY KEY (teststepid),
    CONSTRAINT fk836x7wh9nxi2sai34bfno2s2a FOREIGN KEY (testcase_id)
        REFERENCES public.testcase (testcaseid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
```
### Users
```sql
CREATE TABLE IF NOT EXISTS public.users
(
    userid integer NOT NULL DEFAULT nextval('"USER_Id_seq"'::regclass),
    username character varying(255) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    usertype integer,
    CONSTRAINT "USER_pkey" PRIMARY KEY (userid)
)
```
### Usertype
```sql
CREATE TABLE IF NOT EXISTS public.usertype
(
    usertypeid integer NOT NULL DEFAULT nextval('"USERTYPES_USERTYPEID_seq"'::regclass),
    usertype text COLLATE pg_catalog."default",
    CONSTRAINT "USERTYPES_pkey" PRIMARY KEY (usertypeid)
)
```
## Stammdaten - Inserts
### Users
```sql
INSERT INTO users (userid, username, password, usertype) VALUES (1, 'Admin', 'admin', 0);
INSERT INTO users (userid, username, password, usertype) VALUES (2, 'engineer', 'admin', 1);
INSERT INTO users (userid, username, password, usertype) VALUES (3, 'manager', 'admin', 2);
INSERT INTO users (userid, username, password, usertype) VALUES (4, 'testfall', 'admin', 3);
INSERT INTO users (userid, username, password, usertype) VALUES (5, 'tester', 'admin', 4);
INSERT INTO users (userid, username, password, usertype) VALUES (6, 'tester2', 'admin', 4);
```
### usertype
```sql
INSERT INTO usertype (usertypeid, usertype) VALUES (1, 'Requirement Engineer');
INSERT INTO usertype (usertypeid, usertype) VALUES (2, 'Testmanager');
INSERT INTO usertype (usertypeid, usertype) VALUES (3, 'Tester');
```
