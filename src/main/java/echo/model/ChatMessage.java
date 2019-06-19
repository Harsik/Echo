package echo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;
    private String avatarUrl;

    public enum MessageType {
        CHAT, JOIN, LEAVE
    }
}