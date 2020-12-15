package sample.models;

public class Satire extends Book {
    public int countOfHeroes; // кол-во героев в рассказе
    public boolean okCeleb; // Наличие знаменитостей

    public Satire() {};

    public Satire(String title, String author, String rday, String dpopular, String typeofissue, int countOfHeroes, boolean okCeleb) {
        super(title, author, rday, dpopular, typeofissue);
        this.countOfHeroes = countOfHeroes;
        this.okCeleb = okCeleb;
    }

    @Override
    public String getDescription() {
        String countOfHeroesString = "Кол-во героев в рассказе "+this.countOfHeroes+".";
        String okCelebString = this.okCeleb ? " Есть знаменитости." : " Нет знаменитостей.";
        return String.format("Сатира. %s%s", countOfHeroesString, okCelebString);
    }
}
