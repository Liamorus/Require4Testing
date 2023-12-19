import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class UserVerwaltung implements Serializable
{
    private List<User> userMenge = new ArrayList<User>();

    public UserVerwaltung()
    {
        userMenge.add(new User("Admin","admin"));
        userMenge.add(new User("Simon","grete14"));
    }

    public List<User> getUserMenge()
    {
        return userMenge;
    }
}
