package com.mycompany.projektroboty;

import jakarta.persistence.*;

@Entity
@Table(name = "roboty") // Tak będzie się nazywać tabela w bazie PostgreSQL
public class Robot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Baza danych potrzebuje unikalnego ID (Klucz główny)

    private String producent;
    private String model;
    private String typ;
    private int area;
    private double cena;
    private int ilosc;

    // Pusty konstruktor wymagany przez Hibernate
    public Robot() {
    }

    // Konstruktor do wygodnego dodawania nowych robotów
    public Robot(String producent, String model, String typ, int area, double cena, int ilosc) {
        this.producent = producent;
        this.model = model;
        this.typ = typ;
        this.area = area;
        this.cena = cena;
        this.ilosc = ilosc;
    }

    // Standardowe Gettery i Settery
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getProducent() { return producent; }
    public void setProducent(String producent) { this.producent = producent; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getTyp() { return typ; }
    public void setTyp(String typ) { this.typ = typ; }
    public int getArea() { return area; }
    public void setArea(int area) { this.area = area; }
    public double getCena() { return cena; }
    public void setCena(double cena) { this.cena = cena; }
    public int getIlosc() { return ilosc; }
    public void setIlosc(int ilosc) { this.ilosc = ilosc; }
}