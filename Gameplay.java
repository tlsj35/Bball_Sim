import java.util.*;

public class Gameplay{

   static Random r = new Random();

   //shot attempt formula
   public static boolean shootBall(int shooter, int defender, int distance, PlayerCreation[] players){

      PlayerCreation o = players[shooter];
      PlayerCreation d = players[defender];

      //sets min and max odds to 10% and 90%, regardless of stats
      int base = 50 + (o.shooting * 4) - (d.block * 3) - (distance * 10);
      if(base < 10){
         base = 10;
      }
      if(base > 90){
         base = 90;
      }

      int roll = r.nextInt(100);// 0 - 99
      IO.println("Shot chance: " + base + "% (rolled " + roll + ")");

      return roll < base;//if roll < base, attempt failed
   }

   //pass attempt formula
   public static boolean passBall(int passer, int defender, int distance, int target,
   PlayerCreation[] players){

      PlayerCreation p = players[passer];
      PlayerCreation d = players[defender];
         
      //sets min and max odds to 10% and 90%, regardless of stats
      int base = 65 + (p.passing * 3) - (d.steal * 4) - (distance * 5);
      if(base < 10){
         base = 10;
      }
      if(base > 90){
         base = 90;
      }

      int roll = r.nextInt(100);// 0 - 99
      IO.println("Pass chance: " + base + "% (rolled " + roll + ")");

      return roll < base;//if roll < base, attempt failed
   }

   //dribble attempt formula
   public static boolean dribbleBall(int dribbler, int defender, int distance,
   PlayerCreation[] players){

      PlayerCreation o = players[dribbler];
      PlayerCreation d = players[defender];
        
      //sets min and max odds to 10% and 90%, regardless of stats
      int base = 60 + (o.dribbling * 4) - (d.speed * 4);
      if(base < 10){
         base = 10;
      }
      if(base > 90){
         base = 90;
      }

      int roll = r.nextInt(100);// 0 - 99
      IO.println("Dribble chance: " + base + "% (rolled " + roll + ")");

      return roll < base;//if roll < base, attempt failed
   }
}
