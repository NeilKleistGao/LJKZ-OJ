create table if not exists users(
                                    email varchar(64) primary key,
                                    username varchar(64) not null,
                                    password varchar(64) not null,
                                    acNum int,
                                    waNum int,
                                    tleNum int,
                                    mleNum int,
                                    ceNum int,
                                    reNum int,
                                    isAdmin bool
);

create table if not exists problems(
                                       pid varchar(32) primary key,
                                       title varchar(128) not null,
                                       labels varchar(128) not null,
                                       problemDesc varchar(1024) not null,
                                       totalSubmit int not null,
                                       acSubmit int not null,
                                       exampleInput varchar(128) not null,
                                       exampleOutput varchar(128) not null,
                                       competitionOnly bool not null ,
                                       memoryLimit int not null ,
                                       timeLimit int not null,
                                       dataSize int not null
);

create table if not exists submissions(
                                          email varchar(64) not null,
                                          pid varchar(32) not null,
                                          submitTime DATETIME not null,
                                          state varchar(4) not null,
                                          normalSubmit bool not null,
                                          timeUsed int not null,
                                          memoryUsed int not null,
                                          info varchar(128),
                                          primary key (email, pid, submitTime),
                                          foreign key ukey(email) references users(email) on update restrict on delete restrict,
                                          foreign key pkey(pid) references problems(pid) on update restrict on delete restrict
);

create table if not exists acrec(
                                    email varchar(64) not null,
                                    pid varchar(32) not null,
                                    primary key (email, pid),
                                    foreign key ukey(email) references users(email) on update restrict on delete restrict,
                                    foreign key pkey(pid) references problems(pid) on update restrict on delete restrict
);

create table if not exists competitions(
                                           cid varchar(32) primary key,
                                           beginTime datetime not null,
                                           endTime datetime not null,
                                           title varchar(64) not null,
                                           creator varchar(64) not null,
                                           foreign key ukey(creator) references users(email) on update restrict on delete restrict
);

create table if not exists problem_used(
                                           cid varchar(32) not null,
                                           pid varchar(32) not null,
                                           primary key(cid, pid),
                                           foreign key pkey(pid) references problems(pid) on update restrict on delete restrict,
                                           foreign key ckey(cid) references competitions(cid) on update restrict on delete restrict
);

create table if not exists rank(
                                   cid varchar(32) not null ,
                                   email varchar(32) not null ,
                                   ac int not null,
                                   penalty int not null,
                                   rank int not null,
                                   percent float not null,
                                   primary key (cid, email),
                                   foreign key ckey(cid) references competitions(cid) on update restrict on delete restrict,
                                   foreign key ukey(email) references users(email) on update restrict on delete restrict
);
