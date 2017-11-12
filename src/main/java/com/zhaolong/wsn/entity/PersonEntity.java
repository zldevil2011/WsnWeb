package com.zhaolong.wsn.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_person")
public class PersonEntity implements java.io.Serializable {
	private static final long serialVersionUID = -4376187124011546736L;

	private Integer id;
	private String username;
	private String password;
	
	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@Column(length = 50 , nullable = false , unique = true)
	public String getName() {
		return username;
	}
	public void setName(String name) {
		this.username = name;
	}
	@Column(length = 20 , nullable = false , unique = false)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "PersonEntity [id=" + id + ", username=" + username + "]";
	}
}
