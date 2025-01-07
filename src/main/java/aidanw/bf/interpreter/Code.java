package aidanw.bf.interpreter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Code {
    private String id;
    final private String inputCode;
    @JsonProperty("input") final private String input;
    private String outputCode;
    private final String language;

    public Code(String id, String inputCode, String input, String language) {
        this.id = id;
        this.inputCode = inputCode;
        this.input = input;
        this.language = language;
    }

    public Code(@JsonProperty("inputCode") String inputCode, @JsonProperty("input") String input, @JsonProperty("language") String language) {
        this.inputCode = inputCode;
        this.input = input;
        this.language = language;
    }

    public String getLox() {
        return language;
    }

    public String getInput() {
        return input;
    }

    public void setOutputCode(String outputCode) {
        this.outputCode = outputCode;
    }

    public String getOutputCode() {
        return outputCode;
    }

    public String getInputCode() {
        return inputCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
