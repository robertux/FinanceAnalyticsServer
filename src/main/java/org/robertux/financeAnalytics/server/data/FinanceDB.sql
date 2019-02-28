--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5beta1
-- Dumped by pg_dump version 10.4

-- Started on 2018-08-11 21:50:20 CST

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
-- TOC entry 2397 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 183 (class 1259 OID 57401)
-- Name: accounts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.accounts (
    number bigint NOT NULL,
    alias character varying(50),
    type character varying(5) NOT NULL DEFAULT 'SAV'::character varying,
    balance numeric(10,2) NOT NULL DEFAULT 0.0,
    user_id bigint NOT NULL
);


ALTER TABLE public.accounts OWNER TO postgres;

--
-- TOC entry 185 (class 1259 OID 57409)
-- Name: transactions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.transactions (
    id character varying(100) NOT NULL DEFAULT 'TRNID'::character varying,
    amount numeric(10,2) NOT NULL,
    account_number bigint NOT NULL,
    reference character varying(50),
    description character varying(255),
    category_name character varying(20) DEFAULT 'GENERAL'::character varying,
    date_time timestamp with time zone DEFAULT now() NOT NULL,
    currency character varying(5) NOT NULL DEFAULT 'USD'::character varying
    status character varying(2) NOT NULL DEFAULT 'A'::character varying
);


ALTER TABLE public.transactions OWNER TO postgres;

--
-- TOC entry 182 (class 1259 OID 57390)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    status character varying(1) NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 181 (class 1259 OID 57388)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO postgres;

--
-- TOC entry 2399 (class 0 OID 0)
-- Dependencies: 181
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;

--
-- TOC entry 2258 (class 2604 OID 65715)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);

--
-- TOC entry 2401 (class 0 OID 0)
-- Dependencies: 181
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 12, true);


--
-- TOC entry 2265 (class 2606 OID 65647)
-- Name: accounts account_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accounts
    ADD CONSTRAINT account_pkey PRIMARY KEY (number);


--
-- TOC entry 2263 (class 2606 OID 65717)
-- Name: users customer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT customer_pkey PRIMARY KEY (id);


--
-- TOC entry 2267 (class 2606 OID 65697)
-- Name: transactions transaction_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transactions
    ADD CONSTRAINT transaction_pkey PRIMARY KEY (id);


--
-- TOC entry 2268 (class 2606 OID 65735)
-- Name: accounts fk_account_user_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accounts
    ADD CONSTRAINT fk_account_user_id FOREIGN KEY (user_id) REFERENCES public.users(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2269 (class 2606 OID 65648)
-- Name: transactions fk_transaction_account_number; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transactions
    ADD CONSTRAINT fk_transaction_account_number FOREIGN KEY (account_number) REFERENCES public.accounts(number) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2396 (class 0 OID 0)
-- Dependencies: 6
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2018-08-11 21:50:21 CST

--
-- PostgreSQL database dump complete
--

