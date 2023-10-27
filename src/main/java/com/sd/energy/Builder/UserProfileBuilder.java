package com.sd.energy.Builder;

import com.sd.energy.Model.User;
import com.sd.energy.Model.UserProfile;

public class UserProfileBuilder {

    public String name;
    public String description;
    public User user;

    public UserProfileBuilder()
    {

    }

    public UserProfileBuilder withName(String name)
    {
        this.name = name;
        return this;
    }

    public UserProfileBuilder withDescription(String description)
    {
        this.description = description;
        return this;
    }

    public UserProfileBuilder withUser(User user)
    {
        this.user = user;
        return this;
    }

    public UserProfile build()
    {
        return new UserProfile(this);
    }
}
