package util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;

import static util.TextFormat.errText;

public class ResponseHandler {
    public static String receive(ByteBuffer buffer) {
        try {
            ObjectInputStream inObj = new ObjectInputStream(new ByteArrayInputStream(buffer.array()));
            Response response = (Response) inObj.readObject();
            return Animator.getInstance().animate(response);
        } catch (ClassNotFoundException e) {
            return errText("Server version is unsupported!");
        } catch (InvalidClassException e) {
            return errText("Your version is outdated!");
        } catch (IOException e) {
            return errText("Response is broken! Try again!");
        }
    }

    public static String receive(String errorInformation) {
        return errorInformation;
    }
}
