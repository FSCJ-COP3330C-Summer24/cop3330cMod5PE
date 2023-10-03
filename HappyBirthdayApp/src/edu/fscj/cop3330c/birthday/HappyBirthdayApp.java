// HappyBirthdayApp.java
// D. Singletary
// 1/29/23
// wish multiple users a happy birthday

package edu.fscj.cop3330c.birthday;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;

// main mpplication class
public class HappyBirthdayApp implements BirthdayCardSender {
    //private User user;
    private ArrayList<User> birthdays = new ArrayList<>();

    public HappyBirthdayApp() { }

    public void sendCard(BirthdayCard bc) {
        System.out.println(bc);
        System.out.println("sending email to " + bc.getUser().getEmail() + "\n");
    }

    // compare current month and day to user's data
    // to see if it is their birthday
    public boolean isBirthday(User u) {
        boolean result = false;

        LocalDate today = LocalDate.now();
        if (today.getMonth() == u.getBirthday().getMonth() &&
                today.getDayOfMonth() == u.getBirthday().getDayOfMonth())
            result = true;

        return result;
    }

    // add multiple birthdays
    public void addBirthdays(User... users) {
        for (User u : users) {
            birthdays.add(u);
        }
    }

    // main program
    public static void main(String[] args) {

        HappyBirthdayApp hba = new HappyBirthdayApp();

        BirthdayCardFactory cardFactory;

        // use current date for testing, adjust where necessary
        ZonedDateTime currentDate = ZonedDateTime.now();

        final User USERS[] = {
            // negative test
            new User("Miles", "Bennell", "Miles.Bennell@email.test",
                currentDate.minusDays(1)),
            // positive tests
            // test with odd length full name
            new User("Becky", "Driscoll", "Becky.Driscoll@email.test",
                currentDate),
            // test with even length full name
            new User("Jack", "Belicec", "Jack.Belicec@email.test",
                currentDate),
            new User("Theodora", "Belicec", "Theodora.Belicec.@email.test",
                currentDate),
            new User("Sally", "Withers", "Sally.Withers@email.test",
                currentDate)
        };

        hba.addBirthdays(USERS);

        // create a lambda to send the card
        BirthdayCardSender sender = (bc) -> {
            hba.sendCard(bc);
        };

        // show the birthdays
        if (!hba.birthdays.isEmpty()) {
            System.out.println("Here are the birthdays:");
            int count = 0;
            for (User u : hba.birthdays) {
                System.out.println(u.getName() + ":");
                // see if today is their birthday
                if (!hba.isBirthday(u))
                   System.out.println("Sorry, today is not their birthday.\n");
                else {
                    BirthdayCard bc = null;
                    count++;
                    switch (count) {
                        case 1:
                            cardFactory = new BirthdayCard_Child_Factory();
                            cardFactory.createCard(u, sender);
                            break;
                        case 2:
                            cardFactory = new BirthdayCard_Adolescent_Factory();
                            cardFactory.createCard(u, sender);
                            break;
                        case 3:
                            cardFactory = new BirthdayCard_Adult_Factory();
                            cardFactory.createCard(u, sender);
                            break;
                        case 4, 5: // look for 5 in case we add another test
                            cardFactory = new BirthdayCard_Senior_Factory();
                            cardFactory.createCard(u, sender);
                            break;
                    }
                    //sender.sendCard(bc);
                }
            }
        }
    }
}

// user class
class User {
    private StringBuilder name;
    private String email;
    private ZonedDateTime birthday;

    public User(String fName, String lName, String email, 
                ZonedDateTime birthday) {
        this.name = new StringBuilder();
        this.name.append(fName).append(" ").append(lName);
        this.email = email;
        this.birthday = birthday;
    }

    public StringBuilder getName() {
        return name;
    }

    public String getEmail() { return email; }

    public ZonedDateTime getBirthday() {
        return birthday;
    }

    @Override
    public String toString() {
        return this.name + "," + this.birthday;
    }
}
