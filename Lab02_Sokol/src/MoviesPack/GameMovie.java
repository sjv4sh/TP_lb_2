package MoviesPack;

public class GameMovie extends Movie{
    private String producer;
    private int numberOfPeopleInTheSet; // число человек в съемочной

    public GameMovie(String name, double paymentToOneParticipantOfFilming,
                     String producer, int numberOfPeopleInTheSet) {
        super(name, paymentToOneParticipantOfFilming);
        this.producer = producer;
        this.numberOfPeopleInTheSet = numberOfPeopleInTheSet;
    }

    @Override
    public String toString() {
        return super.toString() +
                "Режиссер: " + producer
                + ", человек в группе: " + numberOfPeopleInTheSet;
    }

    @Override
    public double calcPaymentToTheFilmCrew() {
        return super.calcPaymentToTheFilmCrew() * numberOfPeopleInTheSet;
    }
}
