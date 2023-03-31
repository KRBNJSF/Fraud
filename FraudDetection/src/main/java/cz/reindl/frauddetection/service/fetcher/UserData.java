package cz.reindl.frauddetection.service.fetcher;

public class UserData {

    private String from, to;
    private double value;

    public UserData(String from) {
        this.from = from;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", value=" + value +
                '}';
    }
}
