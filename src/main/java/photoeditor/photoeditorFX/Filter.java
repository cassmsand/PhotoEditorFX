package photoeditor.photoeditorFX;

import java.awt.image.BufferedImage;
import java.nio.file.attribute.UserPrincipal;

public abstract class Filter  {
    private BufferedImage photo;
    private int intensity;

    public Filter(BufferedImage photo) {
        this.photo = photo;
    }

    public BufferedImage getPhoto() {
        return photo;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    public int getIntensity() {
        return intensity;
    }

    public abstract BufferedImage applyFilter();
}