CREATE TABLE public.authorities
(
    id        int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    authority varchar(30) UNIQUE                               NOT NULL
);

alter table public.authorities
    owner to najdiprevoz;

CREATE TABLE public.cities
(
    id   int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    name character varying(255)
);

ALTER TABLE public.cities
    OWNER TO najdiprevoz;

CREATE TABLE public.users
(
    id            int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    birth_date    timestamp without time zone                      NOT NULL,
    username      character varying(255)                           NOT NULL UNIQUE,
    first_name    character varying(255)                           NOT NULL,
    gender        character varying(255)                           NOT NULL,
    last_name     character varying(255)                           NOT NULL,
    password      character varying(255)                           NOT NULL,
    phone_number  character varying(255)                           NOT NULL,
    authority_id  bigint                                           NOT NULL REFERENCES public.authorities (id),
    profile_photo character varying(255)
);


ALTER TABLE public.users
    OWNER TO najdiprevoz;

CREATE TABLE public.member_preferences
(
    id                 int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    is_pet_allowed     boolean,
    is_smoking_allowed boolean,
    member_id          bigint unique references public.users (id)
);


ALTER TABLE public.member_preferences
    OWNER TO najdiprevoz;


CREATE TABLE public.cars
(
    id               int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    brand            character varying(255),
    model            character varying(255),
    total_seats      integer,
    year_manufacture integer,
    owner_id         bigint                                           not null references public.users (id)
);

ALTER TABLE public.cars
    OWNER TO najdiprevoz;


CREATE TABLE public.rides
(
    id                  int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    description         character varying(255),
    created_on          timestamp without time zone,
    departure_time      timestamp without time zone,
    price_per_head      integer,
    total_seats_offered integer,
    to_location         bigint REFERENCES public.cities (id),
    driver_id           bigint                                           NOT NULL REFERENCES public.users (id),
    from_location       bigint REFERENCES public.cities (id),
    status              varchar(255)
);


ALTER TABLE public.rides
    OWNER TO najdiprevoz;

CREATE TABLE public.ride_requests
(
    id           int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    created_on   timestamp without time zone,
    status       character varying(255),
    requester_id bigint,
    ride_id      bigint                                           NOT NULL references public.rides (id)
);

ALTER TABLE public.ride_requests
    OWNER TO najdiprevoz;

CREATE TABLE public.ratings
(
    id              int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    date_submitted  timestamp without time zone,
    note            character varying(255),
    rating          integer                                          NOT NULL,
    ride_request_id bigint                                           NOT NULL references public.ride_requests (id)
);


ALTER TABLE public.ratings
    OWNER TO najdiprevoz;


create table notifications
(
    id              int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    created_on      timestamp,
    from_id         bigint                                           not null REFERENCES public.users (id),
    to_id           bigint                                           not null REFERENCES public.users (id),
    seen            boolean                                          not null,
    type            varchar(255),
    ride_request_id bigint                                           not null REFERENCES public.ride_requests (id)
);

alter table notifications
    owner to najdiprevoz;

-- auto-generated definition
create table notification_actions
(
    notification_id bigint not null references notifications (id),
    actions         varchar(255)
);

alter table notification_actions
    owner to najdiprevoz;