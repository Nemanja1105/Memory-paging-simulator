import java.util.*;

public class LFUScheduler implements IPageReplacementScheduler {
    private int capacity;
    private LinkedList<Pair<Integer, Integer>> priorityQueue = new LinkedList<>();
    private int increment;
    private int decrement;
    private int startValue;

    public LFUScheduler(int capacity, int increment, int decrement, int startValue) {
        this.capacity = capacity;
        this.increment = increment;
        this.decrement = decrement;
        this.startValue = startValue;
    }

    @Override
    public boolean reference(int page) {
        boolean status = true;
        if (!this.priorityQueue.contains(Pair.makePair(page, 0))) {
            if (this.isFull())
                this.priorityQueue.poll();
            status = false;
            this.priorityQueue.offer(Pair.makePair(page, this.startValue));
        } else {
            var pair = this.priorityQueue.get(this.priorityQueue.indexOf(Pair.makePair(page, 0)));
            pair.second += this.increment;
        }
        for (var pair : this.priorityQueue)
            if (!pair.equals(Pair.makePair(page, 0)))
                pair.second -= this.decrement;

        //sortiranje
        Collections.sort(this.priorityQueue, new Comparator<Pair<Integer, Integer>>() {
            @Override
            public int compare(Pair<Integer, Integer> o1, Pair<Integer, Integer> o2) {
                return o1.second.compareTo(o2.second);
            }
        });
        return status;
    }

    @Override
    public void reset() {
        this.priorityQueue.clear();
    }

    @Override
    public List<Integer> getCurrentMemoryState() {
        LinkedList<Integer> temp = new LinkedList<>();
        for (var pair : this.priorityQueue)
            temp.add(pair.first);
        return temp;
    }

    private boolean isFull() {
        return this.capacity == this.priorityQueue.size();
    }
}
