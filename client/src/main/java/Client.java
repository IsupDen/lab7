import util.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

import static util.TextFormat.errText;
import static util.TextFormat.successText;


public class Client {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("\033[H\033[J");
            System.out.println(getEntryInformation());
            Console.getInstance().setScanner(scanner);
            String sessionStatus;

            while (true) {
                try {
                    getRequestHandlerProperties(scanner, InetAddress.getLocalHost());
                } catch (UnknownHostException e) {
                    System.out.println(errText("Your computer has problems with the network, " +
                            "run the application again!"));
                    return;
                }
                try {
                    sessionStatus = getSession();
                } catch (IOException e) {
                    System.out.println(errText("Client can't get authorization on server, try again!"));
                    return;
                }
                if (!sessionStatus.equals(successText("Action processed successful!"))) {
                    System.out.println(errText(sessionStatus));
                    continue;
                }
                RequestHandler.getInstance().setSocketStatus(true);
                System.out.println(RequestHandler.getInstance().getInformation());
                CommandReader commandReader = new CommandReader();
                if (commandReader.enable()) {
                    return;
                }
            }
        } catch (NoSuchElementException e) {
            System.exit(0);
        }
    }

    private static String getEntryInformation() {
        return TextFormat.successText("\u2554\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557\n\u2551 \u2593     \u2593\u2593\u2593\u2593\u2593 \u2593\u2593\u2593\u2593    \u2593   \u2593 \u2593\u2593\u2593\u2593\u2593 \u2593\u2593\u2593\u2593  \u2593  \u2593 \u2551\n\u2551 \u2593     \u2593   \u2593 \u2593   \u2593   \u2593 \u2593 \u2593 \u2593   \u2593 \u2593   \u2593 \u2593 \u2593  \u2551\n\u2551 \u2593     \u2593\u2593\u2593\u2593\u2593 \u2593\u2593\u2593\u2593    \u2593 \u2593 \u2593 \u2593   \u2593 \u2593\u2593\u2593\u2593  \u2593\u2593   \u2551\n\u2551 \u2593     \u2593   \u2593 \u2593   \u2593   \u2593 \u2593 \u2593 \u2593   \u2593 \u2593 \u2593   \u2593 \u2593  \u2551\n\u2551 \u2593\u2593\u2593\u2593\u2593 \u2593   \u2593 \u2593\u2593\u2593\u2593     \u2593 \u2593  \u2593\u2593\u2593\u2593\u2593 \u2593  \u2593  \u2593  \u2593 \u2551\n\u2560\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2563\n\u2551     \u250c\u2500\u256e\u250c\u2500\u250c\u2500\u256e\u252c\u256d\u2500\u2574    \u252c\u256d\u2500\u2574\u2577 \u2577\u250c\u2500\u256e\u256d\u2500\u256e\\    \u002f    \u2551\n\u2551     \u2502 \u2502\u251c\u2500\u2502 \u2502\u2502\u2570\u2500\u256e    \u2502\u2570\u2500\u256e\u2502 \u2502\u251c\u2500\u256f\u2502 \u2502 \\  \u002f     \u2551\n\u2551     \u2514\u2500\u256f\u2514\u2500\u2575 \u2575\u2534\u2576\u2500\u256f    \u2534\u2576\u2500\u256f\u2570\u2500\u256f\u2575  \u2570\u2500\u256f  \\\u002f      \u2551\n\u255a\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255d\n\n\n");
    }

    private static boolean requestTypeOfAddress(Scanner aScanner) {

        String answer;

        do {
            System.out.print("Do you want to connect to the localhost? [y/n] ");

            answer = aScanner.nextLine().toUpperCase();

        } while (!answer.equals("Y") && !answer.equals("N") && !answer.equals("YES") && !answer.equals("NO"));

        return (answer.equals("N") || answer.equals("NO"));
    }

    private static int getPort(Scanner scanner) {

        String arg;
        Pattern remoteHostPortPattern = Pattern.compile("(\\d{1,5})");

        do {
            System.out.print("Please, enter remote host port: ");
            arg = scanner.nextLine();
        } while (!(remoteHostPortPattern.matcher(arg).matches() && (Integer.parseInt(arg) < 65536)));

        return Integer.parseInt(arg);
    }

    private static void getRequestHandlerProperties(Scanner scanner, InetAddress localHostAddress) {

        InetAddress remoteHostAddress = localHostAddress;

        if (requestTypeOfAddress(scanner)) {

            String arg;
            Pattern ipAddress = Pattern.compile("(\\d{1,3}.){3}\\d{1,3}");
            Pattern dndAddress = Pattern.compile("\\w+(.\\w+)*");

            System.out.print("Enter remote host address: ");
            arg = scanner.nextLine();

            while (!ipAddress.matcher(arg).matches() && !dndAddress.matcher(arg).matches()) {
                System.out.print(errText("Enter correct remote host address: "));
                arg = scanner.nextLine();
            }

            try {
                remoteHostAddress = InetAddress.getByName(arg);
            } catch (UnknownHostException e) {
                System.out.println(errText(
                        "The program could not find the server by the specified address!\n" +
                                "The default address(localhost) will be used!"));
            }

        }
        RequestHandler.getInstance().setRemoteHostSocketAddress(
                new InetSocketAddress(remoteHostAddress, getPort(scanner)));
    }

    private static String getSession() throws IOException {
        Session session = new SessionWorker(Console.getInstance()).getSession();
        if (session.getTypeOfSession().equals(TypeOfSession.Register)) {
            return RequestHandler.getInstance().register(session);
        } else {
            return RequestHandler.getInstance().login(session);
        }
    }
}
