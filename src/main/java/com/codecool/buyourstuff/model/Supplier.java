package com.codecool.buyourstuff.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

@Getter
public class Supplier extends BaseModel {

    private static final int NAME_MINLENGTH = 3;
    private static final int NAME_MAXLENGTH = 60;
    private static final int DESC_MINLENGTH = 5;
    private static final int DESC_MAXLENGTH = 10000;
    private static final String ILLEGAL_CHARS = "'\"\\()[]{}&|?!-;~$<>*%_";

    @NonNull
    private final String name;
    @NonNull
    private final String description;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Supplier(@NonNull @JsonProperty("name") String name, @NonNull @JsonProperty("description") String description) {
        if (StringUtils.containsAny(name, ILLEGAL_CHARS)) {
            throw new IllegalArgumentException("Invalid username - must not contain any of these characters: " + ILLEGAL_CHARS);
        }
        if (!isCreationValid(name, NAME_MINLENGTH, NAME_MAXLENGTH)) {
            throw new IllegalArgumentException("Invalid username - length must be between " + NAME_MINLENGTH + "-" + NAME_MAXLENGTH);
        }
        if (!isCreationValid(description, DESC_MINLENGTH, DESC_MAXLENGTH)) {
            throw new IllegalArgumentException("Invalid description - length must be between " + DESC_MINLENGTH + "-" + DESC_MAXLENGTH);
        }

        this.name = name;
        this.description = replaceIllegalChars(description);
    }

    @Override
    public String toString() {
        return String.format("%1$s={" +
                        "id: %2$d, " +
                        "name: %3$s, " +
                        "description: %4$s}",
                getClass().getSimpleName(),
                id,
                name,
                description
        );
    }

    private boolean isCreationValid(String str, int MIN, int MAX) {
        if (str.length() < MIN) {return false;}
        if (str.length() > MAX) {return false;}
        if (StringUtils.containsAny(str, ILLEGAL_CHARS)) {
            if (MIN == NAME_MINLENGTH && MAX == NAME_MAXLENGTH) { return false; }
        }

        return true;
    }

    private static String replaceIllegalChars(String str) {
        if (StringUtils.containsAny(str, "!")) {
            str = str.replaceAll("!", "ǃ");    //exclamation mark -> alveolar click ǃ (same appearance, doesn't conflict)
        }
        if (StringUtils.containsAny(str, ";")) {
            str = str.replaceAll(";", ";");      //semicolon -> greek question mark ;
        }
        if (StringUtils.containsAny(str, "?")) {
            str = str.replaceAll("\\?", "？");   //question mark -> full-width
        }
        if (StringUtils.containsAny(str, "&")) {
            str = str.replaceAll("&", "＆");     //ampersand -> full-width
        }

        if (StringUtils.containsAny(str, "(") ||
            StringUtils.containsAny(str, "[") ||
            StringUtils.containsAny(str, "{")) {
                str = str.replaceAll("\\(|\\[|\\{", "⦅");
        }
        if (StringUtils.containsAny(str, ")") ||
            StringUtils.containsAny(str, "]") ||
            StringUtils.containsAny(str, "}")) {
                str = str.replaceAll("(\\)|]|})", "⦆");
        }

        return str;
    }

}
