package com.codecool.buyourstuff.model;

import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

@Getter
public class Supplier extends BaseModel {

    private static final int NAME_MINLENGTH = 3;
    private static final int NAME_MAXLENGTH = 60;
    private static final int DESC_MINLENGTH = 20;
    private static final int DESC_MAXLENGTH = 10000;
    private static final String ILLEGAL_CHARS = "'\"\\()[]{}&|?!-;~$<>*%_";

    @NonNull
    private final String name;
    @NonNull
    private final String description;

    public Supplier(@NonNull String name, @NonNull String description) {
        if (containsAny(name, ILLEGAL_CHARS)) {
            throw new IllegalArgumentException("Invalid username");
        }
        if (!isCreationValid(name, NAME_MINLENGTH, NAME_MAXLENGTH)) {
            throw new IllegalArgumentException("Invalid username");
        }
        if (!isCreationValid(description, DESC_MINLENGTH, DESC_MAXLENGTH)) {
            throw new IllegalArgumentException("Invalid password");
        }

        this.name = name;
        this.description = description;
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
        if (containsAny(str, ILLEGAL_CHARS)) {
            replaceIllegalChars(str);
        }

        return true;
    }

    private static boolean containsAny(String compareMe, String to) {
        return StringUtils.containsAny(compareMe, to);
    }

    //TODO: not actually converting
    private static String replaceIllegalChars(String str) {
        if (containsAny(str, "!")) {
            str.replaceAll("\\!", "ǃ");    //exclamation mark -> alveolar click (same appearance, doesn't affect graphics)
        }
        if (containsAny(str, ";")) {
            str.replaceAll("\\;", ";");    //semicolon -> greek question mark (same appearance, doesn't affect graphics)
        }
        if (containsAny(str, "?")) {
            str.replaceAll("\\?", "？"); //question mark -> full-width (similar appearance, doesn't affect graphics)
        }
        if (containsAny(str, "(") || containsAny(str, "[") || containsAny(str, "{")) {
            str.replaceAll("(\\(\\[\\{)", "⦅");
        }
        if (containsAny(str, ")") || containsAny(str, "]") || containsAny(str, "}")) {
            str.replaceAll("(\\)]})", "⦆");
        }
        if (containsAny(str, "&")) {
            str.replaceAll("&", "＆");
        }
        return str;
    }

}
