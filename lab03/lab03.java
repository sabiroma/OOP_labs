import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Сабиров Роман 3312
 * @version 1.0
 */
public class lab03 {
    // Объявление графических компонентов
    private JFrame frame;
    private JTable driverTable;
    private DefaultTableModel tableModel;
    private JPanel topPanel, filterPanel;
    private JButton addButton, editButton, deleteButton, searchButton;
    private JComboBox<String> searchCriteria;
    private JTextField searchField;
    private JScrollPane scrollPane;

    public void Bus() {
        // Создание главного окна
        frame = new JFrame("Transport system");
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Центрирование окна
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);

        // Создание панели с кнопками
        topPanel = new JPanel(new FlowLayout());
        addButton = new JButton("Добавить");
        editButton = new JButton("Редактировать");
        deleteButton = new JButton("Удалить");

        topPanel.add(addButton);
        topPanel.add(editButton);
        topPanel.add(deleteButton);
        frame.add(topPanel, BorderLayout.NORTH);

        // Создание таблицы с данными
        String[] columns = {"ФИО водителя", "Стаж работы", "Класс"};
        Object[][] data = {
                {"Иванов Иван Иванович", "5 лет", "B"},
                {"Сидоров Алексей Петрович", "10 лет", "C"},
                {"Соколова Анна Сергеевна", "2 года", "D"}
        };
        tableModel = new DefaultTableModel(data, columns);
        driverTable = new JTable(tableModel);
        scrollPane = new JScrollPane(driverTable);

        frame.add(scrollPane, BorderLayout.CENTER);

        // Компоненты поиска
        searchCriteria = new JComboBox<>(new String[]{"ФИО водителя", "Стаж работы", "Класс"});
        searchField = new JTextField(20);
        searchButton = new JButton("Поиск");

        filterPanel = new JPanel();
        filterPanel.add(new JLabel("Критерий поиска: "));
        filterPanel.add(searchCriteria);
        filterPanel.add(new JLabel("Значение: "));
        filterPanel.add(searchField);
        filterPanel.add(searchButton);
        frame.add(filterPanel, BorderLayout.SOUTH);

        // Добавление слушателей к кнопкам
        addListeners();

        // Делаем окно видимым
        frame.setVisible(true);
    }

    /**
     * Метод для добавления слушателей к кнопкам
     */
    private void addListeners() {
        // Слушатель для кнопки "Добавить"
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addDriverDialog();
            }
        });

        // Слушатель для кнопки "Редактировать"
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = driverTable.getSelectedRow();
                if (selectedRow != -1) {
                    editDriverDialog(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(frame, "Пожалуйста, выберите строку для редактирования");
                }
            }
        });

        // Слушатель для кнопки "Удалить"
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = driverTable.getSelectedRow();
                if (selectedRow != -1) {
                    tableModel.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(frame, "Водитель удален");
                } else {
                    JOptionPane.showMessageDialog(frame, "Пожалуйста, выберите строку для удаления");
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String criterion = (String) searchCriteria.getSelectedItem();
                String value = searchField.getText().trim().toLowerCase();

                driverTable.clearSelection();

                boolean found = false;
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    String cellValue = tableModel.getValueAt(i, searchCriteria.getSelectedIndex()).toString().toLowerCase();

                    if (cellValue.contains(value)) {
                        driverTable.addRowSelectionInterval(i, i);
                        found = true;
                    }
                }

                if (found) {
                    JOptionPane.showMessageDialog(frame, "Найдены совпадения по критерию: " + criterion);
                } else {
                    JOptionPane.showMessageDialog(frame, "Совпадений не найдено");
                }
            }
        });
    }

    /**
     * Метод для отображения диалогового окна добавления водителя
     */
    private void addDriverDialog() {
        // Создание панелей для ввода данных
        JPanel panel = new JPanel(new GridLayout(3, 2));

        JTextField nameField = new JTextField(20);
        JTextField experienceField = new JTextField(20);
        JTextField categoryField = new JTextField(20);

        panel.add(new JLabel("ФИО: "));
        panel.add(nameField);
        panel.add(new JLabel("Стаж работы: "));
        panel.add(experienceField);
        panel.add(new JLabel("Класс: "));
        panel.add(categoryField);

        int result = JOptionPane.showConfirmDialog(frame, panel, "Добавить водителя", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String experience = experienceField.getText();
            String category = categoryField.getText();

            if (!name.isEmpty() && !experience.isEmpty() && !category.isEmpty()) {
                tableModel.addRow(new Object[]{name, experience, category});
                JOptionPane.showMessageDialog(frame, "Водитель добавлен!");
            } else {
                JOptionPane.showMessageDialog(frame, "Все поля должны быть заполнены!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Метод для отображения диалогового окна редактирования водителя
     * @param row номер редактируемой строки
     */
    private void editDriverDialog(int row) {
        // Создание панелей для ввода данных с уже заполненными значениями
        JPanel panel = new JPanel(new GridLayout(3, 2));

        JTextField nameField = new JTextField((String) tableModel.getValueAt(row, 0), 20);
        JTextField experienceField = new JTextField((String) tableModel.getValueAt(row, 1), 20);
        JTextField categoryField = new JTextField((String) tableModel.getValueAt(row, 2), 20);

        panel.add(new JLabel("ФИО: "));
        panel.add(nameField);
        panel.add(new JLabel("Стаж работы: "));
        panel.add(experienceField);
        panel.add(new JLabel("Класс: "));
        panel.add(categoryField);

        int result = JOptionPane.showConfirmDialog(frame, panel, "Редактировать водителя", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String experience = experienceField.getText();
            String category = categoryField.getText();

            if (!name.isEmpty() && !experience.isEmpty() && !category.isEmpty()) {
                // Обновляем данные в выбранной строке
                tableModel.setValueAt(name, row, 0);
                tableModel.setValueAt(experience, row, 1);
                tableModel.setValueAt(category, row, 2);
                JOptionPane.showMessageDialog(frame, "Данные водителя обновлены!");
            } else {
                JOptionPane.showMessageDialog(frame, "Все поля должны быть заполнены!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        new lab03().Bus();
    }
}
