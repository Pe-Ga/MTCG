create type "monsterType" as enum ('Goblin', 'Dragon', 'Wizzard', 'Orc', 'Knight', 'Kraken', 'Elve', 'Spell');

alter type "monsterType" owner to swen1user;

create type "elementType" as enum ('Fire', 'Water', 'Normal');

alter type "elementType" owner to swen1user;

create table testtable
(
    username varchar not null
);

alter table testtable
    owner to swen1user;

create table "User"
(
    "userId"              serial
        constraint "User_pk"
            primary key,
    "userName"            varchar                                           not null,
    "userPassword"        varchar                                           not null,
    "userBio"             varchar,
    "userImage"           varchar,
    "userRealname"        varchar default 'userRealname'::character varying not null,
    elo                   integer default 100                               not null,
    wins                  integer default 0                                 not null,
    losses                integer default 0                                 not null,
    "userToken"           varchar(255)                                      not null,
    "userTokenExpiration" timestamp                                         not null,
    "userCoins"           integer default 20                                not null
);

alter table "User"
    owner to swen1user;

create table "Card"
(
    "cardId"          serial
        constraint "Card_pk"
            primary key,
    "cardMonsterType" "monsterType"         not null,
    "cardOwner"       integer
        constraint "Card_User_userId_fk"
            references "User",
    "cardDamage"      integer,
    "cardElementType" "elementType",
    "cardInTradeDeal" boolean default false not null
);

alter table "Card"
    owner to swen1user;

create table "Deck"
(
    "ownerId" integer not null
        constraint "Deck_pk"
            primary key
        constraint "Deck_User_userId_fk"
            references "User",
    card1     integer
        constraint "Deck_Card_cardId_fk"
            references "Card",
    card2     integer
        constraint "Deck_Card_cardId_fk2"
            references "Card",
    card3     integer
        constraint "Deck_Card_cardId_fk3"
            references "Card",
    card4     integer
        constraint "Deck_Card_cardId_fk4"
            references "Card"
);

alter table "Deck"
    owner to swen1user;

create table messages
(
    id      integer not null
        primary key,
    content varchar(500)
);

alter table messages
    owner to swen1user;

create table "Packages"
(
    "packagesCard1" integer
        constraint "Packages_Card_cardId_fk"
            references "Card",
    "packagesCard2" integer
        constraint "Packages___fk2"
            references "Card",
    "packagesCard3" integer
        constraint "Packages___fk3"
            references "Card",
    "packagesCard4" integer
        constraint "Packages___fk4"
            references "Card",
    "packagesCard5" integer
        constraint "Packages___fk5"
            references "Card",
    "packagesOwner" integer
        constraint "Packages___fkOwner"
            references "User",
    "packagesId"    integer not null
        constraint "Packages_pk"
            primary key
);

alter table "Packages"
    owner to swen1user;


