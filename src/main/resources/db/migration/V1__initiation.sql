CREATE TABLE public.cars
(
    id               bigint NOT NULL,
    brand            character varying(255),
    model            character varying(255),
    total_seats      integer,
    year_manufacture integer,
    owner_id         bigint
);


ALTER TABLE public.cars
    OWNER TO najdiprevoz;

--
-- Name: cars_id_seq; Type: SEQUENCE; Schema: public; Owner: najdiprevoz
--

ALTER TABLE public.cars
    ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
        SEQUENCE NAME public .cars_id_seq
            START WITH 11
            INCREMENT BY 1
            NO MINVALUE
            NO MAXVALUE
            CACHE 1
        );


--
-- Name: cities; Type: TABLE; Schema: public; Owner: najdiprevoz
--

CREATE TABLE public.cities
(
    id   bigint NOT NULL,
    name character varying(255)
);


ALTER TABLE public.cities
    OWNER TO najdiprevoz;

--
-- Name: cities_id_seq; Type: SEQUENCE; Schema: public; Owner: najdiprevoz
--

ALTER TABLE public.cities
    ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
        SEQUENCE NAME public .cities_id_seq
            START WITH 36
            INCREMENT BY 1
            NO MINVALUE
            NO MAXVALUE
            CACHE 1
        );


--
-- Name: member_preferences; Type: TABLE; Schema: public; Owner: najdiprevoz
--

CREATE TABLE public.member_preferences
(
    id                 bigint NOT NULL,
    is_pet_allowed     boolean,
    is_smoking_allowed boolean,
    member_id          bigint
);


ALTER TABLE public.member_preferences
    OWNER TO najdiprevoz;

--
-- Name: member_preferences_id_seq; Type: SEQUENCE; Schema: public; Owner: najdiprevoz
--

ALTER TABLE public.member_preferences
    ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
        SEQUENCE NAME public .member_preferences_id_seq
            START WITH 11
            INCREMENT BY 1
            NO MINVALUE
            NO MAXVALUE
            CACHE 1
        );


--
-- Name: members; Type: TABLE; Schema: public; Owner: najdiprevoz
--

CREATE TABLE public.members
(
    id            bigint                      NOT NULL,
    birth_date    timestamp without time zone NOT NULL,
    email         character varying(255)      NOT NULL UNIQUE,
    first_name    character varying(255)      NOT NULL,
    gender        character varying(255)      NOT NULL,
    last_name     character varying(255)      NOT NULL,
    password      character varying(255)      NOT NULL,
    phone_number  character varying(255)      NOT NULL,
    profile_photo character varying(255)
);


ALTER TABLE public.members
    OWNER TO najdiprevoz;

--
-- Name: members_id_seq; Type: SEQUENCE; Schema: public; Owner: najdiprevoz
--

ALTER TABLE public.members
    ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
        SEQUENCE NAME public .members_id_seq
            START WITH 11
            INCREMENT BY 1
            NO MINVALUE
            NO MAXVALUE
            CACHE 1
        );


--
-- Name: ratings; Type: TABLE; Schema: public; Owner: najdiprevoz
--

CREATE TABLE public.ratings
(
    id             bigint  NOT NULL,
    date_submitted timestamp without time zone,
    note           character varying(255),
    rating         integer NOT NULL,
    author_id      bigint  NOT NULL,
    ride_id        bigint  NOT NULL
);


ALTER TABLE public.ratings
    OWNER TO najdiprevoz;

--
-- Name: ratings_id_seq; Type: SEQUENCE; Schema: public; Owner: najdiprevoz
--

ALTER TABLE public.ratings
    ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
        SEQUENCE NAME public .ratings_id_seq
            START WITH 4
            INCREMENT BY 1
            NO MINVALUE
            NO MAXVALUE
            CACHE 1
        );


--
-- Name: ride_requests; Type: TABLE; Schema: public; Owner: najdiprevoz
--

CREATE TABLE public.ride_requests
(
    id           bigint NOT NULL,
    created_on   timestamp without time zone,
    status       character varying(255),
    requester_id bigint,
    ride_id      bigint NOT NULL
);


