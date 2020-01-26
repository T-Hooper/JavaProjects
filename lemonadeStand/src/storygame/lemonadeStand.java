package storygame;

public class lemonadeStand  {
    
    protected int lemonadStandLevel;
    protected int costOfNextUpgrade = 10;

    public lemonadeStand(int lemonadStandLevel,int startingPriceOfUpgrade) {
        this.lemonadStandLevel = lemonadStandLevel;
        this.costOfNextUpgrade = startingPriceOfUpgrade;
    }
    
    public int sellLemonadeNum(int lemonade){
        int maxLemonadeToBeSold = lemonadStandLevel;
        int lemonadeToBeSold = 0;
        if(lemonade >= maxLemonadeToBeSold){
            lemonadeToBeSold = maxLemonadeToBeSold;
        }else{
            lemonadeToBeSold = lemonade;
        }
        Spaces();
        if(lemonadeToBeSold == 0){
            Spaces();
            System.out.println("NOT ENOUGH LEMONADE");
        }
        return lemonadeToBeSold;
    }
    
    public int buyLemonsNum(int money){
        if(money >= 5){
            money = 5;
            Spaces();
        }else{
            money = 0;
            Spaces();
            System.out.println("NOT ENOUGH MONEY");
        }
        return money;
    }
    
    public int upgradeKitchen_ReturnsNewMoney(int money) {
        if(money > costOfNextUpgrade){
            lemonadStandLevel++;
            money -= costOfNextUpgrade;
            costOfNextUpgrade = costOfNextUpgrade*5/2;
            Spaces();
        }else{
            Spaces();
            System.out.println("NOT ENOUGH MONEY");
        }
        return money;
    }
    
    public static void Spaces(){
        for (int i = 0; i < 10; i++) {
            System.out.println("");
        }
    }
}
