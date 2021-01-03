package gameobject;

public class Obstacle {
    private String type;
    public double delay;
    public int x, y = 515;
    public int height, width;

    public Obstacle(String type, double delay) {
        this.type = type;
        this.delay = delay;

        setPosY();
        reset();
    }

    public void setPosY() {
        switch (this.type) {
            case "dx1":
                y = 525;
                break;
            case "fly":
                y = 425;
                break;
        }
    }

    public void reset() {
        this.x = (int) (1280 * delay + Math.random() * 720);
    }

    public void update(int x) {
        this.x -= x;

        if (this.x < -75)
            reset();
    }

    public String getImageName() {
        if (this.type.equals("fly")) {
            if (this.x / 50 % 2 == 0) {
                return this.type + "_1";
            } else {
                return this.type + "_2";
            }
        } else {
            return this.type;
        }
    }
}
