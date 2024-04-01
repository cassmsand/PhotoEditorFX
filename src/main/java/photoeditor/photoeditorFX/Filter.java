package photoeditor.photoeditorFX;

import java.nio.file.attribute.UserPrincipal;

public abstract class Filter  {
    private UserPhoto photo;
    private int intensity;

    public Filter(UserPhoto photo) {
        this.photo = photo;
    }

    public UserPhoto getPhoto() {
        return photo;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    public int getIntensity() {
        return intensity;
    }

    public abstract UserPhoto applyFilter();
}