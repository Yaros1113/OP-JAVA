public class main
{
    int n = 8;
    int k = 8;
    int C_count;
    Critter[] Crts = new Critter[k];

    StockpileCritter SC;

    /*public main(int _n, int _k)
    {
        n = _n; k = _k;
        for (int i=0; i<k+1; i++)
        {
            Crts[i] = new Critter(Math.abs(n-i), (k+i)%n);
        }

        SC = new StockpileCritter(Crts[k+1].x, Crts[k+1].y, n);
        Crts[k+1] = null;

        run();
    }*/

    public static void main(final String[] args) throws InterruptedException {
        new main();
    }

    public main() throws InterruptedException {
        C_count = k;
        int[][] rnd = new int[n*2][2];
        rnd[0][0] = ((int) (Math.random() * 10))%n; rnd[0][1] = ((int) (Math.random() * 100))%n;
        for (int i=1; i<n*2; i++)
        {
            int[] r = {((int) (Math.random() * 10))%n, rnd[0][0] = ((int) (Math.random() * 100))%n};
            boolean f = true;
            for (int j = 0; j<i; j++) { if (rnd[j] == r) { f = false; break; } }
            if (f)
            {
                rnd[i] = r;
            }
        }
        for (int i=0; i<k; i++)
        {
            Crts[i] = new Critter(rnd[i][0], rnd[i][1]);
        }

        SC = new StockpileCritter(rnd[k+1][0], rnd[k+1][1], n);

        run();
    }

    private void run() throws InterruptedException {
        int steps = 0;
        while (true)
        {
            Crts = SC.act(Crts, this);
            steps++;

            if (C_count <= 0)
            {
                show_status(true, steps);
                break;
            }
            else
            {
                show_status(false, 0);
            }
            Thread.sleep(1000);
        }
        return;
    }

    private void show_status(boolean f, int st)
    {
        if (f)
        {
            System.out.println("Конец!");
            System.out.println("Число шагов: " + st);
        }
        else
        {
            String[][] M = new String[n][n];
            for (int i = 0; i<n; i++)
            {
                for (int j = 0; j<n; j++)
                {
                    M[i][j] = " ";
                }
            }
            for(int i = 0; i<k; i++)
            {
                if (Crts[i] != null)
                {
                    M[Crts[i].x][Crts[i].y] = "*";
                }

            }
            if (SC.getEnergy() != 0)
            {
                M[SC.x][SC.y] = "@";
            }
            else
            {
                M[SC.x][SC.y] = ".";
            }

            for(int j=0; j<n; j++)
            {
                for (int i = 0; i<n; i++)
                {
                    System.out.print(M[j][i]);
                }
                System.out.println();
            }
            System.out.println();
            System.out.println();
        }
    }

    public void kill(int count)
    {
        C_count -= count;
    }
}

class Critter
{
    int x, y;

    public Critter(int _x, int _y)
    {
        x = _x;
        y = _y;
    }

    public Critter() { }
}

class StockpileCritter extends Critter
{
    private int energy;
    int n;

    public StockpileCritter(int _x, int _y, int _n)
    {
        x = _x;
        y = _y;
        n = _n;
        energy = 2;
    }

    public Critter[] act(Critter[] xy, main c)
    {
        Critter[] Crts = xy;
        if (energy>0)
        {
            int i, j;
            if (x-1>=0) { i = x-1;} else { i = 0;}
            if (y-1>=0) { j = y-1;} else { j = 0;}
            for (int m=0;m<Crts.length;m++)
            {
                if (Crts[m] != null)
                {
                    if (((Crts[m].x>=i) && (Crts[m].x<=i+3)) && ((Crts[m].y>=j) && (Crts[m].y<=j+3)))
                    {
                        Crts[m] = null;
                        c.kill(1);
                        energy++;
                    }
                }
            }
            move();
            return xy;
        }
        else
        {
            c.kill(c.C_count);
            return xy;
        }
    }

    public void move()
    {
        double dx = Math.random();
        double dy = Math.random();
        if (dx>0.60) {
            x = (x+1)%n;
        }
        else if (dx<0.30)
        {
            x -= 1;
            if (x<0){x = 0;}
        }

        if (dy>0.60) {
            y = (y+1)%n;
        }
        else if (dy<0.30)
        {
            y -= 1;
            if (y<0){y = 0;}
        }
        energy--;
    }

    int getEnergy()
    {
        return energy;
    }
}