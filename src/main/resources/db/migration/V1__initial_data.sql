create schema wikicredit;

create table wikicredit.company
(
    id int auto_increment,
    name varchar2 not null
);

comment on table wikicredit.company is 'Our table for entity - company name, identified by by id';

create unique index COMPANY_ID_UINDEX
    on wikicredit.company (id);

alter table wikicredit.company
    add constraint COMPANY_PK
        primary key (id);

INSERT INTO WIKICREDIT.COMPANY (NAME) VALUES ('Swedbank');
INSERT INTO WIKICREDIT.COMPANY (NAME) VALUES ('AirBaltic');
INSERT INTO WIKICREDIT.COMPANY (NAME) VALUES ('Ave_Line');
INSERT INTO WIKICREDIT.COMPANY (NAME) VALUES ('Olainfarm');
INSERT INTO WIKICREDIT.COMPANY (NAME) VALUES ('VEF');

create table wikicredit.wikipedia_data
(
    id int auto_increment primary key,
    company_id int unique,
    page_id int,
    summary text,
    created_at timestamp,
    updated_at timestamp,
    article_exists boolean default false,
    foreign key (company_id) references wikicredit.company(id)
);

comment on table wikicredit.wikipedia_data is 'Data loaded from Wikipedia REST API';

INSERT INTO wikicredit.wikipedia_data (company_id, page_id, summary, created_at, updated_at, article_exists)
VALUES (1, 123, 'Insert cool company summary here', now(), now(), true);

commit;