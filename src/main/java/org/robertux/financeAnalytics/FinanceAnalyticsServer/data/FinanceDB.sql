--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5beta1
-- Dumped by pg_dump version 10.4

-- Started on 2018-07-28 23:26:50 CST

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12623)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2395 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 183 (class 1259 OID 57401)
-- Name: account; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.account (
    number bigint NOT NULL,
    alias character varying(50),
    type integer NOT NULL,
    user_id bigint NOT NULL
);


ALTER TABLE public.account OWNER TO postgres;

--
-- TOC entry 185 (class 1259 OID 57409)
-- Name: transaction; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.transaction (
    id bigint NOT NULL,
    amount money NOT NULL,
    date timestamp with time zone NOT NULL,
    account_number bigint NOT NULL,
    reference character varying(50),
    description character varying(255),
    category_name character varying(20)
);


ALTER TABLE public.transaction OWNER TO postgres;

--
-- TOC entry 184 (class 1259 OID 57407)
-- Name: transaction_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.transaction_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.transaction_id_seq OWNER TO postgres;

--
-- TOC entry 2396 (class 0 OID 0)
-- Dependencies: 184
-- Name: transaction_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.transaction_id_seq OWNED BY public.transaction.id;


--
-- TOC entry 182 (class 1259 OID 57390)
-- Name: user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."user" (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    status character varying(1) NOT NULL
);


ALTER TABLE public."user" OWNER TO postgres;

--
-- TOC entry 181 (class 1259 OID 57388)
-- Name: user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_id_seq OWNER TO postgres;

--
-- TOC entry 2397 (class 0 OID 0)
-- Dependencies: 181
-- Name: user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.user_id_seq OWNED BY public."user".id;


--
-- TOC entry 2259 (class 2604 OID 65695)
-- Name: transaction id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transaction ALTER COLUMN id SET DEFAULT nextval('public.transaction_id_seq'::regclass);


--
-- TOC entry 2258 (class 2604 OID 65715)
-- Name: user id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user" ALTER COLUMN id SET DEFAULT nextval('public.user_id_seq'::regclass);


--
-- TOC entry 2384 (class 0 OID 57401)
-- Dependencies: 183
-- Data for Name: account; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.account (number, alias, type, user_id) FROM stdin;
\.


--
-- TOC entry 2386 (class 0 OID 57409)
-- Dependencies: 185
-- Data for Name: transaction; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.transaction (id, amount, date, account_number, reference, description, category_name) FROM stdin;
\.


--
-- TOC entry 2383 (class 0 OID 57390)
-- Dependencies: 182
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."user" (id, name, password, status) FROM stdin;
4	user	prueba	A
3	robertux	prueba123	A
2	admin	Prueba123!	A
\.


--
-- TOC entry 2398 (class 0 OID 0)
-- Dependencies: 184
-- Name: transaction_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.transaction_id_seq', 1, false);


--
-- TOC entry 2399 (class 0 OID 0)
-- Dependencies: 181
-- Name: user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_id_seq', 4, true);


--
-- TOC entry 2263 (class 2606 OID 65647)
-- Name: account account_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.account
    ADD CONSTRAINT account_pkey PRIMARY KEY (number);


--
-- TOC entry 2261 (class 2606 OID 65717)
-- Name: user customer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT customer_pkey PRIMARY KEY (id);


--
-- TOC entry 2265 (class 2606 OID 65697)
-- Name: transaction transaction_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transaction
    ADD CONSTRAINT transaction_pkey PRIMARY KEY (id);


--
-- TOC entry 2266 (class 2606 OID 65735)
-- Name: account fk_account_user_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.account
    ADD CONSTRAINT fk_account_user_id FOREIGN KEY (user_id) REFERENCES public."user"(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2267 (class 2606 OID 65648)
-- Name: transaction fk_transaction_account_number; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transaction
    ADD CONSTRAINT fk_transaction_account_number FOREIGN KEY (account_number) REFERENCES public.account(number) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2394 (class 0 OID 0)
-- Dependencies: 6
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2018-07-28 23:26:50 CST

--
-- PostgreSQL database dump complete
--

