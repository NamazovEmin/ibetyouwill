package ru.namazov.ibetyouwill.profile.entity;

import java.util.Date;

import ru.namazov.ibetyouwill.user.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Profile extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "age")
    private int age;

    @Column(name = "address")
    private String address;

    @Column(name = "telnumber")
    private long telNumber;

    @Column(name = "birthday")
    private Date birthday;
}
