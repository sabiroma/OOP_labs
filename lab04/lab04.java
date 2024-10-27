import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Сабиров Роман 3312
 * @version 1.0
 */
public class lab04 {
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
                {"Иванов Иван Иванович", "5", "B"},
                {"Сидоров Алексей Петрович", "10", "C"},
                {"Соколова Анна Сергеевна", "2", "D"}
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
                try {
                    int selectedRow = driverTable.getSelectedRow();
                    if (selectedRow == -1) {
                        throw new RowNotSelectedException("Пожалуйста, выберите строку для редактирования");
                    }
                    editDriverDialog(selectedRow);
                } catch (RowNotSelectedException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Слушатель для кнопки "Удалить"
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedRow = driverTable.getSelectedRow();
                    if (selectedRow == -1) {
                        throw new RowNotSelectedException("Пожалуйста, выберите строку для удаления");
                    }
                    tableModel.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(frame, "Водитель удален");

                } catch (RowNotSelectedException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Слушатель для кнопки поиск
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String criterion = (String) searchCriteria.getSelectedItem();
                    String value = searchField.getText().trim().toLowerCase();

                    // Проверка на пустое поле поиска
                    if (value.isEmpty()) {
                        throw new EmptySearchFieldException("Поле поиска не должно быть пустым.");
                    }

                    driverTable.clearSelection();
                    boolean found = false;

                    // Поиск значений в таблице по критерию
                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        String cellValue = tableModel.getValueAt(i, searchCriteria.getSelectedIndex()).toString().toLowerCase();
                        if (cellValue.contains(value)) {
                            driverTable.addRowSelectionInterval(i, i);
                            found = true;
                        }
                    }

                    // Сообщение об успехе или отсутствии совпадений
                    if (found) {
                        JOptionPane.showMessageDialog(frame, "Найдены совпадения по критерию: " + criterion);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Совпадений не найдено");
                    }

                } catch (EmptySearchFieldException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    /**
     * Метод для отображения диалогового окна добавления водителя
     */
    private void addDriverDialog() {
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
            try {
                String name = nameField.getText();
                String experience = experienceField.getText();
                String category = categoryField.getText();

                validateFields(name, experience, category); // Проверка полей

                tableModel.addRow(new Object[]{name, experience, category});
                JOptionPane.showMessageDialog(frame, "Водитель добавлен!");

            } catch (InvalidFieldException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Метод для отображения диалогового окна редактирования водителя
     * @param row номер редактируемой строки
     */
    private void editDriverDialog(int row) {
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
            try {
                String name = nameField.getText();
                String experience = experienceField.getText();
                String category = categoryField.getText();

                validateFields(name, experience, category); // Проверка полей

                tableModel.setValueAt(name, row, 0);
                tableModel.setValueAt(experience, row, 1);
                tableModel.setValueAt(category, row, 2);
                JOptionPane.showMessageDialog(frame, "Данные водителя обновлены!");

            } catch (InvalidFieldException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Метод для проверки корректности введенных данных
     * @param name        ФИО водителя
     * @param experience  Стаж
     * @param category    Класс
     * @throws InvalidFieldException если хотя бы одно из полей пустое или стаж работы не является целым числом
     */
    private void validateFields(String name, String experience, String category) throws InvalidFieldException {
        if (name.isEmpty() || experience.isEmpty() || category.isEmpty()) {
            throw new InvalidFieldException("Все поля должны быть заполнены!");
        }
        try {
            Integer.parseInt(experience); // Проверка, что стаж - целое число
        } catch (NumberFormatException ex) {
            throw new InvalidFieldException("Стаж работы должен быть числом!");
        }
    }

    /**
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        new lab04().Bus();
    }
}

/**
 * Исключение, если одно или несколько полей формы содержат недопустимые данные.
 */
class InvalidFieldException extends Exception {
    public InvalidFieldException(String message) {
        super(message);
    }
}

/**
 * Исключение, если строка таблицы не выбрана
 */
class RowNotSelectedException extends Exception {
    public RowNotSelectedException(String message) {
        super(message);
    }
}

/**
 * Исключение, если поле поиска пустое
 */
class EmptySearchFieldException extends Exception {
    public EmptySearchFieldException(String message) {
        super(message);
    }
}