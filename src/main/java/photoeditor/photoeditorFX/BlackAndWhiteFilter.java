package photoeditor.photoeditorFX;

public class BlackAndWhiteFilter extends Filter{

    private int intensity;

    public BlackAndWhiteFilter(String name){ super(name); }
    public int getIntensity(){ return this.intensity; }
    public void setIntensity(int intensity){ this.intensity = intensity; }
    public boolean applyFilter() { return true; };
}
