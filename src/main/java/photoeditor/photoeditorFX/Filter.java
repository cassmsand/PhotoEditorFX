package photoeditor.cassiephotoeditorFX;

public abstract class Filter  {
    private String filterName;

    public Filter(String name) {
        this.filterName = name;
    }

    public String getName() {
        return filterName;
    }

    public void setName(String name) {
        this.filterName = name;
    }

    public abstract boolean applyFilter();
}