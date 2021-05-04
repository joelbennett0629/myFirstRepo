import java.util.Scanner;

public class WinRateGrabber {
    public static void main(String[] args) throws Exception{
        Scanner reader = new Scanner(System.in);

        System.out.print("Enter a Summoner name: ");
        String name = reader.nextLine();

        String sumID = GrabData.getSumID(name);

        int[][] champIDs = GrabData.parseMatch(GrabData.getMatch(sumID));

        int[] blueSideChamps = new int[5];
        int[] purpleSideChamps = new int[5];
        double blueSideWin = 0.0;
        double purpleSideWin = 0.0;

        for(int i = 0; i < 5; i++){
            blueSideChamps[i] = champIDs[0][i];
        }
        for(int i = 0; i < 5; i++){
            purpleSideChamps[i] = champIDs[1][i];
        }

        for(int i = 0; i < 5; i++){
            blueSideWin += ChampionData.getWinRateByChamp(ChampionData.getChampName(blueSideChamps[i]));
            purpleSideWin += ChampionData.getWinRateByChamp(ChampionData.getChampName(purpleSideChamps[i]));
        }

        blueSideWin /= 5;
        purpleSideWin /= 5;

        System.out.print("Blue Side has a ");
        System.out.printf("%.2f", blueSideWin);
        System.out.println("% Win Rate.");
        System.out.print("Purple Side has a ");
        System.out.printf("%.2f", purpleSideWin);
        System.out.println("% Win Rate.");
    }
}
