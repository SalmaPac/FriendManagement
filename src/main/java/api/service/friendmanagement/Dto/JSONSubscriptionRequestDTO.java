package api.service.friendmanagement.Dto;

public class JSONSubscriptionRequestDTO {

    public String getRequestor() {
        return requestor;
    }

    public void setRequestor(String requestor) {
        this.requestor = requestor;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    private String requestor;
    private String target;
}
