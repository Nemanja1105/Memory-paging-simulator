import java.util.LinkedList;
import java.util.List;

public class LRUScheduler implements IPageReplacementScheduler
{
    private LinkedList<Integer> queue=new LinkedList<>();
    private int capacity;
    public LRUScheduler(int capacity)
    {
        this.capacity=capacity;
    }

    @Override
    public boolean reference(int page)
    {
        boolean status=true;
        if(!this.queue.contains(page))
        {
            if(this.isFull())
                this.queue.poll();
            status=false;
        }
        else
            this.queue.remove(Integer.valueOf(page));
        this.queue.offer(page);
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

    private boolean isFull() {
        return this.capacity == queue.size();
    }

}
