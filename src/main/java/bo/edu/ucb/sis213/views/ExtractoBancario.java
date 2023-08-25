package bo.edu.ucb.sis213.views;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import bo.edu.ucb.sis213.bl.UserBl;
import bo.edu.ucb.sis213.dto.UsuarioDto;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

public class ExtractoBancario extends JFrame {
    UsuarioDto user;
    public ExtractoBancario(UsuarioDto user) {
        this.user = user;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Extracto Bancario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 600));
        setResizable(false); // Hace que el JFrame no sea reajustable
        setLocationRelativeTo(null); // Centra el JFrame en la pantalla

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));

        createTitlePanel(mainPanel);
        createTable(mainPanel);
        createButtonPanel(mainPanel);

        add(mainPanel);
        pack();
        setVisible(true);
    }

    private void createTitlePanel(JPanel mainPanel) {
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(30, 144, 255));
        titlePanel.setPreferredSize(new Dimension(getWidth(), 80));

        JLabel titleLabel = new JLabel("Extracto Bancario");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
    }

    private void createTable(JPanel mainPanel) {
        String[] columnNames = {"ID", "Tipo de Operación", "Cantidad", "Fecha"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel) {
            @Override
            public boolean getScrollableTracksViewportWidth() {
                return getPreferredSize().width < getParent().getWidth();
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Deshabilita la edición de celdas
            }
        };
        table.setFont(new Font("Arial", Font.PLAIN, 24));
        table.setRowHeight(50);
        table.getTableHeader().setReorderingAllowed(false);


        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            table.getColumnModel().getColumn(i).setHeaderValue("<html><b><font size=+2>" + columnNames[i] + "</font></b></html>");
            table.getColumnModel().getColumn(i).setResizable(false); // Hace que las columnas no sean ajustables
        }

        Vector<Vector<Object>> data = new Vector<>();
        UserBl userBl = new UserBl();
        try {
            userBl.consultarHistorico(data, user.getAlias());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al consultar el historial", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            e.printStackTrace();
        }
        for (Vector<Object> row : data) {
            tableModel.addRow(row);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void createButtonPanel(JPanel mainPanel) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(200, 200, 200));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton acceptButton = new JButton("Aceptar");
        acceptButton.setFont(new Font("Arial", Font.BOLD, 20));
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra el JFrame
            }
        });
        buttonPanel.add(acceptButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(ExtractoBancario::new);
    // }
}
