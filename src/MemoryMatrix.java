
import java.util.List;

public class MemoryMatrix
{
    private int numOfFrames;
    private int[][] matrix;
    private int numOfRows;
    private int numOfColumns;
    private int nextColumn=0;
    public MemoryMatrix(int numOfColumns,int numOfFrames)
    {
        this.numOfFrames=numOfFrames;
        this.numOfColumns=numOfColumns;
        this.numOfRows=this.numOfFrames+2;
        this.matrix=new int[numOfRows][numOfColumns];
    }

    public void addColumn(int page,int pfStatus, List<Integer> args)throws IllegalArgumentException
    {
       /* if(args.size()!=numOfFrames)
            throw new IllegalArgumentException("Broj elemenata je manji od predvidjenog");*/
        int row=0;
        this.matrix[row++][this.nextColumn]=page;
        this.matrix[row++][this.nextColumn]=pfStatus;
        var iterator=args.listIterator(args.size());
        while(iterator.hasPrevious())
            this.matrix[row++][this.nextColumn]= iterator.previous();
        this.nextColumn++;
    }

    public int numOfPageFault()
    {
        int br=0;
        for(int i=0;i<this.numOfColumns;i++)
            if(this.matrix[1][i]==VirtualMemorySimulator.PAGE_FAULT)
                br++;
        return br;
    }

    @Override
    public String toString()
    {
        StringBuilder builder=new StringBuilder();
        for(int i=0;i<this.numOfRows;i++)
        {
            for(int j=0;j<this.numOfColumns;j++)
            {
                if(i==1)
                {
                    if(this.matrix[i][j]==VirtualMemorySimulator.PAGE_FAULT)
                        builder.append(String.format("%-3s ","PF"));
                    else
                        builder.append("    ");
                }
                else {
                    if (this.matrix[i][j] == 0)
                        builder.append("    ");
                    else
                        builder.append(String.format("%-3d ", this.matrix[i][j]));
                }
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
