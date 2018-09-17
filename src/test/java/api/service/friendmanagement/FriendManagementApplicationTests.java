package api.service.friendmanagement;

import api.service.friendmanagement.Dto.*;
import api.service.friendmanagement.Entity.Friend;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FriendManagementApplicationTests {

			private RestTemplate restTemplate = new RestTemplate();
	/**
	 * Server URL ending with the servlet mapping on which the application can
	 * be reached.
	 */
	private static final String BASE_URL = "http://localhost:8080";

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    private String subscriptionType;
	@Test
	public void listFriends() {
		String url = BASE_URL + "/fetchFriends/{emailAddress}";
		Friend[] friends = null;
        JSONFriendResponseDTO jrto = restTemplate.getForObject(url, JSONFriendResponseDTO.class,"andy@example.com");
		Assert.assertNotNull(jrto.getFriends());
        Assert.assertEquals("true",jrto.getSuccess());
	}

    @Test
    public void connectAsFriends() {
        String url = BASE_URL + "/connectFriends";
        JSONFriendsRequestDTO jsonFriendsRequestDTO = new JSONFriendsRequestDTO();
        List<String> friends = new ArrayList<String>();
        friends.add("andy@example.com");
        friends.add("john@example.com");
        jsonFriendsRequestDTO.setFriends(friends);

        JSONSuccessResponseDTO jsonSuccessResponseDTO = restTemplate.postForObject(url, jsonFriendsRequestDTO,JSONSuccessResponseDTO.class);
        Assert.assertNotNull(jsonSuccessResponseDTO);
    }

    @Test
    public void listCommonFriends() {
        List<String> friendsList = new ArrayList<String>();
        String url = BASE_URL + "/fetchCommonFriends";
        JSONFriendsRequestDTO jsonFriendsRequestDTO = new JSONFriendsRequestDTO();
        friendsList.add("andy@example.com");
        friendsList.add("john@example.com");
        jsonFriendsRequestDTO.setFriends(friendsList);
        JSONFriendResponseDTO jrto = restTemplate.postForObject(url,jsonFriendsRequestDTO, JSONFriendResponseDTO.class);
        Assert.assertNotNull(jrto.getFriends());
        Assert.assertEquals("true",jrto.getSuccess());
        Assert.assertNotEquals(0,jrto.getCount());
    }

    @Test
    public void subscribeFriend(){
        String url = BASE_URL + "/subscribeFriends/";
        url = url + (null!=subscriptionType? subscriptionType : "Yes");
//        Map<String, String> params = new HashMap<>();
//        params.put("SubscriptionType", null!=subscriptionType? subscriptionType : "Yes");
//        URI uri = UriComponentsBuilder.fromUriString(url)
//                .buildAndExpand(params)
//                .toUri();
        System.out.println(url);
        JSONSubscriptionRequestDTO jsonSubscriptionRequestDTO = new JSONSubscriptionRequestDTO();

        if(null!=subscriptionType && subscriptionType.equals("Blocked")){
            jsonSubscriptionRequestDTO.setRequestor("lisa@example.com");
            jsonSubscriptionRequestDTO.setTarget("john@example.com");
        }else{
            jsonSubscriptionRequestDTO.setRequestor("andy@example.com");
            jsonSubscriptionRequestDTO.setTarget("john@example.com");
        }

        JSONSuccessResponseDTO jsonSuccessResponseDTO = new JSONSuccessResponseDTO();
        jsonSuccessResponseDTO = restTemplate.postForObject(url,jsonSubscriptionRequestDTO,JSONSuccessResponseDTO.class);
        Assert.assertEquals("true",jsonSuccessResponseDTO.getSuccess());
    }

    @Test
    public void blockUser(){
	    subscriptionType = "Blocked";
        subscribeFriend();
    }

    @Test
    public void getNotificationEligibleFriendsList(){
        String url = BASE_URL + "/sendUpdates";
        JSONNotificationRequestDTO jsonNotificationRequesttDTO = new JSONNotificationRequestDTO();
        JSONNotificationResponsetDTO jsonNotificationResponsetDTO = new JSONNotificationResponsetDTO();
        jsonNotificationRequesttDTO.setSender("john@example.com");
        jsonNotificationRequesttDTO.setText("Hello World! kate@example.com");
        jsonNotificationResponsetDTO = restTemplate.postForObject(url,jsonNotificationRequesttDTO,JSONNotificationResponsetDTO.class);
        Assert.assertNotNull(jsonNotificationResponsetDTO);
        Assert.assertEquals("true",jsonNotificationResponsetDTO.getSuccess());

    }



}
