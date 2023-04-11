import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numOfFrames;
        do {
            System.out.print("Unesite broj okvira:");
            numOfFrames = scanner.nextInt();
        }
        while (numOfFrames <= 0);

        int n;
        do {
            System.out.print("Unesite broj referenci:");
            n = scanner.nextInt();
        }
        while (n <= 0);

        scanner.skip("\r\n|\n");
        ArrayList<Integer> pages = new ArrayList<>();
        while (true) {
            System.out.print("Unesite reference:");
            String ref = scanner.nextLine();
            String[] arg = ref.split(",");
            if (arg.length != n) {
                System.out.println("Neispravan broj referenci!!");
                continue;
            }
            try {
                for (var str : arg)
                    pages.add(Integer.parseInt(str.trim()));
            } catch (NumberFormatException e) {
                System.out.println("Neispravan format broja!!");
                pages.clear();
                continue;
            }
            break;
        }
        String alg = "";
        System.out.println("Dostupni algoritmi:FIFO, LRU, SECONDCHANCE, LFU, OPTIMAL");
        while (true) {
            System.out.print("Unesite naziv algoritma:");
            alg = scanner.nextLine();
            if (!alg.equals("FIFO") && !alg.equals("LFU") && !alg.equals("LRU") &&
                    !alg.equals("SecondChance") && !alg.equals("OPTIMAL"))
                System.out.println("Trazeni algoritam ne postoji!!");
            else
                break;
        }
        IPageReplacementScheduler scheduler = null;
        if ("FIFO".equals(alg))
            scheduler = new FIFOScheduler(numOfFrames);
        else if ("LRU".equals(alg))
            scheduler = new LRUScheduler(numOfFrames);
        else if ("SECONDCHANCE".equals(alg)) {
            ArrayList<Integer> list = new ArrayList<>();
            while (true) {
                System.out.print("Unesite stranice koje imaju drugu sansu:");
                String temp = scanner.nextLine();
                String[] arg = temp.split(",");
                try {
                    for (var str : arg)
                        list.add(Integer.parseInt(str.trim()));
                } catch (NumberFormatException e) {
                    System.out.println("Neispravan format broja!!");
                    list.clear();
                    continue;
                }
                break;
            }
            scheduler = new SecondChanceScheduler(numOfFrames, list);
        } else if ("LFU".equals(alg)) {
            int inc = 0, dec = 0, startValue = 0;
            do {
                System.out.print("Unesite inkrement:");
                inc = scanner.nextInt();
            }
            while (inc <= 0);

            do {
                System.out.print("Unesite dekrement:");
                dec = scanner.nextInt();
            }
            while (dec <= 0);

            do {
                System.out.print("Unesite pocetnu vrijednost stranica:");
                startValue = scanner.nextInt();
            }
            while (startValue <= 0);
            scheduler = new LFUScheduler(numOfFrames, inc, dec, startValue);
        } else if ("OPTIMAL".equals(alg))
            scheduler = new OptimalScheduler(numOfFrames, pages);
        VirtualMemorySimulator simulator = new VirtualMemorySimulator(scheduler, numOfFrames, pages);
        simulator.simulate();
    }
}
