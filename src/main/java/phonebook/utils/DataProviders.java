package phonebook.utils;

import org.testng.annotations.DataProvider;
import phonebook.data.UserData;
import phonebook.model.Contact;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataProviders {
    @DataProvider
    public static Object[][] loginDataProvider() {
        return new Object[][]{
                {UserData.EMAIL, UserData.PASSWORD},
                {"portishead2026@gmail.com", "Password@1"}
        };
    }

    @DataProvider
    public Object[][] contactDataProvider() {
        return new Object[][]{
                {"name1", "lastName1", "1234567890", "admin1@gmail.com", "Germany, Berlin1", "Some Description1"},
                {"name2", "lastName2", "1234567891", "admin2@gmail.com", "Germany, Berlin2", "Some Description2"},
                {"name3", "lastName3", "1234567892", "admin3@gmail.com", "Germany, Berlin3", "Some Description3"}
        };
    }

    @DataProvider
    public Iterator<Object[]> objectDataProvider() {
        List<Object[]> list = new ArrayList<>();
        list.add(new Object[]{new Contact().setName("Name1").setLastName("LastName1").setPhone("1234567890").setEmail("admin1@gmail.com").setAddress("Germany, Berlin1").setDescription("Description1")});
        list.add(new Object[]{new Contact().setName("Name2").setLastName("LastName2").setPhone("1234567891").setEmail("admin2@gmail.com").setAddress("Germany, Berlin2").setDescription("Description2")});
        list.add(new Object[]{new Contact().setName("Name3").setLastName("LastName3").setPhone("1234567892").setEmail("admin3@gmail.com").setAddress("Germany, Berlin3").setDescription("Description3")});
        list.add(new Object[]{new Contact().setName("Name4").setLastName("LastName4").setPhone("1234567893").setEmail("admin4@gmail.com").setAddress("Germany, Berlin4").setDescription("Description4")});
        return list.iterator();
    }

    @DataProvider
    public Iterator<Object[]> addContactFromCsv() throws IOException {
        // Создаем список для хранения данных для тестов
        List<Object[]> list = new ArrayList<>();
        // Открываем CSV файл для чтения
        BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/contacts.csv"));
        // Читаем первую строку из файла
        String line = reader.readLine();
        // Обрабатываем каждую строку файла до конца
        while (line != null) {
            // Разделяем строку на элементы по запятой
            String[] split = line.split(",");
            // Создаем объект Contact и устанавливаем его поля из прочитанных данных
            list.add(new Object[]{new Contact()
                    .setName(split[0])
                    .setLastName(split[1])
                    .setPhone(split[2])
                    .setEmail(split[3])
                    .setAddress(split[4])
                    .setDescription(split[5])
            });
            // Читаем следующую строку из файла
            line = reader.readLine();
        }
        // Закрываем файл после чтения всех данных
        reader.close();
        // Возвращаем итератор для списка объектов
        return list.iterator();
    }
}
