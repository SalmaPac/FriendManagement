package api.service.friendmanagement.Dto;

import java.util.List;

public class JSONNotificationResponsetDTO {

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    private String success;

    public List<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

    private List<String> recipients;
}
