package model.server;

public class MessageData {
    private String sendername;
    private String content;
    private String type;
    private Integer[] timestamp;


    public MessageData(String sendername, String content, String type, Integer[] timestamp) {
        this.sendername = sendername;
        this.content = content;
        this.type = type;
        this.timestamp = timestamp;
    }


}
