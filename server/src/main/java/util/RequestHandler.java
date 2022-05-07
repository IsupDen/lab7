package util;

import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.concurrent.Executor;

public class RequestHandler{

    private final Invoker invoker;
    private final Executor threadPool;

    public RequestHandler(Invoker invoker,Executor threadPool) {
        this.invoker = invoker;
        this.threadPool = threadPool;
    }

    public void process(Request request, DatagramSocket datagramSocket, SocketAddress socketAddress){
        Task task = new Task(invoker, request, datagramSocket, socketAddress);
        threadPool.execute(task);
    }
}
