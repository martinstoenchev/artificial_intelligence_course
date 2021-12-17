public class City {

    private double x;
    private double y;

    public City() {
        this.x = Math.random()*200;
        this.y = Math.random()*200;
    }

    public City(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getDistanceTo(City city) {
        double xDist = Math.abs(this.x - city.getX());
        double yDist = Math.abs(this.y - city.getY());

        return Math.sqrt((xDist*xDist) + (yDist*yDist));
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        City city = (City) o;
        return this.x == city.getX() && this.y == city.getY();
    }

    @Override
    public final int hashCode() {
        double result = 17;

        result = 31 * result + x;
        result = 31 * result + y;
        return (int)result;
    }


}
