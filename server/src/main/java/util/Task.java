package util;

import java.net.DatagramSocket;
import java.net.SocketAddress;

public class Task implements Runnable{

    private final Invoker invoker;
    private final Request request;

    private final DatagramSocket datagramSocket;
    private final SocketAddress socketAddress;

    public Task(Invoker invoker, Request request, DatagramSocket datagramSocket, SocketAddress socketAddress) {
        this.invoker = invoker;
        this.request = request;
        this.datagramSocket = datagramSocket;
        this.socketAddress = socketAddress;
    }

    @Override
    public void run() {
        Response response = invoker.execute(request);
        Deliver deliver = new Deliver(datagramSocket, response, socketAddress);
        Thread deliverManager = new Thread(deliver);
        deliverManager.start();
    }
}
