import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class OptimalScheduler implements IPageReplacementScheduler {
    private int capacity;
    private LinkedList<Integer> queue = new LinkedList<>();
    private List<Integer> pagesToReference;
    private int indexOfCurrentPage = 0;

    public OptimalScheduler(int capacity, List<Integer> pagesToReference) {
        this.capacity = capacity;
        this.pagesToReference = pagesToReference;
    }

    @Override
    public boolean reference(int page) {
        boolean status = true;
        if (!this.queue.contains(page)) {
            if (isFull())
                this.popAndReplace(page);
            else
                this.queue.offer(page);
            status = false;
        }
        this.indexOfCurrentPage++;
        return status;

    }

    @Override
    public List<Integer> getCurrentMemoryState() {
        return this.queue;
    }

    @Override
    public void reset() {
        this.queue.clear();
    }

    private void popAndReplace(int page) {
        ArrayList<Integer> indexOf = new ArrayList<>();
        for (var pag : this.queue)
            indexOf.add(this.pagesToReference.subList(this.indexOfCurrentPage, this.pagesToReference.size()).indexOf(pag));
        int max = maxIndex(indexOf);
        queue.set(max, page);

    }

    private static int maxIndex(ArrayList<Integer> list) {
        int maxIndex = 0;
        int maxElement = list.get(0);
        if (maxElement == -1)
            return maxIndex;

        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) == -1)
                return i;
            if (maxElement < list.get(i)) {
                maxElement = list.get(i);
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    private boolean isFull() {
        return this.capacity == this.queue.size();
    }
}
