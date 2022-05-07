package util;

import static util.TextFormat.*;

public class Animator {
    private static Animator instance;

    private Animator() {
    }

    public static Animator getInstance() {
        if (instance == null) instance = new Animator();
        return instance;
    }

    public String animate(Response response) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        if (response.getStatus().equals(TypeOfAnswer.SUCCESSFUL)) {
            if (response.getInformation() != null) {
                System.out.println(response.getInformation());
                }
            if (response.getHelp() != null) {
                response.getHelp().
                        forEach((key, value) -> sb
                                .append(successText(key.toUpperCase()))
                                .append(" : ")
                                .append(value)
                                .append("\n\n"));
            }
            if (response.getCollection() != null) {
                response.getCollection()
                        .forEach(sg -> sb.append(sg).append("\n\n"));
            }
            if (response.getLabWork() != null) {
                sb.append(response.getLabWork().toString())
                        .append("\n");
            }
            if (response.getCount() != null) {
                sb.append("Amount of elements: ")
                        .append(successText(String.valueOf(response.getCount())))
                        .append("\n");
            }
            if (sb.toString().equals("\n")) return "Action processed successful!";
        } else {
            switch (response.getStatus()) {
                case OBJECTNOTEXIST:
                    return errText("\nNo object with such parameters was found!\n");
                case DUPLICATESDETECTED:
                    return errText("\nThis element probably duplicates " +
                            "existing one and can't be added\n");
                case ISNTMAX:
                    return errText("\nLabWork isn't max!\n");
                case PERMISSIONDENIED:
                    return errText("\nPermission denied!\n");
                case SQLPROBLEM:
                    return errText("\nSome problem's with database on server!\n");
                case EMPTYCOLLECTION:
                    return helpText("\nCollection is empty!\n");
                case ALREADYREGISTERED:
                    return errText("\nThis account already registered!\n");
                case NOTMATCH:
                    return errText("\nAccount with this parameters doesn't exist!\n");
            }
        }
        return sb.toString();
    }
}
