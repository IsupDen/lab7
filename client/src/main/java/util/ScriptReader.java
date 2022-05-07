package util;

import java.io.*;

public class ScriptReader {

    private final File file;
    private final CommandManager commandManager;
    private final CommandReader commandReader;

    public ScriptReader(CommandManager aCommandManager, CommandReader aCommandReader, File aFile)
            throws FileNotFoundException {

        file = aFile;
        commandManager = aCommandManager;
        commandReader = aCommandReader;
    }

    public void read() throws IOException {

        String nextLine;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {

            do {
                nextLine = bufferedReader.readLine();
                if (nextLine == null) return;

                Console.getInstance().setBufferedReader(bufferedReader);

                UserCommand newCommand = commandReader.readCommand(nextLine);
                commandManager.transferCommand(newCommand);
            } while (true);
        }
    }
}
