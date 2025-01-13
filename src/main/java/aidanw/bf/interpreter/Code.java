package aidanw.bf.interpreter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
@Entity
@Table(name="CODE")
public class Code {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    private User user;

    private String inputCode;
    private String input;
    private String outputCode;
    private String language;


    public Code() {

    }

    public Code(@JsonProperty("inputCode") String inputCode, @JsonProperty("input") String input, @JsonProperty("language") String language) {
        this.inputCode = inputCode;
        this.input = input;
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public String getInput() {
        return input;
    }

    public void setOutputCode(String outputCode) {
        this.outputCode = outputCode;
    }

    public void setUser(User user) {
        this.user = user;
        if (!user.getCodes().contains(this)) { // warning this may cause performance issues if you have a large data set since this operation is O(n)
            user.getCodes().add(this);
        }
    }
    public String getOutputCode() {
        return outputCode;
    }

    public String getInputCode() {
        return inputCode;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

}