ALTER TABLE public.ride_requests
    OWNER TO najdiprevoz;

--
-- Name: ride_requests_id_seq; Type: SEQUENCE; Schema: public; Owner: najdiprevoz
--

ALTER TABLE public.ride_requests
    ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
        SEQUENCE NAME public .ride_requests_id_seq
            START WITH 2
            INCREMENT BY 1
            NO MINVALUE
            NO MAXVALUE
            CACHE 1
        );


--
-- Name: rides; Type: TABLE; Schema: public; Owner: najdiprevoz
--

CREATE TABLE public.rides
(
    id                  bigint NOT NULL,
    description         character varying(255),
    created_on          timestamp without time zone,
    departure_time      timestamp without time zone,
    is_finished         boolean,
    price_per_head      integer,
    total_seats_offered integer,
    to_location         bigint,
    driver_id           bigint NOT NULL,
    from_location       bigint
);


ALTER TABLE public.rides
    OWNER TO najdiprevoz;

--
-- Name: rides_id_seq; Type: SEQUENCE; Schema: public; Owner: najdiprevoz
--

ALTER TABLE public.rides
    ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
        SEQUENCE NAME public .rides_id_seq
            START WITH 3
            INCREMENT BY 1
            NO MINVALUE
            NO MAXVALUE
            CACHE 1
        );


--
-- Data for Name: cars; Type: TABLE DATA; Schema: public; Owner: najdiprevoz
--

INSERT INTO public.cars
VALUES (1, 'Toyota', 'RAV4', 5, 2017, 1);
INSERT INTO public.cars
VALUES (2, 'BMW', 'X6', 5, 2009, 2);
INSERT INTO public.cars
VALUES (3, 'Volvo', 'xc90', 5, 2015, 3);
INSERT INTO public.cars
VALUES (4, 'Ford', 'Fiesta', 4, 2017, 4);
INSERT INTO public.cars
VALUES (5, 'Ford', 'Mondeo', 5, 2004, 5);
INSERT INTO public.cars
VALUES (6, 'Volkswagen', 'Golf 4', 5, 2001, 6);
INSERT INTO public.cars
VALUES (7, 'Volkswagen', 'UP', 4, 2018, 7);
INSERT INTO public.cars
VALUES (8, 'Audi', 'Q7', 5, 2017, 8);
INSERT INTO public.cars
VALUES (9, 'Mercedes', 'C350', 5, 2013, 9);
INSERT INTO public.cars
VALUES (10, 'Honda', 'Civic', 5, 2006, 10);


--
-- Data for Name: cities; Type: TABLE DATA; Schema: public; Owner: najdiprevoz
--

INSERT INTO public.cities
VALUES (1, 'Skopje');
INSERT INTO public.cities
VALUES (2, 'Struga');
INSERT INTO public.cities
VALUES (3, 'Strumica');
INSERT INTO public.cities
VALUES (4, 'Ohrid');
INSERT INTO public.cities
VALUES (5, 'Debar');
INSERT INTO public.cities
VALUES (6, 'Gostivar');
INSERT INTO public.cities
VALUES (7, 'Resen');
INSERT INTO public.cities
VALUES (8, 'Prilep');
INSERT INTO public.cities
VALUES (9, 'Bitola');
INSERT INTO public.cities
VALUES (10, 'Tetovo');
INSERT INTO public.cities
VALUES (11, 'Pehcevo');
INSERT INTO public.cities
VALUES (12, 'Kicevo');
INSERT INTO public.cities
VALUES (13, 'Mavrovo');
INSERT INTO public.cities
VALUES (14, 'Kumanovo');
INSERT INTO public.cities
VALUES (15, 'Veles');
INSERT INTO public.cities
VALUES (16, 'Gevgelija');
INSERT INTO public.cities
VALUES (17, 'Negotino');
INSERT INTO public.cities
VALUES (18, 'Kavadarci');
INSERT INTO public.cities
VALUES (19, 'Stip');
INSERT INTO public.cities
VALUES (20, 'Probistip');
INSERT INTO public.cities
VALUES (21, 'Kocani');
INSERT INTO public.cities
VALUES (22, 'Sveti Nikole');
INSERT INTO public.cities
VALUES (23, 'Delcevo');
INSERT INTO public.cities
VALUES (24, 'Makedonski brod');
INSERT INTO public.cities
VALUES (25, 'Makedonska Kamenica');
INSERT INTO public.cities
VALUES (26, 'Vinica');
INSERT INTO public.cities
VALUES (27, 'Valandovo');
INSERT INTO public.cities
VALUES (28, 'Krusevo');
INSERT INTO public.cities
VALUES (29, 'Radovish');
INSERT INTO public.cities
VALUES (30, 'Kriva Palanka');
INSERT INTO public.cities
VALUES (31, 'Berovo');
INSERT INTO public.cities
VALUES (32, 'Demir kapija');
INSERT INTO public.cities
VALUES (33, 'Kratovo');
INSERT INTO public.cities
VALUES (34, 'Demir hisar');
INSERT INTO public.cities
VALUES (35, 'Dojran');


