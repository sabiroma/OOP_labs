import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * @author Сабиров Роман 3312
 * @version 1.0
 */

public class lab02 {
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

        // Делаем окно видимым
        frame.setVisible(true);
    }

    /**
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        new lab02().Bus();
    }
}