package sample.Hierarchy;

public class Cartoon extends Movie{
    public String creationMethod;

    private int numberOfArtists;

    public Cartoon(String name, double paymentToOneParticipantOfFilming,
                   String creationMethod, int numberOfArtists) {
        super(name, paymentToOneParticipantOfFilming);
        this.creationMethod = creationMethod;
        this.numberOfArtists = numberOfArtists;
    }

    public Cartoon() {
    }

    @Override
    public String toString() {
        return super.toString() + creationMethod +
                ", кол-во художников: " + numberOfArtists;
    }

    @Override
    public double calcPaymentToTheFilmCrew() {
        return super.calcPaymentToTheFilmCrew() * numberOfArtists;
    }

    public String getCreationMethod() {
        return creationMethod;
    }

    public void setCreationMethod(String creationMethod) {
        this.creationMethod = creationMethod;
    }

    public int getNumberOfArtists() {
        return numberOfArtists;
    }

    public void setNumberOfArtists(int numberOfArtists) {
        this.numberOfArtists = numberOfArtists;
    }
}
