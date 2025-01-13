package aidanw.bf.interpreter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="USERS")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique = true)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    final private List<Code> codes = new ArrayList<>();

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
    public void addCode(Code code) {
        this.codes.add(code);
        if (code.getUser() != this) {
            code.setUser(this);
        }
    }

    public List<Code> getCodes() {
        return codes;
    }

}
