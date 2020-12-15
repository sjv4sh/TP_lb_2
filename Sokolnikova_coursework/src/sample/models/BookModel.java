package sample.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class BookModel {
    ArrayList<Book> bookList = new ArrayList<>();
    private int counter = 1;
    Class<? extends Book> bookFilter = Book.class;

    public void saveToFile(String path) {
        try (Writer writer =  new FileWriter(path)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerFor(new TypeReference<ArrayList<Book>>() { })
                    .withDefaultPrettyPrinter()
                    .writeValue(writer, bookList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadToFile(String path) {
        try (Reader reader =  new FileReader(path)) {
            ObjectMapper mapper = new ObjectMapper();
            bookList = mapper.readerFor(new TypeReference<ArrayList<Book>>() { })
                    .readValue(reader);
            this.counter = bookList.stream()
                    .map(book -> book.id)
                    .max(Integer::compareTo)
                    .orElse(0) + 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.emitDataChanged();
    }

    public interface DataChangedListener {
        void dataChanged(ArrayList<Book> bookList, ArrayList<String> sp);
    }

    private ArrayList<DataChangedListener> dataChangedListener = new ArrayList<>();

    public void addDataChangedListener(DataChangedListener listener) {
        this.dataChangedListener.add(listener);
    }

    public void load() {

        bookList.clear();
        this.add(new Satire("Алиса в стране чудес", "Льюис Кэрролл", "14.01.1865", "Весь мир", "Бумажный носитель", 12, true), false);
        this.add(new Detective("Алмазная колесница", "Борис Акунин", "22.01.2003", "Россия", "Бумажный носитель", 0, Detective.NarTime.new1), false);
        this.add(new Novel("Сумерки", "Майер Стефани Морган", "09.01.2005", "Весь мир", "Электронная версия", 100, 17, 2), false);
        this.add(new Satire("Двенадцать стульев", "Ильф и Петров", "08.01.1927", "Россия", "Бумажный носитель", 7, true), false);
        this.add(new Detective("Убийство на улице Морг", "Эдгар Аллан По", "01.01.1841", "Весь мир", "Бумажный носитель", 1, Detective.NarTime.new1), false);
        this.add(new Novel("Унесенные ветром", "Маргарет Митчелл", "13.01.1936", "Весь мир", "Бумажный носитель", 21, 18, 1), false);
        this.add(new Satire("Собачье сердце", "Михаил Булгаков", "19.01.1925", "Россия", "Бумажный носитель", 4, false), false);

        this.emitDataChanged();
    }

    public void delete(int id)
    {
        for (int i = 0; i< this.bookList.size(); ++i) {
            if (this.bookList.get(i).id == id) {
                this.bookList.remove(i);
                break;
            }
        }

        this.emitDataChanged();
    }

    private void emitDataChanged() {
        for (DataChangedListener listener: dataChangedListener) {
            ArrayList<Book> filteredList = new ArrayList<>(
                    bookList.stream()
                            .filter(book -> bookFilter.isInstance(book)) // фильтруем по типу
                            .collect(Collectors.toList()) // превращаем в список
            );
            listener.dataChanged(filteredList, this.getDpopular());
        }
    }

    public ArrayList<String> getDpopular() {
        ArrayList<String> sp = this.bookList.stream()
                .map(books -> books.getDpopular())
                .distinct()
                .sorted()
                .collect(Collectors.toCollection(ArrayList::new));
        sp.add(0,null);
        return sp;
    }

    public void add(Book book, boolean emit) {
        book.id = counter;
        counter += 1;
        this.bookList.add(book);
        if (emit) {
            this.emitDataChanged();
        }
    }
    public void add(Book book) {
        add(book, true);
    }

    public void edit(Book book) {
        for (int i = 0; i< this.bookList.size(); ++i) {
            if (this.bookList.get(i).id == book.id) {
                this.bookList.set(i, book);
                break;
            }
        }
        this.emitDataChanged();
    }

    public void setBookFilter(Class<? extends Book> bookFilter) {
        this.bookFilter = bookFilter;
        this.emitDataChanged();
    }

}

