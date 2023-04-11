
import java.util.List;

public interface IPageReplacementScheduler
{
    boolean reference(int page);
    List<Integer> getCurrentMemoryState();
    void reset();
}
