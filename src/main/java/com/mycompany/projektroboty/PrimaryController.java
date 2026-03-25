package com.mycompany.projektroboty;

import java.io.*;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PrimaryController {

    @FXML private TableView<Robot> tabela;
    @FXML private TableColumn<Robot, String> colProducent, colModel, colTyp;
    @FXML private TableColumn<Robot, Integer> colArea, colIlosc;
    @FXML private TableColumn<Robot, Double> colCena;
    @FXML private TextField tfProducent, tfModel, tfArea, tfCena, tfIlosc;
    @FXML private TextField tfSzukaj; // Pole wyszukiwarki
    @FXML private ComboBox<String> cbTyp;
    @FXML private Label lblSuma;

    private ObservableList<Robot> listaRobotow = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // 1. Mapowanie kolumn tabeli
        colProducent.setCellValueFactory(data -> data.getValue().producentProperty());
        colModel.setCellValueFactory(data -> data.getValue().modelProperty());
        colTyp.setCellValueFactory(data -> data.getValue().typProperty());
        colArea.setCellValueFactory(data -> data.getValue().areaProperty().asObject());
        colCena.setCellValueFactory(data -> data.getValue().cenaProperty().asObject());
        colIlosc.setCellValueFactory(data -> data.getValue().iloscProperty().asObject());

        // 2. Konfiguracja ComboBox
        cbTyp.setItems(FXCollections.observableArrayList("Koszący", "Czyszczący", "Basenowy"));
        cbTyp.setValue("Koszący");

        // 3. Mechanizm Persistence: Odczyt danych z pliku
        wczytajZPliku();

        // 4. Seeding: Dodanie danych domyślnych ze slajdów, jeśli magazyn jest pusty
        if (listaRobotow.isEmpty()) {
            listaRobotow.add(new Robot("Husqvarna", "Automower 305", "Koszący", 600, 5599.0, 2));
            listaRobotow.add(new Robot("Worx", "Landroid M500", "Koszący", 500, 2999.0, 5));
            zapiszDoPliku();
        }

        // 5. Bezpieczna inicjalizacja Filtrowania (Szukaj)
        // Zapobiega NullPointerException, gdy fx:id="tfSzukaj" brakuje w FXML
        if (tfSzukaj != null) {
            FilteredList<Robot> filteredData = new FilteredList<>(listaRobotow, p -> true);
            tfSzukaj.textProperty().addListener((obs, oldVal, newVal) -> {
                filteredData.setPredicate(robot -> {
                    if (newVal == null || newVal.isEmpty()) return true;
                    String filter = newVal.toLowerCase();
                    return robot.getProducent().toLowerCase().contains(filter) || 
                           robot.getModel().toLowerCase().contains(filter);
                });
            });
            tabela.setItems(filteredData);
        } else {
            tabela.setItems(listaRobotow);
        }

        przeliczWartosc();
    }

    @FXML
    private void dodajRobota() {
        try {
            listaRobotow.add(new Robot(
                tfProducent.getText(), tfModel.getText(), cbTyp.getValue(),
                Integer.parseInt(tfArea.getText()), Double.parseDouble(tfCena.getText()),
                Integer.parseInt(tfIlosc.getText())
            ));
            zapiszDoPliku(); // Automatyczny zapis po zmianie stanu
            przeliczWartosc();
            wyczyscPola();
        } catch (Exception e) {
            System.err.println("Błąd wprowadzania danych!");
        }
    }

    @FXML
    private void usunRobota() {
        Robot wybrany = tabela.getSelectionModel().getSelectedItem();
        if (wybrany != null) {
            listaRobotow.remove(wybrany);
            zapiszDoPliku();
            przeliczWartosc();
        }
    }

    private void przeliczWartosc() {
        // Wykorzystanie Java Stream API do obliczeń finansowych
        double suma = listaRobotow.stream()
                .mapToDouble(r -> r.getCena() * r.getIlosc())
                .sum();
        lblSuma.setText(String.format("%.2f PLN", suma));
    }

    private void zapiszDoPliku() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("magazyn.txt"))) {
            for (Robot r : listaRobotow) {
                writer.println(r.getProducent() + ";" + r.getModel() + ";" + r.getTyp() + ";" + 
                               r.getArea() + ";" + r.getCena() + ";" + r.getIlosc());
            }
        } catch (IOException e) {
            System.err.println("Problem z zapisem do pliku!");
        }
    }

    private void wczytajZPliku() {
        File file = new File("magazyn.txt");
        if (!file.exists()) return;
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String[] d = sc.nextLine().split(";");
                if (d.length == 6) {
                    listaRobotow.add(new Robot(d[0], d[1], d[2], 
                        Integer.parseInt(d[3]), Double.parseDouble(d[4]), Integer.parseInt(d[5])));
                }
            }
        } catch (Exception e) {
            System.err.println("Błąd odczytu bazy danych!");
        }
    }

    private void wyczyscPola() {
        tfProducent.clear(); tfModel.clear(); tfArea.clear(); tfCena.clear(); tfIlosc.clear();
    }
}