import xyz.mesr.client.key.Keypair;
import xyz.mesr.client.packet.PacketSender;

public class Main {
    public static PacketSender packetSender;
    public static void main(String args[]) {
        Keypair keypair = new Keypair();
        packetSender = new PacketSender();
        packetSender.run();

            packetSender.sendMessage("Count ");
            try {
                packetSender.getChannel().disconnect().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

