package api.service.friendmanagement.Dto;

public class JSONNotificationRequestDTO {
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    private String sender;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private String text;
}
