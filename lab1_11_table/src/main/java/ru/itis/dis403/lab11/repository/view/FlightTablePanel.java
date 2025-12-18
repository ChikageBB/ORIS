package ru.itis.dis403.lab11.repository.view;

import ru.itis.dis403.lab11.repository.model.Flight;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class FlightTablePanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private SimpleDateFormat dateFormat;

    public FlightTablePanel() {
        this.dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        String[] columnsNames = {
                "Номер рейса",
                "Время отправления",
                "Время прибытия",
                "Статус",
                "Откуда",
                "Куда"
        };

        tableModel = new DefaultTableModel(columnsNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };



        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);

        setColumnSize();

        JScrollPane jScrollPane = new JScrollPane(table);
        add(jScrollPane, BorderLayout.CENTER);


    }

    public void updateTable(List<Flight> flightList) {
        tableModel.setRowCount(0);

        for (Flight flight: flightList) {
            Object[] row = new Object[6];
            row[0] = flight.getFlightId();
            row[1] = dateFormat.format(flight.getScheduledDeparture());
            row[2] = dateFormat.format(flight.getScheduledArrival());
            row[3] = translateStatus(flight.getStatus());

            row[4] = flight.getDepartureAirport();
            row[5] = flight.getArrivalAirport();

            tableModel.addRow(row);
        }
    }

    private void setColumnSize() {
        TableColumnModel columnModel = table.getColumnModel();

        columnModel.getColumn(0).setPreferredWidth(80);
        columnModel.getColumn(1).setPreferredWidth(120);
        columnModel.getColumn(2).setPreferredWidth(150);
        columnModel.getColumn(3).setPreferredWidth(200);
        columnModel.getColumn(4).setPreferredWidth(80);
        columnModel.getColumn(5).setPreferredWidth(80);

    }

    private String translateStatus(String status) {
        return switch (status) {
            case "Cancelled" -> "Отменен";
            case "Scheduled" -> "По расписанию";
            case "Departed" -> "Вылетел";
            case "Arrived" -> "Прибыл";
            default -> status;
        };
    }
}
