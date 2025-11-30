package in.ws;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

public class ChatController {
    @MessageMapping("/mensaje")
    @SendTo("/topic/mensajes")
    public String recibirMensaje(String mensaje) {
        System.out.println("Mensaje recibido: " + mensaje);
        return "Servidor dice: " + mensaje;
    }
}
