package photoeditor.photoeditorFX;

public class MakeSmallerFilter extends Filter {

    private int height; //desired height
    private int width; //desired width

    public MakeSmallerFilter(String name){ super(name); }

    public int getHeight(){ return this.height; }

    public int getWidth(){ return this.width; }

    public void setHeight(int height){ this.height = height;}

    public void setWidth(int width){ this.width = width; }

    public boolean applyFilter(){ return true; };
}