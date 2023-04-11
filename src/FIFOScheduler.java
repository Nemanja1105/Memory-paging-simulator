
import java.util.Queue;
import java.util.List;
import java.util.LinkedList;

public class FIFOScheduler implements IPageReplacementScheduler
{
    private int capacity;
    private LinkedList<Integer> queue=new LinkedList<>();
    public FIFOScheduler(int capacity)
    {
        this.capacity=capacity;
    }




    //true ako je uspjesno
    //false ako je page fault
    @Override
    public boolean reference(int page)
    {
        boolean status=true;
        if(!this.queue.contains(page)) {
            if (this.isFull())
                this.queue.poll();
            status=false;
            this.queue.offer(page);
        }
        return status;
    }

    @Override
    public List<Integer> getCurrentMemoryState()
    {
        return this.queue;
    }

    private boolean isFull()
    {
        return this.queue.size()==this.capacity;
    }

    @Override
    public void reset()
    {
        this.queue.clear();
    }
}
