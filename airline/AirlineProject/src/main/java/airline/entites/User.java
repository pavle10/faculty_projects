package airline.entites;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name="users")
public class User extends BasicEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private int type;
	private int registred;

	public User() {
	}

	public String getFirstName() { return firstName; }

	public void setFirstName(String firstName) { this.firstName = firstName; }

	public String getLastName() { return lastName; }

	public void setLastName(String lastName) { this.lastName = lastName; }

	public String getUsername() { return username; }

	public void setUsername(String username) { this.username = username; }

	public String getPassword() { return password; }

	public void setPassword(String password) { this.password = password; }

	public int getType() { return type; }

	public void setType(int type) { this.type = type; }

	public int getRegistred() { return registred; }

	public void setRegistred(int registred) { this.registred = registred; }

}