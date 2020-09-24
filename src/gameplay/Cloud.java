package gameplay;

public class Cloud
{
    public int x, y;

    public Cloud()
    {
        reset();
    }

    public void reset()
    {
        this.x = (int) (Math.random() * 1200);
        this.y = (int) (Math.random() * 360 + 100);
    }

    public void update(int x)
    {
        this.x -= (int) Math.ceil(x * 0.2);

        if (this.x < -75)
        {
            this.x = 1280 + (int) (Math.random() * 1000);
            this.y = (int) (Math.random() * 360 + 100);

        }
    }
}
