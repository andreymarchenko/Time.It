package andrey.timeit.model;

/**
 * Created by Andrey on 14.07.2016.
 */
public class ModelProfile {
    private String login;
    private String password;
    private String name;
    private String activity;
    private String age;
    private String gender;
    private String weight;
    private String growth;
    private String email;

    public ModelProfile() {
        login = " ";
        password = " ";
        name = " ";
        activity = " ";
        age = " ";
        gender = " ";
        weight = " ";
        growth = " ";
        email = " ";
    }

    public ModelProfile(String login, String password, String name, String activity, String age,
                 String gender, String weight, String growth, String email) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.activity = activity;
        this.age = age;
        this.gender = gender;
        this.weight = weight;
        this.growth = growth;
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getGrowth() {
        return growth;
    }

    public void setGrowth(String growth) {
        this.growth = growth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
