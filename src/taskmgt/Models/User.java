/**
 * Jan 25, 2014
 * Aron Aperauch
 * ISM 6257, Section 0305 - Fall 2013
 * Dr. Seema Bandyopadhyay
 *
 * User.java
 */
package taskmgt.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public abstract class User implements Serializable, Comparable<User> {
    //Properties
    protected String name;
    protected String email;
    protected String password;
    protected boolean active;
    private final ModelType type = ModelType.User;

    //Constructors
    public User(){};
     
    public User(String name, String email, String password, boolean active){
        this.name=name;
        this.email=email;
        this.password=password;
        this.active=active;
    }
   
    //Get
    public String getName() { return this.name; }
    public String getEmail() { return this.email; }
    public String getPassword(){return this.password;}
    public boolean checkActive(){return this.active;}
    public ModelType getType() { return this.type; }

    //Set
    public void setName(String name){ this.name=name;}
    public void setEmail(String email){ this.email=email;}
    public void setPassword(String password){ this.password=password;}
    public void setActive(boolean active){this.active=active;}
    
    //Methods
    @Override
    public boolean equals(Object obj){
        if(obj instanceof User){
            User userObj= (User) obj;
            return userObj.getEmail().toLowerCase().equals(this.email.toLowerCase());
        }
        return false;
    }
    
    @Override
    public int compareTo(User u)
    {
        return this.email.compareToIgnoreCase(u.email);
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.email);
        return hash;
    }

    public String[] toStringArray() {
        ArrayList<String> attrs = new ArrayList<>();

        attrs.add(name);
        attrs.add(email);
        attrs.add(password);
        attrs.add(String.valueOf(active));
        attrs.add(type.name());

        return attrs.toArray(new String[attrs.size()]);
    }
    
    @Override
    public String toString() {
        
        return(name + " (" + email + ")" );
 }
}
 