package ru.itis.dis403.lab11.repository.view;

import ru.itis.dis403.lab11.repository.model.Flight;
import ru.itis.dis403.lab11.repository.repository.AirplaneRepository;
import ru.itis.dis403.lab11.repository.repository.AirportRepository;
import ru.itis.dis403.lab11.repository.repository.FlightRepository;

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {

    private JComboBox<String> airportComboBox;
    private JComboBox<String> typeComboBox;
    private JButton searchButton;
    private JButton refreshButton;
    private final AirportRepository airportRepository;
    private final FlightRepository flightRepository;
    private FlightTablePanel flightTablePanel;

    public MainFrame() {
        super("");
        this.airportRepository = new AirportRepository();
        this.flightRepository = new FlightRepository();

        init();
        loadAirports();
    }

    private void init() {
        setTitle("Табло аэропорта");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();

        setBounds(dimension.width / 2 - 400, dimension.height / 2 - 300, 800, 600);

        JPanel controlPanel = createControlPanel();
        add(controlPanel, BorderLayout.NORTH);


        flightTablePanel = new FlightTablePanel();
        add(flightTablePanel, BorderLayout.CENTER);
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        panel.add(new JLabel("Аэропорт:"));
        airportComboBox = new JComboBox<>();
        panel.add(airportComboBox);

        panel.add(new JLabel("Тип табло:"));
        typeComboBox = new JComboBox<>(new String[]{"Вылет", "Прилёт"});
        panel.add(typeComboBox);

        searchButton = new JButton("Показать");
        searchButton.addActionListener(e -> loadFlights());
        panel.add(searchButton);

        refreshButton = new JButton("Обновить");
        refreshButton.addActionListener(e -> loadFlights());
        panel.add(refreshButton);

        return panel;
    }

    private void loadAirports() {
        List<String> airports = airportRepository.findAll();
        for (String airport : airports) {
            airportComboBox.addItem(airport);
        }
    }

    private void loadFlights() {
        String airportCode = (String) airportComboBox.getSelectedItem();
        String type = typeComboBox.getSelectedIndex() == 0 ? "departure" : "arrival";

        if (airportCode != null) {
            List<Flight> flights = flightRepository.findAll(airportCode, type);
            flightTablePanel.updateTable(flights);
        }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}
