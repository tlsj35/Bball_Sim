//TJ Smith
//CS&141 - 15466
//Final Project

//This program runs a basic basketball simulator. The user will first be prompted to enter the attribute points
//for each of their player's stats. They will then choose how many turns will be played, with a turn ending upon
//a made/missed shot or a turnover.

//The team who starts with the ball is choosen at random. If it is the user's turn, they have the option to shoot,
//pass, or dribble. All interactions are plugged into a formula that includes the user's and cpu's corresponding
//stat: SHOOTING & BLOCK, PASSING & STEAL, and DRIBBLING & SPEED

//RNG determines the outcome of all interactions, with higher attribute points increasing the odds in your favor.
//commit to github

import java.util.*;

public class FinalProject{

   static Scanner console = new Scanner(System.in);//get all user inputs

   public static void main(String[] args){    
      PlayerCreation[] players = new PlayerCreation[10];//object that contains each player's stats
      int distance;//how far the player is from the hoop
      int ballCarrier;//player that has the ball
      int turns;//how many turns will be played in the game

      IO.println("===== BASKETBALL SIMULATOR 1.0 =====");

      //set user's stats
      for(int i = 0; i < 5; i++){
         IO.println("\nSet stats for PLAYER " + i);
         
         int sh = setUserStat("SHOOTING");
         int pa = setUserStat("PASSING");
         int dr = setUserStat("DRIBBLING");
         int bl = setUserStat("BLOCK");
         int st = setUserStat("STEAL");
         int sp = setUserStat("SPEED");

         players[i] = new PlayerCreation(sh, pa, dr, bl, st, sp);

         IO.println(PlayerCreation.toString("User", i, sh, pa, dr, bl, st, sp));
      }

      //set cpu's stats
      for(int i = 5; i < 10; i++){
         
         int sh = setCPUStat();
         int pa = setCPUStat();
         int dr = setCPUStat();
         int bl = setCPUStat();
         int st = setCPUStat();
         int sp = setCPUStat();
         
         players[i] = new PlayerCreation(sh, pa, dr, bl, st, sp);

         IO.println(PlayerCreation.toString("CPU", i, sh, pa, dr, bl, st, sp));
      }
      
      //set how many turns will be played
      turns = turnInput();

      //randomly determine who has the ball on the first turn
      ballCarrier = randNum();
      IO.println("\nPlayer " + ballCarrier + " wins the jumpball!");
      
      //starting score 0 - 0
      int userScore = 0;
      int cpuScore = 0;

      //--------------- GAMEPLAY LOOP ---------------
      while(turns > 0){
         IO.println("\nNEW POSSESSION | Score: USER " + userScore + " - CPU " + cpuScore + " | TURNS LEFT: " + turns);
           
         //randomly determine ballCarrier initial distance from the hoop
         distance = setDistance();
         IO.println("Starting distance from hoop: " + distance);

         boolean possessionActive = true;//checks if shot was taken or turnover committed
         while(possessionActive){

            //--------------- USER GAMEPLAY ---------------
            if(ballCarrier < 5){
            
               //prompt user to shoot, pass, or dribble
               IO.println("\nUser Player " + ballCarrier + " has the ball.");
               IO.println("Enter 's' to shoot, 'p' to pass, or 'd' to dribble:");
               String choice = charInput();

               int defender = ballCarrier + 5;//adding 5 matches up specific offensive and defensive players

               //shooting
               if(choice.equals("s")){
               
                  //pass relevant values to the shooting formula
                  if(Gameplay.shootBall(ballCarrier, defender, distance, players)){
                     IO.println("SHOT MADE!");
                     userScore += 2;
                  }
                  else{
                     IO.println("SHOT MISSED...");
                  }
                  ballCarrier += 5;
                  possessionActive = false;
               }

               //passing
               else if(choice.equals("p")){                  
                  IO.print("Pass to player (0 - 4) except yourself): ");
                  int target = passInput(ballCarrier);//ensure a pass is to a valid target
                  
                  //pass relevant values to the passing formula
                  if(Gameplay.passBall(ballCarrier, defender, distance, target, players)){
                     IO.println("Pass successful!");
                     ballCarrier = target;
                  }
                  else{
                     IO.println("Pass stolen!");
                     ballCarrier = defender;
                     possessionActive = false;
                  }
               }

               //dribbling
               else if(choice.equals("d")){
               
                  //pass relevant values to the dribbling formula
                  if(Gameplay.dribbleBall(ballCarrier, defender, distance, players)){
                     if(distance > 0){
                        distance--;
                        IO.println("Dribble successful! New distance: " + distance);
                     }
                     
                     //ensure distance doesn't go negative
                     else if(distance == 0){
                        IO.println("Out of bounds!");
                        ballCarrier += 5;
                        possessionActive = false;
                     }
                  }
                  else{
                     IO.println("Ball stolen on dribble!");
                     ballCarrier += 5;
                     possessionActive = false;
                  }
               }
            }//if(ballCarrier > 5)
            
            //--------------- CPU GAMEPLAY ---------------
            else{

               int cpu = ballCarrier;
               int defender = cpu - 5;//subtracting 5 matches up specific offensive and defensive players

               IO.println("\nCPU Player " + cpu + " has the ball...");

               //cpu will try to get closer before shooting
               String cpuMove;
               if(distance < 3){
                  cpuMove = "s";
               }
               else{
                  cpuMove = Math.random() < 0.5 ? "d" : "p";//randomly choose to dribble or pass
               }

               //shooting
               if(cpuMove.equals("s")){
                  IO.println("CPU shoots...");
                  
                  //pass relevant values to the shooting formula
                  if(Gameplay.shootBall(cpu, defender, distance, players)){
                     IO.println("CPU MAKES IT!");
                     cpuScore += 2;
                  }
                  else{
                     IO.println("CPU misses.");
                  }
                  ballCarrier -= 5;
                  possessionActive = false;
               }

               //passing
               else if(cpuMove.equals("p")){
                  
                  //cpu random pass to anouther cpu player
                  int target = 5 + new Random().nextInt(5);
                  if(target == cpu){
                     target = 5;
                  }
                  IO.println("CPU passes to " + target);
                  
                  //pass relevant values to the passing formula
                  if(Gameplay.passBall(cpu, defender, distance, target, players)){
                     ballCarrier = target;
                  }
                  else{
                     IO.println("USER steals the pass!");
                     ballCarrier = defender;
                     possessionActive = false;
                  }
               }

               //dribbling
               else{
                  IO.println("CPU dribbles...");
                  
                  //pass relevant values to the dribbing formula
                  if(Gameplay.dribbleBall(cpu, defender, distance, players)){
                     IO.println("CPU moves closer.");
                     if(distance > 0){
                        distance--;
                     }
                  }
                  else{
                     IO.println("USER steals the dribble!");
                     ballCarrier = defender;
                     possessionActive = false;
                  }
               }
            }//else
         }//while(possessionActive)

         turns--;
         
     }//while(turns > 0)

     IO.println("\n===== GAME OVER =====");
     IO.println("FINAL SCORE:");
     IO.println("USER: " + userScore);
     IO.println("CPU:  " + cpuScore);
   }//main

