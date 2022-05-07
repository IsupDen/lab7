package commands;

import util.Receiver;
import util.Request;
import util.Response;

public class RemoveFirst extends Command{
    private final Receiver receiver;

    public RemoveFirst(Receiver receiver) {
        super("remove the first element from the collection", true);
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request) {
        String username = request.getSession().getName();
        return new Response(receiver.removeById(username, 1));
    }
}
