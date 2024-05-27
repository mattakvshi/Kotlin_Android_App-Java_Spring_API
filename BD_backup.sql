--
-- PostgreSQL database dump
--

-- Dumped from database version 15.4
-- Dumped by pg_dump version 15.4

-- Started on 2024-05-27 23:22:55

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 214 (class 1259 OID 32870)
-- Name: olympiads; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.olympiads (
    olympiad_id uuid NOT NULL,
    olympiad_name character varying(255)
);


ALTER TABLE public.olympiads OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 32875)
-- Name: pupils; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pupils (
    pupils_id uuid NOT NULL,
    olympiad_id uuid,
    event_date timestamp without time zone,
    pupils_grade integer,
    pupils_mark integer,
    pupils_name character varying(255),
    pupils_phone character varying(255),
    pupils_region character varying(255),
    pupils_scores integer,
    school_subject character varying(255)
);


ALTER TABLE public.pupils OWNER TO postgres;

--
-- TOC entry 3325 (class 0 OID 32870)
-- Dependencies: 214
-- Data for Name: olympiads; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.olympiads (olympiad_id, olympiad_name) VALUES ('c1eebc99-9c0b-4ef8-bb6d-6bb9bd380a24', 'Русский медвежёнок');
INSERT INTO public.olympiads (olympiad_id, olympiad_name) VALUES ('71be4dd3-60a3-45e8-b64d-aed1f870a60a', 'Кенгуру (метематика)');
INSERT INTO public.olympiads (olympiad_id, olympiad_name) VALUES ('d51d7967-eef0-46ac-945f-3067cff3d2d8', 'Кит (информатика)');
INSERT INTO public.olympiads (olympiad_id, olympiad_name) VALUES ('bd77957e-973c-4133-963e-d338c2a42b25', 'Бритиш Бульдог');


--
-- TOC entry 3326 (class 0 OID 32875)
-- Dependencies: 215
-- Data for Name: pupils; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.pupils (pupils_id, olympiad_id, event_date, pupils_grade, pupils_mark, pupils_name, pupils_phone, pupils_region, pupils_scores, school_subject) VALUES ('9cedfcdb-0300-40ab-8d10-4c64370172bd', 'bd77957e-973c-4133-963e-d338c2a42b25', '2024-05-26 13:47:12.224', 0, 3, 'Артюшенко Никита', '+79997854123', 'Краснодар', 54, 'Английский язык');
INSERT INTO public.pupils (pupils_id, olympiad_id, event_date, pupils_grade, pupils_mark, pupils_name, pupils_phone, pupils_region, pupils_scores, school_subject) VALUES ('4ae072f7-999d-4b5e-9369-b970264933ac', '71be4dd3-60a3-45e8-b64d-aed1f870a60a', '2024-05-26 13:47:31.523', 10, 3, 'Иванов Антон', '+79182285566', 'Краснодар', 48, 'Математика');
INSERT INTO public.pupils (pupils_id, olympiad_id, event_date, pupils_grade, pupils_mark, pupils_name, pupils_phone, pupils_region, pupils_scores, school_subject) VALUES ('49f5dfa0-2837-48ba-8588-9e4c1b9f15ab', '71be4dd3-60a3-45e8-b64d-aed1f870a60a', '2024-04-01 03:00:00', 10, 2, 'Александр Емельяненко ', '+79184569933', 'Краснодар', 35, 'Математика');
INSERT INTO public.pupils (pupils_id, olympiad_id, event_date, pupils_grade, pupils_mark, pupils_name, pupils_phone, pupils_region, pupils_scores, school_subject) VALUES ('4bfdc7e0-cdea-4338-9b14-7dacc11dd243', '71be4dd3-60a3-45e8-b64d-aed1f870a60a', '2024-04-01 03:00:00', 10, 3, 'Аватаев Мурад', '+79997779977', 'Грозный ', 300, 'Математика');
INSERT INTO public.pupils (pupils_id, olympiad_id, event_date, pupils_grade, pupils_mark, pupils_name, pupils_phone, pupils_region, pupils_scores, school_subject) VALUES ('3c864e00-4062-44e7-ba86-7c3cd807080f', 'd51d7967-eef0-46ac-945f-3067cff3d2d8', '2024-05-26 14:03:33.028', 10, 3, 'Сидоренко Максим', '+79182842848', 'Краснодар', 100, 'Информатика');
INSERT INTO public.pupils (pupils_id, olympiad_id, event_date, pupils_grade, pupils_mark, pupils_name, pupils_phone, pupils_region, pupils_scores, school_subject) VALUES ('bc77a60d-ebdb-4c3a-bd94-15d1ce8b4164', 'd51d7967-eef0-46ac-945f-3067cff3d2d8', '2024-05-26 14:05:20.509', 10, 3, 'Владарчук Дмитрий', '+79184563289', 'Петропавловск-Камчатский', 100, 'Информатика');
INSERT INTO public.pupils (pupils_id, olympiad_id, event_date, pupils_grade, pupils_mark, pupils_name, pupils_phone, pupils_region, pupils_scores, school_subject) VALUES ('53c9fee5-5715-427e-805b-7d1edb3add85', 'c1eebc99-9c0b-4ef8-bb6d-6bb9bd380a24', '2024-04-09 03:00:00', 10, 3, 'Мифтахов Михаил', '+99997778899', 'Краснодар', 1488, 'Русский язык');
INSERT INTO public.pupils (pupils_id, olympiad_id, event_date, pupils_grade, pupils_mark, pupils_name, pupils_phone, pupils_region, pupils_scores, school_subject) VALUES ('1b6999a7-3abe-4cb3-aead-e7c9b49b42cf', '71be4dd3-60a3-45e8-b64d-aed1f870a60a', '2024-05-27 22:26:16.846', 8, 1, 'Пологий Иван', '+79182489653', 'Москва', 25, 'Математика');
INSERT INTO public.pupils (pupils_id, olympiad_id, event_date, pupils_grade, pupils_mark, pupils_name, pupils_phone, pupils_region, pupils_scores, school_subject) VALUES ('187b6d02-320f-404a-80a2-fd11939e0274', 'bd77957e-973c-4133-963e-d338c2a42b25', '2024-04-10 03:00:00', 8, 2, 'Сафонов Игорь', '+79182842848', 'Казань', 55, 'Английский язык ');


--
-- TOC entry 3178 (class 2606 OID 32874)
-- Name: olympiads olympiads_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.olympiads
    ADD CONSTRAINT olympiads_pkey PRIMARY KEY (olympiad_id);


--
-- TOC entry 3182 (class 2606 OID 32881)
-- Name: pupils pupils_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pupils
    ADD CONSTRAINT pupils_pkey PRIMARY KEY (pupils_id);


--
-- TOC entry 3176 (class 1259 OID 32882)
-- Name: index_olympiads_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX index_olympiads_id ON public.olympiads USING btree (olympiad_id);


--
-- TOC entry 3179 (class 1259 OID 32883)
-- Name: index_pupils_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX index_pupils_id ON public.pupils USING btree (pupils_id);


--
-- TOC entry 3180 (class 1259 OID 32884)
-- Name: index_pupils_olympiads_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX index_pupils_olympiads_id ON public.pupils USING btree (olympiad_id);


-- Completed on 2024-05-27 23:22:56

--
-- PostgreSQL database dump complete
--

