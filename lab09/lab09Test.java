package org.example;

import org.junit.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class lab09Test {

    private lab09 lab09Instance;

    @BeforeClass
    public static void allTestsStarted() {
        System.out.println("Начало тестирования");
    }

    @AfterClass
    public static void allTestsFinished() {
        System.out.println("Конец тестирования");
    }

    @Before
    public void startTest() {
        System.out.println("Запуск теста");
        lab09Instance = new lab09();
        String[] columns = {"ФИО водителя", "Стаж работы", "Класс"};
        lab09Instance.tableModel = new DefaultTableModel(columns, 0);
        lab09Instance.driverTable = new JTable(lab09Instance.tableModel);
    }

    @After
    public void finishTest() {
        System.out.println("Завершение теста");
    }

    @Test
    public void testAddDriver() {
        System.out.println("Тестирование добавления водителя");

        lab09Instance.addDriver("Петров Петр Петрович", "7 лет", "C");
        lab09Instance.addDriver("Петров Петр Петрович", "7 лет", null);

        Assert.assertEquals(1, lab09Instance.tableModel.getRowCount());
        Assert.assertEquals("Петров Петр Петрович", lab09Instance.tableModel.getValueAt(0, 0));
        Assert.assertEquals("7 лет", lab09Instance.tableModel.getValueAt(0, 1));
        Assert.assertEquals("C", lab09Instance.tableModel.getValueAt(0, 2));
    }

    @Test
    public void testEditDriver() {
        System.out.println("Тестирование редактирования водителя");

        lab09Instance.addDriver("Петров Петр Петрович", "7 лет", "C");

        lab09Instance.editDriver(0, "Иванов Иван Иванович", "6 лет", "B");

        Assert.assertEquals("Иванов Иван Иванович", lab09Instance.tableModel.getValueAt(0, 0));
        Assert.assertEquals("6 лет", lab09Instance.tableModel.getValueAt(0, 1));
        Assert.assertEquals("B", lab09Instance.tableModel.getValueAt(0, 2));
    }

    @Test
    public void testDeleteDriver() {
        System.out.println("Тестирование удаления водителя");

        lab09Instance.addDriver("Петров Петр Петрович", "7 лет", "C");
        lab09Instance.addDriver("Иванов Иван Иванович", "6 лет", "B");

        lab09Instance.tableModel.removeRow(1);

        Assert.assertEquals(1, lab09Instance.tableModel.getRowCount());
        Assert.assertEquals("Петров Петр Петрович", lab09Instance.tableModel.getValueAt(0, 0));
    }

}
