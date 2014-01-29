package fr.persistence.copy.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue
    private String id;

    @Column(name = "NAME")
    private String name;

    @ManyToOne(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
    private Account account;

    @ManyToOne(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
    private Currency currency;

    @Version
    @Column(name = "VERSION")
    private Integer version;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
