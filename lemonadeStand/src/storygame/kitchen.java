package storygame;

public class kitchen {
    
    protected int kitchenLevel = 15;
    protected int costOfNextUpgrade;

    public kitchen(int kitchenLevel,int startingPriceOfUpgrade) {
        this.kitchenLevel = kitchenLevel;
        this.costOfNextUpgrade = startingPriceOfUpgrade;
    }
    
    public int makeLemonadeNum(int lemons){
        int newLemonade = 0;
        if(lemons <= kitchenLevel){
            newLemonade = lemons*2;
        }else{
            newLemonade = kitchenLevel*2;
        }
        Spaces();
        if (newLemonade == 0){
            Spaces();
            System.out.println("NOT ENOUGH LEMONS");
        }
        return newLemonade;
    }
    
    public int upgradeKitchen_ReturnsNewMoney(int money) {
        if(money > costOfNextUpgrade){
            kitchenLevel++;
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
