package Hammurabi;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Hammurabi {
    Random rand = new Random();
    Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        new Hammurabi().playGame();
    }

    void playGame() {
        int year = 0;
        int immigrants = 0;
        int population = 100;
        int bushels = 2800;
        int grainAte = 0;
        int acres = 1000;
        int landValue = 19;
        int yearlyDead = 0;
        int totalDead = 0;
        int diedFromPlague = 0;
        printGreeting();

        while (year <= 10) {
            printSummary(year, yearlyDead, immigrants, population, bushels, grainAte, acres, landValue);

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
            int food = askHowMuchGrainToFeedPeople(bushels, population);
            bushels -= (food * population);
            int plant = askHowManyAcresToPlant(acres, population, bushels);
            bushels -= (plant * 2);

            int peopleFed = bushels / 20;
            int peopleStarved = 0;
            if (peopleFed < population) {
                peopleStarved = population - peopleFed;
            }
            population -= peopleStarved;
            yearlyDead = peopleStarved;
            totalDead += peopleStarved;
            if (peopleStarved == 0) {
                immigrants = immigrants(population, acres, bushels);
                population += immigrants;
            }
            int harvest = harvest(plant);
            diedFromPlague = plagueDeaths(population);
            population -= diedFromPlague;
            if (uprising(population, peopleStarved)){
                break;
            }
            bushels += harvest;
            grainAte = grainEatenByRats(bushels);
            bushels -= grainAte;
            landValue = newCostOfLand();
            year++;
        }
        finalSummary(year, totalDead, yearlyDead, population, bushels, acres, landValue);
    }

    private void printGreeting() {
        System.out.println("Congratulations, you are the newest ruler of ancient Sumer, elected for a ten year term of office." +
                "\nYour duties are to dispense food, direct farming, and buy and sell land as needed to support your people." +
                "\nWatch out for rat infestations and the plague! Grain is the general currency, measured in bushels." +
                "\nThe following will help you in your decisions:" +
                "\n\tEach person needs at least 20 bushels of grain per year to survive" +
                "\n\tEach person can farm at most 10 acres of land" +
                "\n\tIt takes 2 bushels of grain to farm an acre of land" +
                "\n\tThe market price for land fluctuates yearly" +
                "\nRule wisely and you will be showered with appreciation at the end of your term" +
                "\nRule poorly and you will be kicked out of office!" +
                "\n***************\n");
    }

    private void printSummary(int year, int yearlyDead, int immigrants, int population, int bushels, int grainAte, int acres, int landValue) {
        System.out.println(("\nO great Hammurabi!\n" +
                "You are in year " + year + " of your ten year rule.\n" +
                "In the previous year " + yearlyDead + " people starved to death.\n" +
                "In the previous year " +  immigrants + " people entered the kingdom.\n" +
                "The population is now " + population + ".\n" +
                "We harvested " + bushels + " bushels at 3 bushels per acre.\n" +
                "Rats destroyed " + grainAte + " leaving " + bushels + " bushels in storage.\n" +
                "The city owns " + acres + " acres of land.\n" +
                "Land is currently worth " + landValue + " bushels per acre.\n"));
        System.out.println("\n");
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

    private int askHowMuchGrainToFeedPeople(int grain, int population) {
        int feed = getNumber("How much grain should each person be fed? ");
        if (feed * population > grain) {
            feed = getNumber(String.format("Think again. We only have %s bushels of grain ", grain));
        }
        return feed;
    }

    private int askHowManyAcresToPlant(int acres, int population, int grain) {
        int plant = getNumber("How many acres of land would you like to plant with grain? ");
        if (plant > population * 10 || plant > grain / 2 * acres) {
            plant = getNumber("We cannot plant that many acres. ");
        }
        return plant;
    }

    public int getNumber(String message) {
        while (true) {
            System.out.print(message);
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\"" + scanner.next() + "\" isn't a number!");
            }
        }
    }

    public void finalSummary(int year, int total, int yearlyDead, int population, int grain, int acres, int landValue) {
        if(year == 10){
            System.out.println("Hammurabi you did great");
        }
        System.out.println("Unfortunately "+ total +" people have died.");
    }

    public int plagueDeaths(int population) {
        int numberDead = 0;
        if (rand.nextInt(101) > 85) {
            numberDead = population / 2;
        }
        return numberDead;
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
        int harvest = (int)(Math.random()*(6 - 1 + 1)+1);
        return harvest;
    }

    public int grainEatenByRats(int bushels) {
        int ratGrain = 0;
        if (rand.nextInt(101) > 60) {
            ratGrain = (int) (((10 + rand.nextInt(21)) * bushels / 100.0));
        }
        return ratGrain;
    }
    public int newCostOfLand() {
        return rand.nextInt(23 - 17 + 1) + 17;
    }

}