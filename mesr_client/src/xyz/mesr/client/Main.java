package xyz.mesr.client;

import xyz.mesr.client.Client;
import xyz.mesr.client.key.Keypair;
import xyz.mesr.client.packet.PacketSender;
import xyz.mesr.client.ui.MainUI;

public class Main {
    public static PacketSender packetSender;
    public static Client client;

    public static void main(String args[]) {
        Keypair keypair = new Keypair();
      /*  packetSender = new PacketSender();
        packetSender.run();

            packetSender.sendMessage("Count ");
            try {
                packetSender.getChannel().disconnect().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        client = new Client();
    }
}

