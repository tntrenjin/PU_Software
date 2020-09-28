package gameplay;

public class Cloud {
    public int x, y;
    double speed = 0.1;

    public Cloud() {
        reset();
    }

    public void reset() {
        this.x = (int) (Math.random() * 1280 * 1.5);
        this.y = (int) (Math.random() * 360 + 100);
    }

    public void update(int x) {
        this.x -= (int) Math.ceil(x * this.speed);

        if (this.x < -75) {
            reset();
            this.x += 1280;
        }
    }
}
