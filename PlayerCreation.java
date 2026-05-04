public class PlayerCreation{
   
   //iniitalize attributes
   public int shooting;
   public int passing;
   public int dribbling;
   public int block;
   public int steal;
   public int speed;
   
   //create player with given stats
   public PlayerCreation(int shooting, int passing, int dribbling, int block, int steal, int speed){
      this.shooting = shooting;
      this.passing = passing;
      this.dribbling = dribbling;
      this.block = block;
      this.steal = steal;
      this.speed = speed;
   }
   
   //print each player's stats
   public static String toString(String team, int num, int shooting, int passing, int dribbling,
   int block, int steal, int speed){
      return team + " Player " + num + ": [SH " + shooting + ", PA " + passing
                + ", DR " + dribbling + ", BL " + block + ", ST " + steal + ", SP " + speed + "]";
   }
}
