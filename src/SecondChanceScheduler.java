import java.util.LinkedList;
import java.util.List;

public class SecondChanceScheduler implements IPageReplacementScheduler
{
    private LinkedList<Pair<Integer,Boolean>> queue=new LinkedList<>();
    private int capacity;
    private List<Integer> pagesWithSecondChance;

    public SecondChanceScheduler(int capacity,List<Integer> pagesWithSecondChance)
    {
        this.capacity=capacity;
        this.pagesWithSecondChance=pagesWithSecondChance;
    }

    @Override
    public boolean reference(int page) {
        boolean status=true;
        if(!this.queue.contains(Pair.makePair(page,true)))
        {
            if(this.isFull())
                this.pop();
            status=false;
            if(this.pagesWithSecondChance.contains(page))
                this.queue.offer(Pair.makePair(page,true));
            else
                this.queue.offer(Pair.makePair(page,false));
        }
        else
        {
            if(this.pagesWithSecondChance.contains(page))
            {
                var pair= this.queue.get(this.queue.indexOf(Pair.makePair(page, true)));
                pair.second=true;
            }
        }
        return status;
    }

    private void pop()
    {
        var temp=this.queue.peekFirst();
        if(temp.second==true)
        {
            this.queue.poll();
            this.queue.poll();
            temp.second=false;
            this.queue.offer(temp);
        }
        else
            this.queue.poll();
    }

    private boolean isFull()
    {
        return this.capacity==queue.size();
    }

    @Override
    public List<Integer> getCurrentMemoryState() {
        LinkedList<Integer> list=new LinkedList<>();
        for(var temp:this.queue)
            list.add(temp.first);
        return list;
    }

    @Override
    public void reset() {
        this.queue.clear();
    }
}
