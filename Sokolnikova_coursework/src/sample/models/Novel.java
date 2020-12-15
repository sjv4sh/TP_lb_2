package sample.models;

public class Novel extends Book {
    public double ageMain; // Возраст главного героя
    public double ageSecond; // Возраст пассии главного героя
    public double countTransform; // Кол-во трансформаций главного героя

    public Novel() {};

    public Novel(String title, String author, String rday, String dpopular, String typeofissue, double ageMain, double ageSecond, double countTransform) {
        super(title, author, rday, dpopular, typeofissue);
        this.ageMain = ageMain;
        this.ageSecond = ageSecond;
        this.countTransform = countTransform;
    }

    @Override
    public String getDescription() {
        String ageMainString = "Возраст главного героя: "+this.ageMain+".";
        String ageSecondString = " Возраст пассии главного героя: "+this.ageSecond+".";
        String countTransformString = " Кол-во трансформаций главного героя: "+this.countTransform+".";
        return String.format("Новелла. %s%s%s", ageMainString, ageSecondString, countTransformString);
    }
}
