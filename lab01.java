/**
 * @author Сабиров Роман 3312
 * @version 1.0
 */
public class lab01 {
    /**
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {

        int[] array = {13, -7, 227, 14, 0, 18, 39, -1};

        // Выводим исходный массив
        System.out.println("Исходный массив:");
        arrayPrint(array);

        // Сортируем массив
        arraySort(array);

        // Выводим отсортированный массив
        System.out.println("\nОтсортированный массив:");
        arrayPrint(array);

    }

    /**
     *
     * @param array массив целых чисел для вывода
     */
    public static void arrayPrint(int[] array) {
        for (int element : array) {
            System.out.print(element + " ");
        }
    }

    /**
     *
     * @param array массив целых чисел для сортировки
     */
    public static void arraySort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }
}