   //--------------- INPUT + MISC METHODS ---------------
   
   //random number generator 0 - 9
   public static int randNum(){
      return new Random().nextInt(10);
   }
   
   //set user player stats
   public static int setUserStat(String stat){
      IO.print(stat + " (1 - 9): ");
      return intInput();
   }
   
   //randomly set cpu player stats 1 - 9
   public static int setCPUStat(){
      return new Random().nextInt(9) + 1;
   }
   
   //enter number of turns and ensure it's a valid input
   public static int turnInput(){
      IO.print("Enter number of turns: ");
      while (true) {
      if (console.hasNextInt()){
         int x = console.nextInt();
         if (x > 0) return x;
      }
      console.nextLine();
      IO.print("Invalid. Enter positive number: ");
      }
   }
   
   //ensure valid integer is 1 - 9 is entered
   public static int intInput(){
      while(true){
         if(console.hasNextInt()){
            int x = console.nextInt();
            if(x >= 1 && x <= 9){
               return x;
            }
         }
         console.nextLine();
         IO.print("Invalid. Enter (1 - 9): ");
      }
   }
   
   //ensure pass is to a valid teammate
   public static int passInput(int carrier){
      while(true){
         if(console.hasNextInt()){
            int x = console.nextInt();
            if(x >= 0 && x <= 4 && x != carrier){
               return x;
            }
         }
         console.nextLine();
         IO.print("Invalid. Enter (0 - 4) except " + carrier + ": ");
      }
   }
   
   //ensure shoot, pass, or dribble input is valid
   public static String charInput(){
      while(true){
         String s = console.next().toLowerCase();
         if(s.equals("s") || s.equals("p") || s.equals("d")){
            return s;
         }
         IO.print("Invalid. Enter (s/p/d): ");
      }
   }
   
   //set distance from hoop 4 - 6
   public static int setDistance(){
      return new Random().nextInt(3) + 4;
   }
}
