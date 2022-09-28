import java.util.*;

public class Main {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";


    public static void main(String[] args) throws InterruptedException {

        Scanner in = new Scanner(System.in);
        int[] seq = {7,7,7,7,7,7,7,2,3,4,5};
//        ArrayList<Integer> seq = new ArrayList<>();
////        Collections.addAll(seq, a);
//        System.out.println("Press '-1' to end page sequence");
//        while(true){
//            System.out.println(ANSI_RED + "Enter page number: ");
//            int p = in.nextInt();
//            if(p == -1) break;
//            seq.add(p);
//        }
        System.out.print(ANSI_RED + "Enter frame size: " + ANSI_RESET);
        int f = in.nextInt(), pf = 0;

        Deque<Integer> recent = new LinkedList<>();
        int lru = 0, p = 0;
        int[][] display = new int[f][seq.length];
        char[] fault = new char[seq.length];
        Arrays.fill(fault, '-');

        for(int i: seq){

            if(!recent.contains(i)){
                pf++;
                fault[p] = '*';
                if(recent.size() == f){
                    recent.remove(lru);
                }
            }else{
                recent.remove(i);
            }
            recent.addLast(i);
            lru = recent.getFirst();

            Deque<Integer> temp = new LinkedList<>();
            int s = recent.size();
            for (int j = 0; j < s; j++) {
                display[j][p] = recent.getFirst();
                temp.addLast(display[j][p]);
                recent.removeFirst();
            }
            s = temp.size();
            for (int j = 0; j < s; j++) {
                recent.addLast(temp.getFirst());
                temp.removeFirst();
            }
            p++;
        }
        System.out.println(ANSI_GREEN);
        for(int i : seq) System.out.print(" " + i);
        System.out.println();
        for (int i = 0; i < seq.length; i++) System.out.print("  ");
        System.out.println(ANSI_RESET);
        for (int i = 0; i < seq.length; i++){
            Thread.sleep(100);
            System.out.print(" ↓");
        }
        System.out.println(ANSI_CYAN);
        for (int i = 0; i < seq.length; i++) System.out.print("  ");
        System.out.println();
        for (int i = 0; i < f; i++) {
            Thread.sleep(100);
            for (int j = 0; j < seq.length; j++) {
                System.out.print(" " + display[i][j]);
            }
            System.out.println();
        }
        for (int i = 0; i < seq.length; i++){
            Thread.sleep(100);
            System.out.print(ANSI_RESET+"__");
        }
        System.out.print("_");
        System.out.println(ANSI_RED);
        for(char c : fault){
            Thread.sleep(300);
            System.out.print(" " + c);
        }
        System.out.println();
        System.out.println();

        System.out.println(ANSI_YELLOW + pf + " Page Faults");
    }
}