--
-- Data for Name: member_preferences; Type: TABLE DATA; Schema: public; Owner: najdiprevoz
--

INSERT INTO public.member_preferences
VALUES (1, false, false, 1);
INSERT INTO public.member_preferences
VALUES (2, false, true, 2);
INSERT INTO public.member_preferences
VALUES (3, true, false, 3);
INSERT INTO public.member_preferences
VALUES (4, true, true, 4);
INSERT INTO public.member_preferences
VALUES (5, false, true, 5);
INSERT INTO public.member_preferences
VALUES (6, true, true, 6);
INSERT INTO public.member_preferences
VALUES (7, false, false, 7);
INSERT INTO public.member_preferences
VALUES (8, true, false, 8);
INSERT INTO public.member_preferences
VALUES (9, false, true, 9);
INSERT INTO public.member_preferences
VALUES (10, true, true, 10);


--
-- Data for Name: members; Type: TABLE DATA; Schema: public; Owner: najdiprevoz
--

INSERT INTO public.members
VALUES (1, '2020-02-11 22:00:06', 'email@email.com', 'John', 'M', 'Smith', 'password', '071711033', NULL);
INSERT INTO public.members
VALUES (2, '2020-02-11 22:00:06', 'email2@email.com', 'Jane', 'F', 'Doe', 'password', '45282138', NULL);
INSERT INTO public.members
VALUES (3, '2020-02-11 22:00:06', 'email3@email.com', 'Thierry', 'M', 'Henry', 'password', '357429566', NULL);
INSERT INTO public.members
VALUES (4, '2020-02-11 22:00:06', 'email4@email.com', 'Renath', 'M', 'Minerou', 'password', '071711033', NULL);
INSERT INTO public.members
VALUES (5, '2020-02-11 22:00:06', 'email5@email.com', 'Millow', 'M', 'Hobs', 'password', '071711033', NULL);
INSERT INTO public.members
VALUES (6, '2020-02-11 22:00:06', 'email6@email.com', 'Nathan', 'M', 'Bowie', 'password', '071711033', NULL);
INSERT INTO public.members
VALUES (7, '2020-02-11 22:00:06', 'email7@email.com', 'John', 'M', 'Lennon', 'password', '071711033', NULL);
INSERT INTO public.members
VALUES (8, '2020-02-11 22:00:06', 'email8@email.com', 'Jennifer', 'F', 'Lopez', 'password', '071711033', NULL);
INSERT INTO public.members
VALUES (9, '2020-02-11 22:00:06', 'email9@email.com', 'Danielle', 'F', 'Rossi', 'password', '071711033', NULL);
INSERT INTO public.members
VALUES (10, '2020-02-11 22:00:06', 'email10@email.com', 'Adrianna', 'F', 'Lima', 'password', '071711033', NULL);


--
-- Data for Name: ratings; Type: TABLE DATA; Schema: public; Owner: najdiprevoz
--

