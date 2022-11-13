package Hammurabi;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Hammurabi {
    Random rand = new Random();
    Scanner scanner = new Scanner(System.in);
    boolean rule = true;
    int count = 0;

    public static void main(String[] args) {
        new Hammurabi().playGame();
    }

    void playGame() {
        int people = 100;
        int bushels = 2800;
        int acres = 1000;
        int landValue = 19;
        int yearlyDead = 0;
        int totalDead = 0;
        printGreeting();

        while (rule) {
            printSummary(yearlyDead, people, bushels, acres, landValue);

            int landBought = askHowManyAcresToBuy(landValue, bushels);
            if (landBought == 0) {
                int landSold = askHowManyAcresToSell(acres);
                acres -= landSold;
                bushels += landSold * landValue;
            }
            if (landBought > 0) {
                acres += landBought;
                bushels -= landBought * landValue;
            }
            int food = askHowMuchGrainToFeedPeople(bushels, people);
            bushels -= (food * people);
            int plant = askHowManyAcresToPlant(acres, people, bushels);
            bushels -= (plant * 2);

            int peopleFed = bushels / 20;
            int peopleStarved = 0;
            if (peopleFed < people) {
                peopleStarved = people - peopleFed;
            }
            people -= peopleStarved;
            yearlyDead = peopleStarved;
            totalDead += peopleStarved;
            int harvest = harvest(plant);
            bushels += harvest;
            landValue = newCostOfLand();
            count++;
            if (count == 3) rule = false;
        }
        finalSummary(totalDead);
    }

    private void printGreeting() {
        System.out.println("Congratulations, you are the newest ruler of ancient Sumer, elected for a ten year term of office." +
                "\nYour duties are to dispense food, direct farming, and buy and sell land as needed to support your people." +
                "\nWatch out for rat infestiations and the plague! Grain is the general currency, measured in bushels." +
                "\nThe following will help you in your decisions:" +
                "\n\tEach person needs at least 20 bushels of grain per year to survive" +
                "\n\tEach person can farm at most 10 acres of land" +
                "\n\tIt takes 2 bushels of grain to farm an acre of land" +
                "\n\tThe market price for land fluctuates yearly" +
                "\nRule wisely and you will be showered with appreciation at the end of your term" +
                "\nRule poorly and you will be kicked out of office!" +
                "\n***************\n");
    }

    private void printSummary(int yearlyDead, int people, int grain, int acres, int landValue) {
        String s = "\nO great Hamurabi\n" +
                String.format("You are in year %s of your ten year rule.\n", count + 1) +
                String.format("In the previous year %s people died.\n", yearlyDead) +
                String.format("The population is now %s.\n", people) +
                String.format("The city has %s bushels in storage.\n", grain) +
                String.format("The city owns %s acres of land.\n", acres) +
                String.format("Land is currently worth %s bushels per acre.\n", landValue);

        System.out.println(s);
    }

    private int askHowManyAcresToBuy(int landValue, int grain) {
        int buy = getNumber("How many acres do you wish to purchase? ");
        if (buy * landValue > grain) {
            buy = getNumber(String.format("Think again. You only have %s bushels of grain. ", grain));
        }
        return buy;
    }

    private int askHowManyAcresToSell(int acres) {
        int sell = getNumber("How many acres do you wish to sell? ");
        if (sell > acres) {
            sell = getNumber(String.format("Think again. You only have %s acres. ", acres));
        }
        return sell;
    }

    private int askHowMuchGrainToFeedPeople(int grain, int people) {
        int feed = getNumber("How much grain should each person be fed? ");
        if (feed * people > grain) {
            feed = getNumber(String.format("Think again. We only have %s bushels of grain ", grain));
        }
        return feed;
    }

    private int askHowManyAcresToPlant(int acres, int people, int grain) {
        int plant = getNumber("How many acres of land would you like to plant with grain? ");
        if (plant > people * 10 || plant > grain / 2 * acres) {
            plant = getNumber("We cannot plant that many acres. ");
        }
        return plant;
    }

    int getNumber(String message) {
        while (true) {
            System.out.print(message);
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\"" + scanner.next() + "\" isn't a number!");
            }
        }
    }


    private void finalSummary(int total) {
        System.out.println("Hammurabi you did great");
        System.out.println("Unfortunately" + total + " people have died.");
    }

    public int plagueDeaths(int population) {
        return population /2;
    }

    public int starvationDeaths(int population, int bushelsFedToPeople) {
        int starved = 0;
        int fedPeople = bushelsFedToPeople / 20;
        if (population > fedPeople){
            starved = population - fedPeople;
        }
        return starved;
    }



    public boolean uprising(int population, int howManyPeopleStarved) {
        if (howManyPeopleStarved > 0.45 * population){
            return true;
        }
        return false;
    }

    public int immigrants(int population, int acresOwned, int grainStorage) {
        return (20 * acresOwned + grainStorage) / (100 * population) + 1;
    }

    public int harvest(int acres){
        int random = (int)(Math.random()*(6 - 1 + 1)+1);
        return random;
    }

    public int grainEatenByRats(int bushels) {
        int ratGrain = 0;
        int ratindex = rand.nextInt(101);
        if (ratindex > 60) {
            ratGrain = (int) (((10 + rand.nextInt(21)) / 100.0) * bushels);
        }
        return ratGrain;
    }
    public int newCostOfLand() {
        return rand.nextInt(23 - 17 + 1) + 17;
    }

}