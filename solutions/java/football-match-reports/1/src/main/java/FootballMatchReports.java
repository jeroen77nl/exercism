public class FootballMatchReports {
    public static String onField(int shirtNum) {
        String s = "";

        switch (shirtNum) {
            case 1:
                s = "goalie";
                break;
            case 2:
                s = "left back";
                break;
            case 3:
            case 4:
                s = "center back";
                break;
            case 5:
                s = "right back";
                break;
            case 6:
            case 7:
            case 8:
                s = "midfielder";
                break;
            case 9:
                s = "left wing";
                break;
            case 10:
                s = "striker";
                break;
            case 11:
                s = "right wing";
                break;
            default:
                s = "invalid";
                break;
        }

        return s;
    }
}
