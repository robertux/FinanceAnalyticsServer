--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5beta1
-- Dumped by pg_dump version 11.1

-- Started on 2019-03-03 23:42:58 CST

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

CREATE TABLE public.categories (
    name character varying(20) NOT NULL
);

ALTER TABLE public.categories OWNER TO "financeUser";

--
-- TOC entry 181 (class 1259 OID 90282)
-- Name: accounts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.accounts (
    number bigint NOT NULL,
    alias character varying(50) NOT NULL,
    type character varying(5) DEFAULT 'SAV'::character varying NOT NULL,
    balance numeric(10,2) DEFAULT 0.0 NOT NULL,
    currency character varying(3) DEFAULT 'USD'::character varying NOT NULL,
    user_id bigint NOT NULL,
    status character varying(1) NOT NULL
);


ALTER TABLE public.accounts OWNER TO "financeUser";

--
-- TOC entry 185 (class 1259 OID 90320)
-- Name: sessions; Type: TABLE; Schema: public; Owner: financeUser
--

CREATE TABLE public.sessions (
    id character varying(250) NOT NULL,
    user_id bigint NOT NULL,
    created_at timestamp without time zone NOT NULL,
    status character varying(1) NOT NULL
);


ALTER TABLE public.sessions OWNER TO "financeUser";

--
-- TOC entry 182 (class 1259 OID 90287)
-- Name: transactions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.transactions (
    id character varying(100) DEFAULT 'TRNID'::character varying NOT NULL,
    amount numeric(10,2) NOT NULL,
    account_number bigint NOT NULL,
    reference character varying(20),
    title character varying(50) NOT NULL,
    description character varying(255),
    category_name character varying(20) DEFAULT 'GENERAL'::character varying,
    date_time timestamp with time zone DEFAULT now() NOT NULL,
    currency character varying(3) DEFAULT 'USD'::character varying NOT NULL,
    status character varying(2) DEFAULT 'A'::character varying NOT NULL
);


ALTER TABLE public.transactions OWNER TO "financeUser";

--
-- TOC entry 183 (class 1259 OID 90295)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    status character varying(1) NOT NULL
);


ALTER TABLE public.users OWNER TO "financeUser";

--
-- TOC entry 184 (class 1259 OID 90301)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO "financeUser";

--
-- TOC entry 2404 (class 0 OID 0)
-- Dependencies: 184
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- TOC entry 2267 (class 2604 OID 90303)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- TOC entry 2405 (class 0 OID 0)
-- Dependencies: 184
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 1, true);



ALTER TABLE ONLY public.categories
    ADD CONSTRAINT category_pkey PRIMARY KEY (name);

--
-- TOC entry 2269 (class 2606 OID 90305)
-- Name: accounts account_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accounts
    ADD CONSTRAINT account_pkey PRIMARY KEY (number);


--
-- TOC entry 2273 (class 2606 OID 90307)
-- Name: users customer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT customer_pkey PRIMARY KEY (id);


--
-- TOC entry 2275 (class 2606 OID 90324)
-- Name: sessions sessions_pkey; Type: CONSTRAINT; Schema: public; Owner: financeUser
--

ALTER TABLE ONLY public.sessions
    ADD CONSTRAINT sessions_pkey PRIMARY KEY (id);


--
-- TOC entry 2271 (class 2606 OID 90309)
-- Name: transactions transaction_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transactions
    ADD CONSTRAINT transaction_pkey PRIMARY KEY (id);


--
-- TOC entry 2276 (class 2606 OID 90310)
-- Name: accounts fk_account_user_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accounts
    ADD CONSTRAINT fk_account_user_id FOREIGN KEY (user_id) REFERENCES public.users(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2278 (class 2606 OID 90325)
-- Name: sessions fk_session_user_id; Type: FK CONSTRAINT; Schema: public; Owner: financeUser
--

ALTER TABLE ONLY public.sessions
    ADD CONSTRAINT fk_session_user_id FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 2277 (class 2606 OID 90315)
-- Name: transactions fk_transaction_account_number; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transactions
    ADD CONSTRAINT fk_transaction_account_number FOREIGN KEY (account_number) REFERENCES public.accounts(number) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2403 (class 0 OID 0)
-- Dependencies: 7
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2019-03-03 23:42:59 CST

--
-- PostgreSQL database dump complete
--

