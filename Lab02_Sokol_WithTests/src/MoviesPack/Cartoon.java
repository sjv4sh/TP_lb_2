package MoviesPack;

public class Cartoon extends Movie{
    public enum CreationMethod {
        нарисованный, кукольный, пластилиновый
    }

    private CreationMethod creationMethod;
    private int numberOfArtists;

    public Cartoon(String name, double paymentToOneParticipantOfFilming,
                   CreationMethod creationMethod, int numberOfArtists) {
        super(name, paymentToOneParticipantOfFilming);
        this.creationMethod = creationMethod;
        this.numberOfArtists = numberOfArtists;
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
}
