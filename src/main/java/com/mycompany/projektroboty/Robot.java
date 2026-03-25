package com.mycompany.projektroboty;

import javafx.beans.property.*;

public class Robot {
    private final SimpleStringProperty producent;
    private final SimpleStringProperty model;
    private final SimpleStringProperty typ;
    private final SimpleIntegerProperty area;
    private final SimpleDoubleProperty cena;
    private final SimpleIntegerProperty ilosc;

    public Robot(String producent, String model, String typ, int area, double cena, int ilosc) {
        this.producent = new SimpleStringProperty(producent);
        this.model = new SimpleStringProperty(model);
        this.typ = new SimpleStringProperty(typ);
        this.area = new SimpleIntegerProperty(area);
        this.cena = new SimpleDoubleProperty(cena);
        this.ilosc = new SimpleIntegerProperty(ilosc);
    }

    // Gettery Property dla TableView
    public StringProperty producentProperty() { return producent; }
    public StringProperty modelProperty() { return model; }
    public StringProperty typProperty() { return typ; }
    public IntegerProperty areaProperty() { return area; }
    public DoubleProperty cenaProperty() { return cena; }
    public IntegerProperty iloscProperty() { return ilosc; }

    // Zwykłe gettery do zapisu w formacie CSV
    public String getProducent() { return producent.get(); }
    public String getModel() { return model.get(); }
    public String getTyp() { return typ.get(); }
    public int getArea() { return area.get(); }
    public double getCena() { return cena.get(); }
    public int getIlosc() { return ilosc.get(); }
}