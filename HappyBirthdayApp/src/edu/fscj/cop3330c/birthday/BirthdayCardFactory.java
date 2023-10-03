// BirthdayCardFactory.java
// D. Singletary
// 7/2/23
// Class which represents a birthday card

package edu.fscj.cop3330c.birthday;

import java.util.Arrays;

public abstract class BirthdayCardFactory {
    public abstract void createCard(User u,
                                    BirthdayCardSender sender);
}

// factory subclasses

// ChatGPT4: "make this message age appropriate for a <age category>:
// "Hope all of your birthday wishes come true!"

class BirthdayCard_Child_Factory extends BirthdayCardFactory {
    public void createCard(User u, BirthdayCardSender sender) {
        final String MSG = """  
              Happy Birthday! Hope your day is filled with magic, fun,
              and all your birthday wishes coming true! 🎈🎂
              """;
        BirthdayCard bc = new BirthdayCard_Child(u, MSG);
        sender.sendCard(bc);
    }
}

class BirthdayCard_Adolescent_Factory extends BirthdayCardFactory {
    final String MSG = """  
              Happy Birthday! Hope your day is as awesome as you are
              and all your birthday wishes come true! 🎉🎈
              """;
    public void createCard(User u, BirthdayCardSender sender) {
        BirthdayCard bc = new BirthdayCard_Adolescent(u, MSG);
        sender.sendCard(bc);
    }
}

class BirthdayCard_Adult_Factory extends BirthdayCardFactory {
    final String MSG = """  
              Happy Birthday!
              Here's to a wonderful year ahead, filled with joy, growth,
              and every single one of your birthday wishes coming true. 
              Cheers!
              """;
    public void createCard(User u, BirthdayCardSender sender) {
        BirthdayCard bc = new BirthdayCard_Adult(u, MSG);
        sender.sendCard(bc);
    }
}

class BirthdayCard_Senior_Factory extends BirthdayCardFactory {
    final String MSG = """  
              Happy Birthday!
              May this special day bring you cherished memories, joy
              in the present, and hopes for the future. 
              Wishing all of your birthday dreams and wishes come true!
              """;
    public void createCard(User u, BirthdayCardSender sender) {
        BirthdayCard bc = new BirthdayCard_Senior(u, MSG);
        sender.sendCard(bc);
    }
}

class BirthdayCard implements BirthdayCardBuilder {

    // test with odd length (comment to test with even length, below)
    protected final String WISHES = "Hope all of your birthday wishes come true!";
    // uncomment to test with even length
    //final String WISHES = "Hope all of your birthday wishes come true!x";

    User user;
    private String message;

    public BirthdayCard() { }

    public BirthdayCard(User user, String msg) {
        this.user = user;
        System.out.println("building card for " + this.getClass().getSimpleName());
        this.buildCard(user, msg);
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    // given a String containing a (possibly) multi-line message,
    // split the lines, find the longest line, and return its length
    public int getLongest(String s) {
        final String NEWLINE = "\n";
        int maxLength = 0;
        String[] splitStr = s.split(NEWLINE);
        for (String line : splitStr)
            if (line.length() > maxLength)
                maxLength = line.length();
        return maxLength;
    }

    public void buildCard(User u, String message) {

        String msg;
        if (message == null)
            msg = "Happy Birthday, " + u.getName() + "\n" + WISHES;
        else
            msg = message;

        final String NEWLINE = "\n";

        // get the widest line and number of lines in the message
        int longest = getLongest(msg);

        // need to center lines
        // dashes on top (header) and bottom (footer)
        // vertical bars on the sides
        // |-----------------------|
        // | longest line in group |
        // |      other lines      |
        // |-----------------------|
        //
        // pad with an extra space if the length is odd

        int numDashes = (longest + 2) + (longest % 2);  // pad if odd length
        char[] dashes = new char[numDashes];  // header and footer
        char[] spaces = new char[numDashes];  // body lines
        Arrays.fill(dashes, '-');
        Arrays.fill(spaces, ' ');
        String headerFooter = "|" + new String(dashes) + "|\n";
        String spacesStr = "|" + new String(spaces) + "|\n";

        // start the card with the header
        this.message  = headerFooter;

        // split the message into separate strings
        String[] splitStr = msg.split(NEWLINE);
        for (String s : splitStr) {
            String line = spacesStr;  // start with all spaces

            // create a StringBuilder with all spaces,
            // then replace some spaces with the centered string
            StringBuilder buildLine = new StringBuilder(spacesStr);

            // start at  middle minus half the length of the string (0-based)
            int start = (spacesStr.length() / 2) - (s.length() / 2);
            // end at the starting index plus the length of the string
            int end = start + s.length();
            /// replace the spaces and create a String, then append
            buildLine.replace(start, end, s);
            line = new String(buildLine);
            this.message += line;
        }
        // append the footer
        this.message += headerFooter;
    }

    @Override
    public String toString() {
        String s = "Birthday card for " + this.getUser().getName() + "\n";
        s += getMessage();
        return s;
    }
}

//AGE_UNKNOWN, AGE_INFANT, AGE_CHILD, AGE_ADOLESCENT, AGE_ADULT, AGE_SENIOR;

class BirthdayCard_Child extends BirthdayCard {
    public BirthdayCard_Child(User u, String msg) {
        super(u, msg);
    }
}

class BirthdayCard_Adolescent extends BirthdayCard {
    public BirthdayCard_Adolescent(User u, String msg) {
        super(u, msg);
    }
}

class BirthdayCard_Adult extends BirthdayCard {
    public BirthdayCard_Adult(User u, String msg) {
        super(u, msg);
    }
}

class BirthdayCard_Senior extends BirthdayCard {
    public BirthdayCard_Senior(User u, String msg) {
        super(u, msg);
    }
}