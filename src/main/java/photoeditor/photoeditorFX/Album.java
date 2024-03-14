package photoeditor.photoeditorFX;

public class Album {

    private String albumName;
    private final UserPhoto[] photoAlbum;
    private int photoCount;
    private int count = 0;

    public Album(String albumName) {
        this.albumName = albumName;
        this.photoAlbum = new UserPhoto[100]; // Assuming a maximum of 100 photos
        this.photoCount = 0;
    }

    public Album(String albumName, UserPhoto photo) {
        this.albumName = albumName;
        this.photoAlbum = new UserPhoto[100];
        count++;
        this.photoCount = count;
        this.photoAlbum[0] = photo;
    }

    public String getAlbumName() {
        return albumName;
    }

    public UserPhoto[] getPhotoAlbum() {
        return photoAlbum;
    }

    public int getPhotoCount() {
        return photoCount;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public UserPhoto grabPhoto(int photoNum) {
        if (photoNum >= 0 && photoNum < photoCount) {
            return photoAlbum[photoNum];
        } else {
            System.out.println("Photo not found");
            return null;
        }
    }

    public void addPhoto(UserPhoto newPhoto) {
        if (photoCount < photoAlbum.length) {
            photoAlbum[photoCount] = newPhoto;
            photoCount++;
        } else {
            System.out.println("Album is full");
        }
    }

    public boolean deletePhoto(UserPhoto photoToDelete) {
        for (int i = 0; i < photoCount; i++) {
            if (photoAlbum[i] == photoToDelete) {
                photoAlbum[i] = photoAlbum[photoCount - 1];
                photoCount--;
                return true;
            }
        }
        System.out.println("Photo not found");
        return false;
    }
}


