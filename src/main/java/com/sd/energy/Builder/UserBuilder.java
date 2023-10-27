package com.sd.energy.Builder;

import com.sd.energy.Model.User;
import com.sd.energy.Model.UserProfile;

public class UserBuilder {

    public String username;
    public String password;
    public boolean admin;
    public UserProfile userProfile;

    public UserBuilder()
    {

    }

    public UserBuilder withUsername(String username)
    {
        this.username = username;
        return this;
    }

    public UserBuilder withPassword(String password)
    {
        this.password = password;
        return this;
    }

    public UserBuilder withAdmin(boolean admin)
    {
        this.admin = admin;
        return this;
    }

    public UserBuilder withUserProfile(UserProfile userProfile)
    {
        this.userProfile = userProfile;
        return this;
    }

    public User build()
    {
        return new User(this);
    }
}
