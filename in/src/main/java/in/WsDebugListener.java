package in;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Component
public class WsDebugListener {

    @EventListener
    public void connect(SessionConnectEvent e) {
        System.out.println("✅ CONNECT");
    }

    @EventListener
    public void subscribe(SessionSubscribeEvent e) {
        System.out.println("✅ SUBSCRIBE " + e.getMessage());
    }

    @EventListener
    public void disconnect(SessionDisconnectEvent e) {
        System.out.println("❌ DISCONNECT");
    }
}