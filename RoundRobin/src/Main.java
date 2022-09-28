import java.util.*;

public class Main {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";

    public static void main(String[] args) throws InterruptedException {

        Scanner in = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = in.nextInt();
        System.out.print("Enter the quantum time: ");
        int q = in.nextInt();

        int[] at = new int[n], bt = new int[n], ct = new int[n], wt = new int[n], tat = new int[n];
        TreeMap<Integer, ArrayList<Integer>> tm = new TreeMap<>();

        System.out.println("Enter arrival time and burst time: ");
        for (int i = 0; i < n; i++) {
            System.out.println("P" + i);
            System.out.print("\tAT = ");
            at[i] = in.nextInt();
            System.out.print("\tBT = ");
            bt[i] = in.nextInt();

            tm.putIfAbsent(at[i], new ArrayList<>()); // map to add process in ready state when
            tm.get(at[i]).add(i);                     // it arrives
        }

        int[] rem = Arrays.copyOf(bt, n);
        Queue<Integer> readyState = new LinkedList<>();
        ArrayList<Integer> gc = new ArrayList<>(), tgc = new ArrayList<>();

        int t = 0, done = 0;
        int prevT = 0, nextT = 0;
        while(done < n){
            for (int i = prevT; i <= t ; i++) {
                if(tm.containsKey(i)){
                    readyState.addAll(tm.get(i));
                    tm.remove(i);
                }
            }
            prevT = t;
            if(!readyState.isEmpty()) {      // possibility that at a given time readyState is empty
                int cur = readyState.poll();
                gc.add(cur);
                tgc.add(t);
                if (rem[cur] <= q) {
                    t += rem[cur];
                    rem[cur] -= rem[cur];
                    ct[cur] = t;
                    tat[cur] = ct[cur] - at[cur];
                    wt[cur] = tat[cur] - bt[cur];
                    done++;
                } else {
                    rem[cur] -= q;
                    t += q;
                    for (int i = prevT; i <= t ; i++) {
                        if(tm.containsKey(i)){
                            readyState.addAll(tm.get(i));
                            tm.remove(i);
                        }
                    }
                    readyState.add(cur);
                }
            }else t++;
        }

        int tatAvg = 0, wtAvg = 0;
        for (int i = 0; i < n; i++) {
            tatAvg += tat[i];
            wtAvg += wt[i];
        }

        Formatter fmt = new Formatter();
        fmt.format(ANSI_YELLOW+"%106s",
                "-------------------------------------------------------------------------------------------------------------------\n");
        fmt.format(ANSI_BLUE+"|%11s |%18s |%18s |%18s |%18s |%18s  |\n", "Process No.", "Arrival Time",
                "Burst Time"
                , "Completion Time", "Turn Around Time", "Waiting Time");
        fmt.format("|%11s |%18s |%18s |%18s |%18s |%18s  |\n", "(PId)", "(AT)", "(BT)"
                , "(CT)", "(TAT)", "(WT)");
        fmt.format(ANSI_YELLOW+"%106s",
                "-------------------------------------------------------------------------------------------------------------------\n");
        for (int i = 0; i < n; i++) {
            fmt.format("|%11s |%18s |%18s |%18s |%18s |%18s  |\n", "P"+i, at[i], bt[i],
                    ct[i],
                    tat[i],
                    wt[i]);
        }
        fmt.format("%106s",
                "-------------------------------------------------------------------------------------------------------------------\n");
        System.out.println(fmt);

        System.out.printf(ANSI_RED+"TAT average = %.3f\n", tatAvg/(float)n);
        System.out.printf("WT average = %.3f", wtAvg/(float)n);


        System.out.println(ANSI_RESET+"\n\n**GANTT CHART**\n");
        System.out.print(ANSI_CYAN+"_");
        for (int i = 0; i < gc.size(); i++){
            Thread.sleep(200);
            System.out.print("_______");
        }
        System.out.println();
        for (int i = 0; i < gc.size(); i++){
            Thread.sleep(500);
            System.out.print("|  P"+gc.get(i)+"  ");
        }
        System.out.println("|");
        System.out.print("-");
        for (int i = 0; i < gc.size(); i++){
            Thread.sleep(200);
            System.out.print("-------");
        }
        System.out.println();
        for (int i = 0; i < tgc.size(); i++){
            System.out.print(tgc.get(i));
            Thread.sleep(500);
            if(tgc.get(i) < 10) System.out.print("      ");
            else System.out.print("     ");
        }
        int sum = 0;
        for(int i : bt) sum += i;
        System.out.println(sum);

        System.out.println("\n\n**THANK YOU**");





//        int n,i,qt,count=0,temp,sq=0,bt[],wt[],tat[],rem_bt[];
//        float awt=0,atat=0;
//        bt = new int[10];
//        wt = new int[10];
//        tat = new int[10];
//        rem_bt = new int[10];
//        Scanner s=new Scanner(System.in);
//        System.out.print("Enter the number of process (maximum 10) = ");
//        n = s.nextInt();
//        System.out.print("Enter the burst time of the process\n");
//        for (i=0;i<n;i++)
//        {
//            System.out.print("P"+i+" = ");
//            bt[i] = s.nextInt();
//            rem_bt[i] = bt[i];
//        }
//        System.out.print("Enter the quantum time: ");
//        qt = s.nextInt();
//        while(true)
//        {
//            for (i=0,count=0;i<n;i++)
//            {
//                temp = qt;
//                if(rem_bt[i] == 0)
//                {
//                    count++;
//                    continue;
//                }
//                if(rem_bt[i]>qt)
//                    rem_bt[i]= rem_bt[i] - qt;
//                else
//                if(rem_bt[i]>=0)
//                {
//                    temp = rem_bt[i];
//                    rem_bt[i] = 0;
//                }
//                sq = sq + temp;
//                tat[i] = sq;
//            }
//            if(n == count)
//                break;
//        }
//        System.out.print("--------------------------------------------------------------------------------");
//        System.out.print("\nProcess\t      Burst Time\t       Turnaround Time\t          Waiting Time\n");
//        System.out.print("--------------------------------------------------------------------------------");
//        for(i=0;i<n;i++)
//        {
//            wt[i]=tat[i]-bt[i];
//            awt=awt+wt[i];
//            atat=atat+tat[i];
//            System.out.print("\n "+(i+1)+"\t "+bt[i]+"\t\t "+tat[i]+"\t\t "+wt[i]+"\n");
//        }
//        awt=awt/n;
//        atat=atat/n;
//        System.out.println("\nAverage waiting Time = "+awt+"\n");
//        System.out.println("Average turnaround time = "+atat);
    }
}

