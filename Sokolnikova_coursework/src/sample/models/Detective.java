package sample.models;

public class Detective extends Book{
    public int numberOfMurdCrimes; // число убийств/преступлений
    public enum NarTime {middle, new1, modern, modernity} // Время повествования
    public NarTime narTime;

    public Detective() {};

    public Detective(String title, String author, String rday, String dpopular, String typeofissue, int numberOfMurdCrimes, NarTime narTime) {
        super(title, author, rday, dpopular, typeofissue);
        this.numberOfMurdCrimes = numberOfMurdCrimes;
        this.narTime = narTime;
    }

    @Override
    public String getDescription() {
        String numberOfMurdCrimesString = " Число убийств/преступлений: "+this.numberOfMurdCrimes+".";
        String narTimeString = "";
        switch (this.narTime)
        {
            case middle:
                narTimeString = " Средние века.";
                break;
            case new1:
                narTimeString = " Новое время.";
                break;
            case modern:
                narTimeString = " Новейшее время.";
                break;
            case modernity:
                narTimeString = " Современность.";
                break;
        }
        return String.format("Детектив. %s%s", numberOfMurdCrimesString, narTimeString);
    }
}
