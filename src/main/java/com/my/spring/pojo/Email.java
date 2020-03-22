package com.my.spring.pojo;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "email_table")
public class Email {
	
        
        
     @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name= "uuid", strategy = "uuid2",parameters = @Parameter(name = "property", value = "user"))
    @Column(name = "emailID", unique=true, nullable = false)
	private String id;
        
        

	@Column(name = "email_address")
	private String emailAddress;

	@OneToOne
	@PrimaryKeyJoinColumn
	private User user;

	public Email() {
            this.id = UUID.randomUUID().toString();
	}

	public Email(String emailAddress) {
		this.emailAddress = emailAddress;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}



}
