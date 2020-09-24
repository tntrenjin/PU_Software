package gameplay;

public class Obstacle
{
    public String type;
    public int delay;
    public int x, y = 510;

    public Obstacle(String type, int delay)
    {
        this.type = type;
        this.delay = delay;

        reset();
    }

    public void reset()
    {
        this.x = 1280 * delay + (int) (Math.random() * 1000);
    }

    public void update(int x)
    {
        this.x -= x;

        if (this.x < -75)
            reset();
    }
}
