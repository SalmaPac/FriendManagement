package api.service.friendmanagement.Dto;

import java.util.List;

public class JSONEmailRequestDTO {

    private List<String> email = null;


    public List<String> getEmail() {

        return email;
    }


    public void setEmail(List<String> email)
    {
        this.email = email;
    }

}
