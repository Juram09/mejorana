package logic;

import database.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.*;

public class main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ui/principalCar.fxml"));
        primaryStage.setTitle("La Mejorana");
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
    }

    public static void main(String[] args) throws SQLException{
        createDatabase();
        launch(args);
    }

    private static void createDatabase() throws SQLException {
        Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", "postgres", "admin");
        String sql="SELECT 'CREATE DATABASE mejorana' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'mejorana')";
        PreparedStatement statement = c.prepareStatement(sql);
        ResultSet data=statement.executeQuery();
        if(data.next()){
            sql="CREATE DATABASE mejorana WITH OWNER = postgres ENCODING = 'UTF8' CONNECTION LIMIT = -1;";
            PreparedStatement statement1 = c.prepareStatement(sql);
            statement1.execute();
            statement1.close();
            data.close();
            statement.close();
            createTables();
        }
    }

    private static void createTables() throws SQLException {
        Connection connection = new DBConnection().getConnection();
        String sql="--\n" +
                "-- PostgreSQL database dump\n" +
                "--\n" +
                "\n" +
                "-- Dumped from database version 13.3\n" +
                "-- Dumped by pg_dump version 13.3\n" +
                "\n" +
                "-- Started on 2021-11-05 22:20:20\n" +
                "\n" +
                "SET statement_timeout = 0;\n" +
                "SET lock_timeout = 0;\n" +
                "SET idle_in_transaction_session_timeout = 0;\n" +
                "SET client_encoding = 'UTF8';\n" +
                "SET standard_conforming_strings = on;\n" +
                "SELECT pg_catalog.set_config('search_path', '', false);\n" +
                "SET check_function_bodies = false;\n" +
                "SET xmloption = content;\n" +
                "SET client_min_messages = warning;\n" +
                "SET row_security = off;\n" +
                "\n" +
                "SET default_tablespace = '';\n" +
                "\n" +
                "SET default_table_access_method = heap;\n" +
                "\n" +
                "--\n" +
                "-- TOC entry 201 (class 1259 OID 24631)\n" +
                "-- Name: car; Type: TABLE; Schema: public; Owner: postgres\n" +
                "--\n" +
                "\n" +
                "CREATE TABLE public.car (\n" +
                "    placa text NOT NULL,\n" +
                "    km bigint NOT NULL,\n" +
                "    color text NOT NULL,\n" +
                "    marca text NOT NULL,\n" +
                "    modelo integer NOT NULL,\n" +
                "    chasis text NOT NULL,\n" +
                "    capacidad integer NOT NULL,\n" +
                "    tipo text NOT NULL,\n" +
                "    conductor text\n" +
                ");\n" +
                "\n" +
                "\n" +
                "ALTER TABLE public.car OWNER TO postgres;\n" +
                "\n" +
                "--\n" +
                "-- TOC entry 200 (class 1259 OID 24617)\n" +
                "-- Name: conductor; Type: TABLE; Schema: public; Owner: postgres\n" +
                "--\n" +
                "\n" +
                "CREATE TABLE public.conductor (\n" +
                "    nit text NOT NULL,\n" +
                "    documento text NOT NULL,\n" +
                "    nombre text NOT NULL,\n" +
                "    apellido text NOT NULL,\n" +
                "    telefono bigint NOT NULL,\n" +
                "    licencia bigint NOT NULL,\n" +
                "    imagen text\n" +
                ");\n" +
                "\n" +
                "\n" +
                "ALTER TABLE public.conductor OWNER TO postgres;\n" +
                "\n" +
                "--\n" +
                "-- TOC entry 203 (class 1259 OID 32984)\n" +
                "-- Name: documento; Type: TABLE; Schema: public; Owner: postgres\n" +
                "--\n" +
                "\n" +
                "CREATE TABLE public.documento (\n" +
                "    id text NOT NULL,\n" +
                "    nombre text NOT NULL,\n" +
                "    placa text NOT NULL,\n" +
                "    fecha_in date NOT NULL,\n" +
                "    imagen text,\n" +
                "    descripcion text,\n" +
                "    fecha_out date,\n" +
                "    active boolean\n" +
                ");\n" +
                "\n" +
                "\n" +
                "ALTER TABLE public.documento OWNER TO postgres;\n" +
                "\n" +
                "--\n" +
                "-- TOC entry 202 (class 1259 OID 24658)\n" +
                "-- Name: imagenes; Type: TABLE; Schema: public; Owner: postgres\n" +
                "--\n" +
                "\n" +
                "CREATE TABLE public.imagenes (\n" +
                "    placa text NOT NULL,\n" +
                "    frontal text,\n" +
                "    trasera text,\n" +
                "    izquierda text,\n" +
                "    derecha text,\n" +
                "    cabina text\n" +
                ");\n" +
                "\n" +
                "\n" +
                "ALTER TABLE public.imagenes OWNER TO postgres;\n" +
                "\n" +
                "--\n" +
                "-- TOC entry 209 (class 1259 OID 33081)\n" +
                "-- Name: mantenimiento; Type: TABLE; Schema: public; Owner: postgres\n" +
                "--\n" +
                "\n" +
                "CREATE TABLE public.mantenimiento (\n" +
                "    id bigint NOT NULL,\n" +
                "    placa text NOT NULL,\n" +
                "    km bigint NOT NULL,\n" +
                "    fecha date NOT NULL,\n" +
                "    tipo text NOT NULL,\n" +
                "    imagen text,\n" +
                "    descripcion text,\n" +
                "    active boolean,\n" +
                "    prox bigint\n" +
                ");\n" +
                "\n" +
                "\n" +
                "ALTER TABLE public.mantenimiento OWNER TO postgres;\n" +
                "\n" +
                "--\n" +
                "-- TOC entry 208 (class 1259 OID 33079)\n" +
                "-- Name: mantenimiento_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres\n" +
                "--\n" +
                "\n" +
                "CREATE SEQUENCE public.mantenimiento_id_seq\n" +
                "    START WITH 1\n" +
                "    INCREMENT BY 1\n" +
                "    NO MINVALUE\n" +
                "    NO MAXVALUE\n" +
                "    CACHE 1;\n" +
                "\n" +
                "\n" +
                "ALTER TABLE public.mantenimiento_id_seq OWNER TO postgres;\n" +
                "\n" +
                "--\n" +
                "-- TOC entry 3042 (class 0 OID 0)\n" +
                "-- Dependencies: 208\n" +
                "-- Name: mantenimiento_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres\n" +
                "--\n" +
                "\n" +
                "ALTER SEQUENCE public.mantenimiento_id_seq OWNED BY public.mantenimiento.id;\n" +
                "\n" +
                "\n" +
                "--\n" +
                "-- TOC entry 205 (class 1259 OID 33035)\n" +
                "-- Name: observacion; Type: TABLE; Schema: public; Owner: postgres\n" +
                "--\n" +
                "\n" +
                "CREATE TABLE public.observacion (\n" +
                "    id bigint NOT NULL,\n" +
                "    placa text NOT NULL,\n" +
                "    observacion text NOT NULL,\n" +
                "    fecha date NOT NULL\n" +
                ");\n" +
                "\n" +
                "\n" +
                "ALTER TABLE public.observacion OWNER TO postgres;\n" +
                "\n" +
                "--\n" +
                "-- TOC entry 204 (class 1259 OID 33033)\n" +
                "-- Name: observacion_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres\n" +
                "--\n" +
                "\n" +
                "CREATE SEQUENCE public.observacion_id_seq\n" +
                "    START WITH 1\n" +
                "    INCREMENT BY 1\n" +
                "    NO MINVALUE\n" +
                "    NO MAXVALUE\n" +
                "    CACHE 1;\n" +
                "\n" +
                "\n" +
                "ALTER TABLE public.observacion_id_seq OWNER TO postgres;\n" +
                "\n" +
                "--\n" +
                "-- TOC entry 3043 (class 0 OID 0)\n" +
                "-- Dependencies: 204\n" +
                "-- Name: observacion_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres\n" +
                "--\n" +
                "\n" +
                "ALTER SEQUENCE public.observacion_id_seq OWNED BY public.observacion.id;\n" +
                "\n" +
                "\n" +
                "--\n" +
                "-- TOC entry 207 (class 1259 OID 33063)\n" +
                "-- Name: tanqueo; Type: TABLE; Schema: public; Owner: postgres\n" +
                "--\n" +
                "\n" +
                "CREATE TABLE public.tanqueo (\n" +
                "    id bigint NOT NULL,\n" +
                "    placa text NOT NULL,\n" +
                "    fecha date NOT NULL,\n" +
                "    km bigint NOT NULL,\n" +
                "    galones bigint NOT NULL,\n" +
                "    kmpgalon bigint\n" +
                ");\n" +
                "\n" +
                "\n" +
                "ALTER TABLE public.tanqueo OWNER TO postgres;\n" +
                "\n" +
                "--\n" +
                "-- TOC entry 206 (class 1259 OID 33061)\n" +
                "-- Name: tanqueo_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres\n" +
                "--\n" +
                "\n" +
                "CREATE SEQUENCE public.tanqueo_id_seq\n" +
                "    START WITH 1\n" +
                "    INCREMENT BY 1\n" +
                "    NO MINVALUE\n" +
                "    NO MAXVALUE\n" +
                "    CACHE 1;\n" +
                "\n" +
                "\n" +
                "ALTER TABLE public.tanqueo_id_seq OWNER TO postgres;\n" +
                "\n" +
                "--\n" +
                "-- TOC entry 3044 (class 0 OID 0)\n" +
                "-- Dependencies: 206\n" +
                "-- Name: tanqueo_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres\n" +
                "--\n" +
                "\n" +
                "ALTER SEQUENCE public.tanqueo_id_seq OWNED BY public.tanqueo.id;\n" +
                "\n" +
                "\n" +
                "--\n" +
                "-- TOC entry 2887 (class 2604 OID 33084)\n" +
                "-- Name: mantenimiento id; Type: DEFAULT; Schema: public; Owner: postgres\n" +
                "--\n" +
                "\n" +
                "ALTER TABLE ONLY public.mantenimiento ALTER COLUMN id SET DEFAULT nextval('public.mantenimiento_id_seq'::regclass);\n" +
                "\n" +
                "\n" +
                "--\n" +
                "-- TOC entry 2885 (class 2604 OID 33038)\n" +
                "-- Name: observacion id; Type: DEFAULT; Schema: public; Owner: postgres\n" +
                "--\n" +
                "\n" +
                "ALTER TABLE ONLY public.observacion ALTER COLUMN id SET DEFAULT nextval('public.observacion_id_seq'::regclass);\n" +
                "\n" +
                "\n" +
                "--\n" +
                "-- TOC entry 2886 (class 2604 OID 33066)\n" +
                "-- Name: tanqueo id; Type: DEFAULT; Schema: public; Owner: postgres\n" +
                "--\n" +
                "\n" +
                "ALTER TABLE ONLY public.tanqueo ALTER COLUMN id SET DEFAULT nextval('public.tanqueo_id_seq'::regclass);\n" +
                "\n" +
                "\n" +
                "--\n" +
                "-- TOC entry 2891 (class 2606 OID 24638)\n" +
                "-- Name: car car_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres\n" +
                "--\n" +
                "\n" +
                "ALTER TABLE ONLY public.car\n" +
                "    ADD CONSTRAINT car_pkey PRIMARY KEY (placa);\n" +
                "\n" +
                "\n" +
                "--\n" +
                "-- TOC entry 2889 (class 2606 OID 24624)\n" +
                "-- Name: conductor conductor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres\n" +
                "--\n" +
                "\n" +
                "ALTER TABLE ONLY public.conductor\n" +
                "    ADD CONSTRAINT conductor_pkey PRIMARY KEY (nit);\n" +
                "\n" +
                "\n" +
                "--\n" +
                "-- TOC entry 2895 (class 2606 OID 32991)\n" +
                "-- Name: documento documento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres\n" +
                "--\n" +
                "\n" +
                "ALTER TABLE ONLY public.documento\n" +
                "    ADD CONSTRAINT documento_pkey PRIMARY KEY (id, nombre);\n" +
                "\n" +
                "\n" +
                "--\n" +
                "-- TOC entry 2893 (class 2606 OID 24665)\n" +
                "-- Name: imagenes imagenes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres\n" +
                "--\n" +
                "\n" +
                "ALTER TABLE ONLY public.imagenes\n" +
                "    ADD CONSTRAINT imagenes_pkey PRIMARY KEY (placa);\n" +
                "\n" +
                "\n" +
                "--\n" +
                "-- TOC entry 2901 (class 2606 OID 33089)\n" +
                "-- Name: mantenimiento mantenimiento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres\n" +
                "--\n" +
                "\n" +
                "ALTER TABLE ONLY public.mantenimiento\n" +
                "    ADD CONSTRAINT mantenimiento_pkey PRIMARY KEY (id);\n" +
                "\n" +
                "\n" +
                "--\n" +
                "-- TOC entry 2897 (class 2606 OID 33043)\n" +
                "-- Name: observacion observacion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres\n" +
                "--\n" +
                "\n" +
                "ALTER TABLE ONLY public.observacion\n" +
                "    ADD CONSTRAINT observacion_pkey PRIMARY KEY (id);\n" +
                "\n" +
                "\n" +
                "--\n" +
                "-- TOC entry 2899 (class 2606 OID 33071)\n" +
                "-- Name: tanqueo tanqueo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres\n" +
                "--\n" +
                "\n" +
                "ALTER TABLE ONLY public.tanqueo\n" +
                "    ADD CONSTRAINT tanqueo_pkey PRIMARY KEY (id);\n" +
                "\n" +
                "\n" +
                "--\n" +
                "-- TOC entry 2902 (class 2606 OID 24639)\n" +
                "-- Name: car car_conductor_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres\n" +
                "--\n" +
                "\n" +
                "ALTER TABLE ONLY public.car\n" +
                "    ADD CONSTRAINT car_conductor_fkey FOREIGN KEY (conductor) REFERENCES public.conductor(nit);\n" +
                "\n" +
                "\n" +
                "--\n" +
                "-- TOC entry 2904 (class 2606 OID 32992)\n" +
                "-- Name: documento fk_car_doc; Type: FK CONSTRAINT; Schema: public; Owner: postgres\n" +
                "--\n" +
                "\n" +
                "ALTER TABLE ONLY public.documento\n" +
                "    ADD CONSTRAINT fk_car_doc FOREIGN KEY (placa) REFERENCES public.car(placa);\n" +
                "\n" +
                "\n" +
                "--\n" +
                "-- TOC entry 2903 (class 2606 OID 33049)\n" +
                "-- Name: imagenes img_car; Type: FK CONSTRAINT; Schema: public; Owner: postgres\n" +
                "--\n" +
                "\n" +
                "ALTER TABLE ONLY public.imagenes\n" +
                "    ADD CONSTRAINT img_car FOREIGN KEY (placa) REFERENCES public.car(placa) ON DELETE CASCADE NOT VALID;\n" +
                "\n" +
                "\n" +
                "--\n" +
                "-- TOC entry 2905 (class 2606 OID 33044)\n" +
                "-- Name: observacion obs_car; Type: FK CONSTRAINT; Schema: public; Owner: postgres\n" +
                "--\n" +
                "\n" +
                "ALTER TABLE ONLY public.observacion\n" +
                "    ADD CONSTRAINT obs_car FOREIGN KEY (placa) REFERENCES public.car(placa) ON DELETE CASCADE;\n" +
                "\n" +
                "\n" +
                "--\n" +
                "-- TOC entry 2906 (class 2606 OID 33072)\n" +
                "-- Name: tanqueo tanq_car; Type: FK CONSTRAINT; Schema: public; Owner: postgres\n" +
                "--\n" +
                "\n" +
                "ALTER TABLE ONLY public.tanqueo\n" +
                "    ADD CONSTRAINT tanq_car FOREIGN KEY (placa) REFERENCES public.car(placa) ON DELETE CASCADE;\n" +
                "\n" +
                "\n" +
                "-- Completed on 2021-11-05 22:20:20\n" +
                "\n" +
                "--\n" +
                "-- PostgreSQL database dump complete\n" +
                "--\n" +
                "\n";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.execute();
        statement.close();
    }
}
