package sample.Hierarchy;

public abstract class Movie implements Request {
    private String name;
    private double paymentToOneParticipantOfFilming; //оплата одному участнику съемок

    public Movie(String name, double paymentToOneParticipantOfFilming) {
        this.name = name;
        this.paymentToOneParticipantOfFilming = paymentToOneParticipantOfFilming;
    }

    public Movie() {
    }

    @Override
    public String toString() {
        return  name + ", оплата 1-му участнику: " + paymentToOneParticipantOfFilming + ", ";
    }

    @Override
    public double calcPaymentToTheFilmCrew(){
        return paymentToOneParticipantOfFilming;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPaymentToOneParticipantOfFilming() {
        return paymentToOneParticipantOfFilming;
    }

    public void setPaymentToOneParticipantOfFilming(double paymentToOneParticipantOfFilming) {
        this.paymentToOneParticipantOfFilming = paymentToOneParticipantOfFilming;
    }
}
