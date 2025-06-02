--
-- PostgreSQL database dump
--

-- Dumped from database version 17.4
-- Dumped by pg_dump version 17.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: update_station_transfer_status(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.update_station_transfer_status() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    -- Проверяем, что это соединение с пересадкой
    IF NEW.is_transfer THEN
        -- Обновляем is_transfer для первой станции
        UPDATE stations 
        SET is_transfer = true
        WHERE id = NEW.station1_id;
        
        -- Обновляем is_transfer для второй станции
        UPDATE stations 
        SET is_transfer = true
        WHERE id = NEW.station2_id;
    END IF;
    
    RETURN NEW;
END;
$$;


ALTER FUNCTION public.update_station_transfer_status() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: connections; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.connections (
    id integer NOT NULL,
    station1_id integer NOT NULL,
    station2_id integer NOT NULL,
    is_transfer boolean DEFAULT false NOT NULL,
    transfer_time integer,
    CONSTRAINT different_stations CHECK ((station1_id <> station2_id))
);


ALTER TABLE public.connections OWNER TO postgres;

--
-- Name: connections_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.connections_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.connections_id_seq OWNER TO postgres;

--
-- Name: connections_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.connections_id_seq OWNED BY public.connections.id;


--
-- Name: lines; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.lines (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    color character varying(20) NOT NULL
);


ALTER TABLE public.lines OWNER TO postgres;

--
-- Name: lines_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.lines_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.lines_id_seq OWNER TO postgres;

--
-- Name: lines_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.lines_id_seq OWNED BY public.lines.id;


--
-- Name: stations; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.stations (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    line_id integer NOT NULL,
    map_x integer NOT NULL,
    map_y integer NOT NULL,
    name_x integer NOT NULL,
    name_y integer NOT NULL,
    is_transfer boolean DEFAULT false
);


ALTER TABLE public.stations OWNER TO postgres;

--
-- Name: stations_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.stations_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.stations_id_seq OWNER TO postgres;

--
-- Name: stations_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.stations_id_seq OWNED BY public.stations.id;


--
-- Name: connections id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.connections ALTER COLUMN id SET DEFAULT nextval('public.connections_id_seq'::regclass);


--
-- Name: lines id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lines ALTER COLUMN id SET DEFAULT nextval('public.lines_id_seq'::regclass);


--
-- Name: stations id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stations ALTER COLUMN id SET DEFAULT nextval('public.stations_id_seq'::regclass);


--
-- Data for Name: connections; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.connections (id, station1_id, station2_id, is_transfer, transfer_time) FROM stdin;
1	1	2	f	3
2	2	3	f	3
3	3	4	f	3
4	4	5	f	3
5	5	6	f	3
6	6	7	f	3
7	7	8	f	3
8	8	9	f	3
9	9	10	f	3
10	10	11	f	3
11	11	12	f	3
12	12	13	f	3
13	13	14	f	3
14	14	15	f	3
15	15	16	f	3
16	16	17	f	3
17	17	18	f	3
18	18	19	f	3
19	19	20	f	3
20	20	21	f	3
21	21	22	f	3
22	23	24	f	3
23	24	25	f	3
24	25	26	f	3
25	26	27	f	3
26	27	28	f	3
27	28	29	f	3
28	29	30	f	3
29	30	31	f	3
30	31	32	f	3
31	32	33	f	3
32	33	34	f	3
33	34	35	f	3
34	35	36	f	3
35	36	37	f	3
36	37	38	f	3
37	38	39	f	3
38	39	40	f	3
39	40	41	f	3
40	41	42	f	3
41	42	43	f	3
42	43	44	f	3
43	44	45	f	3
44	46	47	f	3
45	47	48	f	3
46	48	49	f	3
47	49	50	f	3
48	50	51	f	3
49	51	52	f	3
50	52	53	f	3
51	53	54	f	3
52	54	55	f	3
53	55	56	f	3
54	56	57	f	3
55	57	58	f	3
56	58	59	f	3
57	59	60	f	3
58	60	61	f	3
59	62	63	f	3
60	63	64	f	3
61	64	65	f	3
62	65	66	f	3
63	66	67	f	3
64	67	68	f	3
65	68	69	f	3
66	69	70	f	3
67	70	71	f	3
68	71	72	f	3
69	73	74	f	3
70	74	75	f	3
71	75	76	f	3
72	76	77	f	3
73	77	78	f	3
74	78	79	f	3
75	79	80	f	3
76	80	81	f	3
77	81	82	f	3
78	82	83	f	3
79	83	84	f	3
80	85	86	f	3
81	86	87	f	3
82	87	88	f	3
83	88	89	f	3
84	89	90	f	3
85	90	91	f	3
86	91	92	f	3
87	92	93	f	3
88	93	94	f	3
89	94	95	f	3
90	95	96	f	3
91	96	97	f	3
92	97	98	f	3
93	98	99	f	3
94	99	100	f	3
95	100	101	f	3
96	101	102	f	3
97	102	103	f	3
98	103	104	f	3
99	104	105	f	3
100	105	106	f	3
101	106	107	f	3
102	107	108	f	3
103	109	110	f	3
104	110	111	f	3
105	111	112	f	3
106	112	113	f	3
107	113	114	f	3
108	114	115	f	3
109	115	116	f	3
110	116	117	f	3
111	117	118	f	3
112	118	119	f	3
113	119	120	f	3
114	120	121	f	3
115	121	122	f	3
116	122	123	f	3
117	123	124	f	3
118	124	125	f	3
119	125	126	f	3
120	126	127	f	3
121	127	128	f	3
122	128	129	f	3
123	129	130	f	3
124	130	131	f	3
125	84	73	f	3
126	6	79	t	3
127	8	94	t	4
128	9	120	t	4
129	92	80	t	4
130	53	78	t	4
131	31	82	t	4
132	118	83	t	4
133	57	84	t	4
134	69	84	t	4
135	57	69	t	4
136	13	73	t	4
137	74	97	t	4
138	36	76	t	4
139	77	122	t	4
140	95	121	t	4
141	10	54	t	4
142	10	34	t	4
143	34	54	t	4
144	55	11	t	4
145	55	72	t	4
146	11	72	t	4
147	60	62	t	4
148	119	33	t	4
\.


--
-- Data for Name: lines; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.lines (id, name, color) FROM stdin;
1	Сокольническая	#D6083B
2	Замоскворецкая	#4CB848
3	Арбатско-Покровская	#0078BF
4	Филёвская	#00B9E4
5	Кольцевая	#915F2C
6	Калужско-Рижская	#F58220
7	Таганско-Краснопресненская	#8E479C
\.


--
-- Data for Name: stations; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.stations (id, name, line_id, map_x, map_y, name_x, name_y, is_transfer) FROM stdin;
85	Медведково	6	850	-50	870	-30	f
86	Бабушкинская	6	850	0	870	20	f
87	Свиблово	6	850	50	870	70	f
88	Ботанический сад	6	850	100	870	120	f
89	ВДНХ	6	850	150	870	170	f
90	Алексеевская	6	850	200	870	220	f
91	Рижская	6	850	250	870	270	f
96	Третьяковская	6	825	750	845	770	f
46	Щёлковская	3	1410	120	1430	140	f
47	Первомайская	3	1360	170	1380	190	f
35	Новокузнецкая	2	900	700	920	720	f
32	Маяковская	2	580	525	600	545	f
23	Ховрино	2	140	50	160	70	f
59	Славянский бульвар	3	260	780	280	800	f
56	Смоленская	3	615	750	575	770	f
58	Парк Победы	3	415	850	435	870	f
7	Красные Ворота	1	900	420	920	440	f
1	Бульвар Рокоссовского	1	1200	90	1220	110	f
2	Черкизовская	1	1150	140	1170	160	f
3	Преображенская площадь	1	1100	190	1120	210	f
4	Сокольники	1	1050	240	1070	260	f
24	Беломорская	2	190	100	210	120	f
25	Речной вокзал	2	240	150	260	170	f
26	Водный стадион	2	290	200	310	220	f
27	Войковская	2	340	250	360	270	f
99	Ленинский проспект	6	700	1000	720	1020	f
100	Академическая	6	650	1050	670	1070	f
101	Профсоюзная	6	600	1100	620	1120	f
102	Новые Черёмушки	6	550	1150	570	1170	f
6	Комсомольская	1	1010	360	1030	380	t
79	Комсомольская	5	1010	360	1030	380	t
8	Чистые пруды	1	850	510	870	530	t
9	Лубянка	1	730	600	750	620	t
92	Проспект Мира	6	850	300	870	320	t
80	Проспект Мира	5	850	300	870	320	t
53	Курская	3	1060	470	1080	490	t
78	Курская	5	1060	470	1080	490	t
31	Белорусская	2	540	450	560	470	t
82	Белорусская	5	540	450	560	470	t
13	Парк культуры	1	650	860	670	880	t
73	Парк культуры	5	650	860	670	880	t
74	Октябрьская	5	800	900	820	920	t
97	Октябрьская	6	800	900	820	920	t
36	Павелецкая	2	1060	750	1080	770	t
76	Павелецкая	5	1060	750	1080	770	t
10	Охотный Ряд	1	745	675	765	695	t
70	Смоленская	4	590	720	530	700	f
77	Таганская	5	1100	600	1135	620	t
54	Площадь Революции	3	745	675	745	655	t
118	Баррикадная	7	500	600	535	620	t
122	Таганская	7	1100	600	1135	620	t
121	Китай-город	7	850	600	885	620	t
95	Китай-город	6	850	600	885	620	t
120	Кузнецкий Мост	7	730	600	735	590	t
34	Театральная	2	745	675	665	685	t
72	Александровский сад	4	690	750	710	730	t
83	Краснопресненская	5	500	600	490	580	t
55	Арбатская	3	690	750	630	740	t
68	Студенческая	4	490	750	450	730	f
67	Кутузовская	4	440	750	400	770	f
94	Тургеневская	6	850	510	750	490	t
93	Сухаревская	6	850	395	870	375	f
84	Киевская	5	540	750	500	770	t
57	Киевская	3	540	750	500	770	t
69	Киевская	4	540	750	500	770	t
11	Библиотека им. Ленина	1	690	750	670	770	t
19	Юго-Западная	1	350	1150	370	1170	f
28	Сокол	2	390	300	410	320	f
29	Аэропорт	2	440	350	460	370	f
30	Динамо	2	490	400	510	420	f
48	Измайловская	3	1310	220	1330	240	f
5	Красносельская	1	1000	290	1020	310	f
37	Автозаводская	2	1110	800	1130	820	f
38	Коломенская	2	1160	850	1180	870	f
39	Каширская	2	1210	900	1230	920	f
40	Кантемировская	2	1260	950	1280	970	f
98	Шаболовская	6	750	950	770	970	f
49	Партизанская	3	1260	270	1280	290	f
50	Семёновская	3	1210	320	1230	340	f
51	Электрозаводская	3	1160	370	1180	390	f
20	Тропарёво	1	300	1200	320	1220	f
14	Фрунзенская	1	600	900	620	920	f
103	Калужская	6	500	1200	520	1220	f
104	Беляево	6	450	1250	470	1270	f
105	Коньково	6	400	1300	420	1320	f
106	Тёплый Стан	6	350	1350	370	1370	f
107	Ясенево	6	300	1400	320	1420	f
108	Новоясеневская	6	250	1450	270	1470	f
33	Тверская	2	630	600	620	575	t
63	Пионерская	4	240	660	220	640	f
64	Филёвский парк	4	290	690	270	670	f
65	Багратионовская	4	340	720	320	700	f
66	Фили	4	390	750	370	730	f
62	Кунцевская	4	190	630	170	610	t
109	Планерная	7	0	150	35	170	f
110	Сходненская	7	50	200	85	220	f
111	Тушинская	7	100	250	135	270	f
112	Спартак	7	150	300	185	320	f
113	Щукинская	7	200	350	235	370	f
114	Октябрьское Поле	7	250	400	285	420	f
119	Пушкинская	7	630	600	665	620	t
61	Пятницкое шоссе	3	140	580	170	580	f
60	Кунцевская	3	190	630	170	610	t
117	Улица 1905 года	7	400	550	425	565	f
71	Арбатская	4	640	720	620	700	f
52	Бауманская	3	1110	420	1130	440	f
12	Кропоткинская	1	660	810	680	830	f
15	Спортивная	1	550	950	570	970	f
16	Воробьёвы горы	1	500	1000	520	1020	f
75	Добрынинская	5	950	860	970	880	f
17	Университет	1	450	1050	470	1070	f
81	Новослободская	5	650	340	670	360	f
18	Проспект Вернадского	1	400	1100	420	1120	f
41	Царицыно	2	1310	1000	1330	1020	f
42	Орехово	2	1360	1050	1380	1070	f
43	Домодедовская	2	1410	1100	1430	1120	f
44	Красногвардейская	2	1460	1150	1480	1170	f
45	Алма-Атинская	2	1510	1200	1530	1220	f
21	Румянцево	1	250	1250	270	1270	f
22	Саларьево	1	200	1300	220	1320	f
123	Пролетарская	7	1150	650	1185	670	f
124	Волгоградский проспект	7	1200	700	1235	720	f
125	Текстильщики	7	1250	750	1285	770	f
126	Кузьминки	7	1300	800	1335	820	f
127	Рязанский проспект	7	1350	850	1385	870	f
128	Выхино	7	1400	900	1435	920	f
129	Лермонтовский проспект	7	1450	950	1485	970	f
130	Жулебино	7	1500	1000	1535	1020	f
131	Котельники	7	1550	1050	1585	1070	f
116	Беговая	7	350	500	385	520	f
115	Полежаевская	7	300	450	335	470	f
\.


--
-- Name: connections_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.connections_id_seq', 148, true);


--
-- Name: lines_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.lines_id_seq', 1, false);


--
-- Name: stations_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.stations_id_seq', 248, true);


--
-- Name: connections connections_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.connections
    ADD CONSTRAINT connections_pkey PRIMARY KEY (id);


--
-- Name: lines lines_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lines
    ADD CONSTRAINT lines_pkey PRIMARY KEY (id);


--
-- Name: stations stations_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stations
    ADD CONSTRAINT stations_pkey PRIMARY KEY (id);


--
-- Name: idx_stations_line; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_stations_line ON public.stations USING btree (line_id);


--
-- Name: idx_stations_name; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_stations_name ON public.stations USING btree (name);


--
-- Name: connections connections_after_insert; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER connections_after_insert AFTER INSERT ON public.connections FOR EACH ROW EXECUTE FUNCTION public.update_station_transfer_status();


--
-- Name: connections connections_station1_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.connections
    ADD CONSTRAINT connections_station1_id_fkey FOREIGN KEY (station1_id) REFERENCES public.stations(id);


--
-- Name: connections connections_station2_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.connections
    ADD CONSTRAINT connections_station2_id_fkey FOREIGN KEY (station2_id) REFERENCES public.stations(id);


--
-- Name: stations stations_line_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stations
    ADD CONSTRAINT stations_line_id_fkey FOREIGN KEY (line_id) REFERENCES public.lines(id);


--
-- PostgreSQL database dump complete
--