INSERT INTO public.ratings
VALUES (1, '2020-02-12 21:01:04', 'dobra voznja', 1, 1, 2);
INSERT INTO public.ratings
VALUES (2, '2020-02-12 21:01:15', 'mnogu loso', 2, 1, 1);
INSERT INTO public.ratings
VALUES (3, '2020-02-12 21:04:36', 'Meh,sredno', 3, 2, 2);


--
-- Data for Name: ride_requests; Type: TABLE DATA; Schema: public; Owner: najdiprevoz
--

INSERT INTO public.ride_requests(id, created_on, status, requester_id, ride_id)
VALUES (1, '2020-02-12 19:50:27', 'PENDING', 1, 2);


--
-- Data for Name: rides; Type: TABLE DATA; Schema: public; Owner: najdiprevoz
--

INSERT INTO public.rides
VALUES (1, 'test-description', '2020-02-12 19:50:55', '2020-02-12 19:50:57', true, 100, 5, 1, 1);
INSERT INTO public.rides
VALUES (2, 'test-ride-2', '2020-02-12 21:04:51', '2020-02-12 21:04:55', true, 150, 3, 1, 2, 1);


--
-- -- Name: cars_id_seq; Type: SEQUENCE SET; Schema: public; Owner: najdiprevoz
-- --
--
-- SELECT pg_catalog.setval('public.cars_id_seq', 1, false);
--
--
-- --
-- -- Name: cities_id_seq; Type: SEQUENCE SET; Schema: public; Owner: najdiprevoz
-- --
--
-- SELECT pg_catalog.setval('public.cities_id_seq', 1, false);
--
--
-- --
-- -- Name: member_preferences_id_seq; Type: SEQUENCE SET; Schema: public; Owner: najdiprevoz
-- --
--
-- SELECT pg_catalog.setval('public.member_preferences_id_seq', 1, false);
--
--
-- --
-- -- Name: members_id_seq; Type: SEQUENCE SET; Schema: public; Owner: najdiprevoz
-- --
--
-- SELECT pg_catalog.setval('public.members_id_seq', 1, false);
--
--
-- --
-- -- Name: ratings_id_seq; Type: SEQUENCE SET; Schema: public; Owner: najdiprevoz
-- --
--
-- SELECT pg_catalog.setval('public.ratings_id_seq', 1, false);
--
--
-- --
-- -- Name: ride_requests_id_seq; Type: SEQUENCE SET; Schema: public; Owner: najdiprevoz
-- --
--
-- SELECT pg_catalog.setval('public.ride_requests_id_seq', 1, false);
--
--
-- --
-- -- Name: rides_id_seq; Type: SEQUENCE SET; Schema: public; Owner: najdiprevoz
-- --
--
-- SELECT pg_catalog.setval('public.rides_id_seq', 1, false);


--
-- Name: cars cars_pkey; Type: CONSTRAINT; Schema: public; Owner: najdiprevoz
--

ALTER TABLE ONLY public.cars
    ADD CONSTRAINT cars_pkey PRIMARY KEY (id);


--
-- Name: cities cities_pkey; Type: CONSTRAINT; Schema: public; Owner: najdiprevoz
--

ALTER TABLE ONLY public.cities
    ADD CONSTRAINT cities_pkey PRIMARY KEY (id);



--
-- Name: member_preferences member_preferences_pkey; Type: CONSTRAINT; Schema: public; Owner: najdiprevoz
--

ALTER TABLE ONLY public.member_preferences
    ADD CONSTRAINT member_preferences_pkey PRIMARY KEY (id);


--
-- Name: members members_pkey; Type: CONSTRAINT; Schema: public; Owner: najdiprevoz
--

ALTER TABLE ONLY public.members
    ADD CONSTRAINT members_pkey PRIMARY KEY (id);


--
-- Name: ratings ratings_pkey; Type: CONSTRAINT; Schema: public; Owner: najdiprevoz
--

ALTER TABLE ONLY public.ratings
    ADD CONSTRAINT ratings_pkey PRIMARY KEY (id);


