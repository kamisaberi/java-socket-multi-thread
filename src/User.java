import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class User implements Serializable {


    public static ArrayList<User> users;

    public static User login(String email, String password) {
        int ind;
        if ((ind = User.users.indexOf(new User(email, password, "", "", "", null))) != -1) {
            return User.users.get(ind);
        } else {
            return null;
        }
    }


    static {
        users = new ArrayList<>();
        users.add(new User("a@s.com" , "A1234" , "ali" , "0000000000" , "" , null));
        users.add(new User("b@s.com" , "A1234" , "reza" , "0000000000" , "" , null));
        users.add(new User("c@s.com" , "A1234" , "ahmad" , "0000000000" , "" , null));
        users.add(new User("d@s.com" , "A1234" , "hassan" , "0000000000" , "" , null));
    }

    private String email;
    private String password;
    private String username;
    private String phoneNumber;
    private String bio;
    private Date birthDate;
    private ArrayList<Post> posts = new ArrayList<>();
    private ArrayList<User> followers = new ArrayList<>();
    private ArrayList<User> followings = new ArrayList<>();

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public ArrayList<User> getFollowers() {
        return followers;
    }

    public void setFollowers(ArrayList<User> followers) {
        this.followers = followers;
    }

    public ArrayList<User> getFollowings() {
        return followings;
    }

    public void setFollowings(ArrayList<User> followings) {
        this.followings = followings;
    }

    public User(String email, String password, String username, String phoneNumber, String bio, Date birthDate) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.bio = bio;
        this.birthDate = birthDate;
    }

    // Add getters and setters here

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }


    @Override
    public boolean equals(Object obj) {
        User user = (User) obj;
        return this.email.equals(user.email) && this.password.equals(user.password);
    }
}