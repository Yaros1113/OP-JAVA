public class main
{
    Sandwich sandwich;
    Salad salad;
    Drink drink;
    Trio trio;
    Interface MenuItem;
    public static void main(final String[] args) throws InterruptedException {
        new main();
    }

    public main()
    {
        sandwich = new Sandwich();
        salad = new Salad();
        drink = new Drink();
        trio = new Trio(sandwich, salad, drink);
        MenuItem = new Interface(trio);

        show_status();
    }

    private void show_status()
    {
        System.out.println("Trio name is " + MenuItem.getName() + " and a price of $" + MenuItem.getPrice() +
                " (the two highest prices are $" + MenuItem.max1p + " and $" + MenuItem.max2p + ").");

    }
}

class Interface
{
    private Trio T;
    private String name;
    private double price;
    double max1p, max2p;

    public Interface(Trio trio)
    {
        T = trio;
        name = T.getSandwich().name + T.getSalad().name + T.getDrink().name;
        price = T.getSandwich().cost + T.getSalad().cost + T.getDrink().cost - Math.min(Math.min(T.getSandwich().cost, T.getSalad().cost), T.getDrink().cost);
        max1p = Math.max(Math.max(T.getSandwich().cost, T.getSalad().cost), T.getDrink().cost);
        max2p = price - max1p;
    }
    /** @return the name of the menu item */
    public String getName()
    {
        return name;
    }
    /** @return the price of the menu item */
    public double getPrice()
    {
        return price;
    }
}

class baseBl
{
    String name;
    double cost;
}
class Sandwich extends baseBl
{
    String[] names = {"Cheeseburger/", "Club Sandwich/", "Spicy Chicken Wrap/", "Mediterranean Veggie Delight/", "BBQ Pulled Pork Sandwich/"};
    public Sandwich()
    {
        name = names[((int) (Math.random() * 10))%5];
        cost = Math.round(Math.random() * 1000)/100.0;
    }
}

class Salad extends baseBl
{
    String[] names = {"Caesar Salad/","Greek Salad/","Quinoa and Black Bean Salad/","Caprese Salad/"};
    public Salad()
    {
        name = names[((int) (Math.random() * 10))%4];
        cost = Math.round(Math.random() * 1000)/100.0;
    }
}

class Drink extends baseBl
{
    String[] names = {"Mango Lassi","Iced Matcha Latte","Sparkling Lemonade","Berry Smoothie","Coconut Water"};
    public Drink()
    {
        name = names[((int) (Math.random() * 10))%5];
        cost = Math.round(Math.random() * 1000)/100.0;
    }
}

class Trio
{
    private Sandwich sandwich;
    private Salad salad;
    private Drink drink;

    public Trio(Sandwich _sandwich, Salad _salad, Drink _drink)
    {
        sandwich = _sandwich;
        salad = _salad;
        drink = _drink;
    }

    public Sandwich getSandwich()
    {
        return sandwich;
    }

    public Salad getSalad()
    {
        return salad;
    }

    public Drink getDrink()
    {
        return drink;
    }
}