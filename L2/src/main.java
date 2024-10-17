public class main
{
    int n = 20;
    int k = 80;
    int C_count;
    Critter[] Crts = new Critter[k];

    StockpileCritter SC;

    public static void main(final String[] args) throws InterruptedException {
        new main();
    }

    public main() throws InterruptedException {
        C_count = k;
        int[][] rnd = new int[k*3][2];
        rnd[0][0] = ((int) (Math.random() * 100))%n; rnd[0][1] = ((int) (Math.random() * 100))%n;
        for (int i=1; i<k*3; i++)
        {
            int[] r = {((int) (Math.random() * 100))%n, rnd[0][0] = ((int) (Math.random() * 100))%n};
            boolean f = true;
            for (int j = 0; j<i; j++) { if (rnd[j] == r) { f = false; break; } }
            if (f)
            {
                rnd[i] = r;
            }
        }
        for (int i=0; i<k; i++)
        {
            Crts[i] = new Critter(rnd[i][0], rnd[i][1], ((int) (Math.random() * 10))%4+1);
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

            if ((C_count <= 0)||(SC.getEnergy()<0))
            {
                show_status(true, steps);
                break;
            }
            else
            {
                show_status(false, 0);
            }
            Thread.sleep(800);
        }
        return;
    }

    private void show_status(boolean f, int st)
    {
        if (f)
        {
            for (int i = 0; i<n*2; i++) {System.out.print("_");} 
            System.out.println();
            System.out.println("Конец!");
            if (C_count==0) {System.out.println("Криттер победил!!");} else {System.out.println("Криттер умер...");}
            System.out.println("Число шагов: " + st);
        }
        else
        {
            for (int i = 0; i<28-n; i++) {System.out.println();}

            String[][] M = new String[n][n];

            System.out.print(" ");
            for (int i = 0; i<n*2; i++) {System.out.print("_");}
            System.out.println();

            for (int i = 0; i<n; i++)
            {
                for (int j = 0; j<n; j++)
                {
                    M[i][j] = "_";
                }
            }
            for(int i = 0; i<k; i++)
            {
                if (Crts[i] != null)
                {
                    if (Crts[i].pwr <= 2) {M[Crts[i].x][Crts[i].y] = "*";}
                    else {M[Crts[i].x][Crts[i].y] = "o";}
                }

            }
            if (SC.getEnergy() != 0)
            {
                if (SC.pwr <= 2) {M[SC.x][SC.y] = "Ф";}
                else {M[SC.x][SC.y] = "@";}
                
            }
            else
            {
                M[SC.x][SC.y] = ".";
            }

            for(int j=0; j<n; j++)
            {
                System.out.print("|");
                for (int i = 0; i<n; i++)
                {
                    System.out.print(M[j][i]+"|");
                }
                System.out.println();
            }
        }
    }

    public void kill(int count)
    {
        C_count -= count;
    }
}

class Critter
{
    int x, y, pwr;

    public Critter(int _x, int _y, int _pwr)
    {
        x = _x;
        y = _y;
        pwr = _pwr;
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
        pwr = 2;
    }

    public Critter[] act(Critter[] xy, main c)
    {
        Critter[] Crts = xy;
        if (energy>0)
        {
            int i, j;
            boolean f = true;
            if (x-1>=0) { i = x-1;} else { i = 0;}
            if (y-1>=0) { j = y-1;} else { j = 0;}
            for (int m=0;m<Crts.length;m++)
            {
                if (Crts[m] != null)
                {
                    if (((Crts[m].x>=i) && (Crts[m].x<=i+2)) && ((Crts[m].y>=j) && (Crts[m].y<=j+2)))
                    {
                        energy+=Crts[m].pwr;
                        Crts[m] = null;
                        c.kill(1);
                        f = false;
                    }
                }
            }
            if (f) {move(Crts); if (energy>n/4){pwr=4;} else {pwr=2;}}
            return Crts;
        }
        else
        {
            return xy;
        }
    }

    public void move(Critter[] Crts)
    {
        boolean f = true;
        for (int m=0;m<Crts.length;m++)
        {
            if (Crts[m] != null)
            {
                if (((Crts[m].x>=x-2) && (Crts[m].x<=x-1)) && ((Crts[m].y>=y-2) && (Crts[m].y<=y-1)))
                {
                    x = (x-1); if (x<0){x = 0;}
                    y = (y-1); if (y<0){y = 0;}
                    f = false;
                    break;
                }
                else if (((Crts[m].x>=x-2) && (Crts[m].x<=x-1)) && ((Crts[m].y>=y+2) && (Crts[m].y<=y+1)))
                {
                    x = (x-1); if (x<0){x = 0;}
                    y = (y+1)%n;
                    f = false;
                    break;
                }
                else if (((Crts[m].x>=x+2) && (Crts[m].x<=x+1)) && ((Crts[m].y>=y+2) && (Crts[m].y<=y+1)))
                {
                    x = (x+1)%n;
                    y = (y+1)%n;
                    f = false;
                    break;
                }
                else if (((Crts[m].x>=x+2) && (Crts[m].x<=x+1)) && ((Crts[m].y>=y-2) && (Crts[m].y<=y-1)))
                {
                    x = (x+1)%n;
                    y = (y-1); if (y<0){y = 0;}
                    f = false;
                    break;
                }
                else if (((Crts[m].x>=x+1)||(Crts[m].x<=x+2))&&(Crts[m].y==y))
                {
                    x = (x+1)%n;
                    f = false;
                    break;
                }
                else if (((Crts[m].x<=x-1)||(Crts[m].x>=x-2))&&(Crts[m].y==y))
                {
                    x = (x-1); if (x<0){x = 0;}
                    f = false;
                    break;
                }
                else if (((Crts[m].y>=y+1)||(Crts[m].y<=y+2))&&(Crts[m].x==x))
                {
                    y = (y+1)%n;
                    f = false;
                    break;
                }
                else if (((Crts[m].y<=y-1)||(Crts[m].y>=y-2))&&(Crts[m].x==x))
                {
                    y = (y-1); if (y<0){y = 0;}
                    f = false;
                    break;
                }
            }
        }
        if (f)
        {
            double dx = Math.random();
            double dy = Math.random();
            if (dx>0.60) {
                x = (x+1)%n;
            }
            else if (dx<0.40)
            {
                x -= 1;
                if (x<0){x = 0;}
            }

            if (dy>0.60) {
                y = (y+1)%n;
            }
            else if (dy<0.40)
            {
                y -= 1;
                if (y<0){y = 0;}
            }
        }
        energy--;
    }

    int getEnergy()
    {
        return energy;
    }
}