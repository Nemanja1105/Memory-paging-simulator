import java.util.ArrayList;

public class VirtualMemorySimulator {
    public static final int PAGE_FAULT = -1;
    public static final int PAGE_FAULT_NONE = 0;
    private int numOfFrames;
    private ArrayList<Integer> pageToReference = new ArrayList<>();
    private IPageReplacementScheduler scheduler;
    private MemoryMatrix matrix;

    public VirtualMemorySimulator(IPageReplacementScheduler scheduler, int numOfFrames, ArrayList<Integer> pageToReference) {
        this.scheduler = scheduler;
        this.numOfFrames = numOfFrames;
        this.pageToReference = pageToReference;
        this.matrix = new MemoryMatrix(pageToReference.size(), numOfFrames);
    }

    public void simulate() {
        for (var page : this.pageToReference) {
            boolean status = this.scheduler.reference(page);
            int pfStatus = (status) ? PAGE_FAULT_NONE : PAGE_FAULT;
            matrix.addColumn(page, pfStatus, this.scheduler.getCurrentMemoryState());
        }
        int numOFPF = matrix.numOfPageFault();
        double procent = ((double) numOFPF / this.pageToReference.size()) * 100;
        System.out.println("Rezultat simulacije:");
        System.out.println(matrix.toString());
        System.out.println("Efikasnost algoritma: PF = " + numOFPF + "  =>  pf = " + numOFPF + " / "
                + this.pageToReference.size() + " = " + String.format("%3.2f", procent) + "%");
    }

    public void reset() {
        this.matrix = new MemoryMatrix(pageToReference.size(), numOfFrames);
        this.scheduler.reset();
    }
}