--
-- Name: ride_requests ride_requests_pkey; Type: CONSTRAINT; Schema: public; Owner: najdiprevoz
--

ALTER TABLE ONLY public.ride_requests
    ADD CONSTRAINT ride_requests_pkey PRIMARY KEY (id);


--
-- Name: rides rides_pkey; Type: CONSTRAINT; Schema: public; Owner: najdiprevoz
--

ALTER TABLE ONLY public.rides
    ADD CONSTRAINT rides_pkey PRIMARY KEY (id);


-- Name: ride_requests fk4mlf748wvd1c16p2at7mkux39; Type: FK CONSTRAINT; Schema: public; Owner: najdiprevoz
--

ALTER TABLE ONLY public.ride_requests
    ADD CONSTRAINT fk4mlf748wvd1c16p2at7mkux39 FOREIGN KEY (ride_id) REFERENCES public.rides (id);


--
-- Name: ride_requests fkbhe7ehymbljgen3amgx4yuaq3; Type: FK CONSTRAINT; Schema: public; Owner: najdiprevoz
--

--
-- Name: rides fkgco6pwrgn8co7gwyludy1nnc1; Type: FK CONSTRAINT; Schema: public; Owner: najdiprevoz
--

ALTER TABLE ONLY public.rides
    ADD CONSTRAINT fkgco6pwrgn8co7gwyludy1nnc1 FOREIGN KEY (from_location) REFERENCES public.cities (id);


--
-- Name: cars fkm9dna12woo9bxuhtbdlhek4al; Type: FK CONSTRAINT; Schema: public; Owner: najdiprevoz
--

ALTER TABLE ONLY public.cars
    ADD CONSTRAINT fkm9dna12woo9bxuhtbdlhek4al FOREIGN KEY (owner_id) REFERENCES public.members (id);


--
-- Name: rides fko62n3qexfjtslknusqfjfcs2p; Type: FK CONSTRAINT; Schema: public; Owner: najdiprevoz
--

ALTER TABLE ONLY public.rides
    ADD CONSTRAINT fko62n3qexfjtslknusqfjfcs2p FOREIGN KEY (to_location) REFERENCES public.cities (id);


--
-- Name: ride_requests fkoc5uj7y01drryd0itrmnkk1kf; Type: FK CONSTRAINT; Schema: public; Owner: najdiprevoz
--

ALTER TABLE ONLY public.ride_requests
    ADD CONSTRAINT fkoc5uj7y01drryd0itrmnkk1kf FOREIGN KEY (requester_id) REFERENCES public.members (id);


--
-- Name: ratings fkpn9mvopg00wpfi5xti2wf3p1s; Type: FK CONSTRAINT; Schema: public; Owner: najdiprevoz
--

ALTER TABLE ONLY public.ratings
    ADD CONSTRAINT fkpn9mvopg00wpfi5xti2wf3p1s FOREIGN KEY (ride_id) REFERENCES public.rides (id);


--
-- Name: rides fkq0n68ras6f40ck5lqccd3v75n; Type: FK CONSTRAINT; Schema: public; Owner: najdiprevoz
--

ALTER TABLE ONLY public.rides
    ADD CONSTRAINT fkq0n68ras6f40ck5lqccd3v75n FOREIGN KEY (driver_id) REFERENCES public.members (id);


--
-- Name: member_preferences fkr36s4dryxkuqc91v9bfl7nxc7; Type: FK CONSTRAINT; Schema: public; Owner: najdiprevoz
--

ALTER TABLE ONLY public.member_preferences
    ADD CONSTRAINT fkr36s4dryxkuqc91v9bfl7nxc7 FOREIGN KEY (member_id) REFERENCES public.members (id);


--
-- Name: ratings fks922wdjjs39auw6ac4nd8tgfb; Type: FK CONSTRAINT; Schema: public; Owner: najdiprevoz
--

ALTER TABLE ONLY public.ratings
    ADD CONSTRAINT fks922wdjjs39auw6ac4nd8tgfb FOREIGN KEY (author_id) REFERENCES public.members (id);


--
-- PostgreSQL database dump complete
--

