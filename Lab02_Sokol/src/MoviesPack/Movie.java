package MoviesPack;

import MoviesPack.MoviesWork.Request;

public abstract class Movie implements Request {
    private String name;
    private double paymentToOneParticipantOfFilming; //оплата одному участнику съемок

    public Movie(String name, double paymentToOneParticipantOfFilming) {
        this.name = name;
        this.paymentToOneParticipantOfFilming = paymentToOneParticipantOfFilming;
    }

    @Override
    public String toString() {
        return  name + ", оплата 1-му участнику: " + paymentToOneParticipantOfFilming + ", ";
    }

    @Override
    public double calcPaymentToTheFilmCrew(){
        return paymentToOneParticipantOfFilming;
    }
}
