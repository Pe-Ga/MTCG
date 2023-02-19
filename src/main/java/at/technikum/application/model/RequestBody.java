package at.technikum.application.model;

public class RequestBody {

    public String Name;
    public String Bio;
    public String Image;

    public RequestBody(){};

    public RequestBody(String name, String bio, String image) {
        Name = name;
        Bio = bio;
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getBio() {
        return Bio;
    }

    public void setBio(String bio) {
        Bio = bio;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
