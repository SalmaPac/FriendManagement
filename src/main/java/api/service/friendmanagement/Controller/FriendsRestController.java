package api.service.friendmanagement.Controller;

import api.service.friendmanagement.Dto.*;
import api.service.friendmanagement.Entity.*;
import api.service.friendmanagement.Service.FriendsManagementService;
import api.service.friendmanagement.Util.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A Spring @Controller controller handling requests to view and modify
 * User and friends information.
 * <p>
 * Note that all the friends application classes are imported from the
 * <li>Service layer: {@link FriendsManagementService} interface and its
 * implementations</li>
 * <li>No repository layer is being used in the controller - the FriendsManagementService does
 * everything</li>
 */
@Controller
public class FriendsRestController {

    /**
     * Creates a new FriendsManagementService with a given friends Management Service.
     */
    private final FriendsManagementService friendsManagementService;

    @Autowired
    public FriendsRestController(FriendsManagementService friendsManagementService) {
        this.friendsManagementService = friendsManagementService;
    }

    /**
     * Test method for web interface
     * @return view name resolved by mustache resolver
     */
    @RequestMapping(value="/fm")
    public String launch() {
        return "friendManagement";
    }

    @RequestMapping(value="/errorFM",method = RequestMethod.GET)
    public String errorFM() {
        System.out.print("error");
        return "error";
    }

    /**
     * Custom Exception handler to return custom messages
     * @return
     */
    @ExceptionHandler({ DataException.class})
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public String handleException() {
        return "error";
    }

    /**
     * <p>
     * Provide a JSON Response with to indicate the status of establishing connection between two users.
     * </p>
     *
     * @param friends the JSONFriendsRequestDTO object with json request data
     */
    @PostMapping (value = "/connectFriends", produces = "application/JSON")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public @ResponseBody
    JSONSuccessResponseDTO connectAsFriends(
            @RequestBody JSONFriendsRequestDTO friends) {
        System.out.print("REST Service Called");
        JSONSuccessResponseDTO jsonSuccessResponseDTO;
        jsonSuccessResponseDTO = friendsManagementService.connectFriends(friends);
        System.out.print("REST Service Called connectAsFriends = "+jsonSuccessResponseDTO.getSuccess());
        return jsonSuccessResponseDTO;
    }

    /**
     * <p>
     * Provide a JSON Response  with a list of all friends for the given user email.
     * </p>
     *
     * @param emailAddress the json request data with emailAddress parameter
     */
    @RequestMapping(
            value = "/fetchFriends/{emailAddress}",
            method = RequestMethod.GET,
            produces = {"application/JSON"})
    public @ResponseBody JSONFriendResponseDTO getFriends(@PathVariable String emailAddress, Model model) {
        System.out.print("REST Service Called ="+emailAddress);
        User user = new User();
        user.setUserEmailAddress(emailAddress);
        JSONFriendResponseDTO jrb = friendsManagementService.getFriendsList(user);
        model.addAttribute("friendsList", jrb.getFriends());
        System.out.print("REST Service Called");
        return jrb;
    }

    /**
     * <p>
     * Provide a JSON Response with a list of all common friends for the given user email address.
     * </p>
     *
     * @param jsonFriendsRequestDTO the json request data with emailAddress of the 2 users
     */
    @PostMapping (value = "/fetchCommonFriends", produces = "application/JSON")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public @ResponseBody
    JSONFriendResponseDTO getCommonFriends(
            @RequestBody JSONFriendsRequestDTO jsonFriendsRequestDTO) {
        JSONFriendResponseDTO jrb = new JSONFriendResponseDTO();
        jrb = friendsManagementService.searchCommonFriends(jsonFriendsRequestDTO);
        return jrb;
    }

    /**
     * <p>
     * Provide a JSON Response to indicate the update of Subscription
     * </p>
     *
     * @param JSONSubscriptionRequestDTO the json request data with emailAddress of the 2 users to be subscribed
     */
    @PostMapping(value = "/subscribeFriends/Yes", produces = "application/JSON")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public @ResponseBody JSONSuccessResponseDTO subscribeFriends(@RequestBody JSONSubscriptionRequestDTO friends) {
        System.out.print("REST Service subscribeUnsubcribeFriends Called");
        JSONSuccessResponseDTO jsonSuccessResponseDTO;
        jsonSuccessResponseDTO = friendsManagementService.subscribeUnsubscribeUsers(friends,"Yes");
        System.out.print("REST Service Called connectAsFriends = "+jsonSuccessResponseDTO.getSuccess());
        return jsonSuccessResponseDTO;
    }

    /**
     * <p>
     * Provide a JSON Response to indicate the update of Subscription in order to subscribe for any updates from an email address.
     * Note : Post Mapping is used as the JSON Request data is a complex object and not a simple string value.
     * </p>
     *
     * @param JSONSubscriptionRequestDTO the json request data with emailAddress of the 2 users to be blocked in order to block any updates.
     */
    @PostMapping(value = "/subscribeFriends/Blocked", produces = "application/JSON")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public @ResponseBody JSONSuccessResponseDTO unsubscribeFriends(@RequestBody JSONSubscriptionRequestDTO friends) {
        System.out.print("REST Service subscribeUnsubscribeFriends Called");
        JSONSuccessResponseDTO jsonSuccessResponseDTO;
        jsonSuccessResponseDTO = friendsManagementService.subscribeUnsubscribeUsers(friends,"Blocked");
        System.out.print("REST Service Called connectAsFriends = "+jsonSuccessResponseDTO.getSuccess());
        return jsonSuccessResponseDTO;
    }

    /**
     * <p>
     * Provide a JSON Response to retrieve all email addresses that can receive updates from an email address.
     * Note : Post Mapping is used as the JSON Request data is a complex object and not a simple string value.
     * </p>
     *
     * @param JSONSubscriptionRequestDTO the json request data with emailAddress of the 2 users to be subscribed
     */
    @PostMapping (value = "/sendUpdates", produces = "application/JSON")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public @ResponseBody
    JSONNotificationResponsetDTO SendUpdates(
            @RequestBody JSONNotificationRequestDTO userMsgDetails) {
        System.out.print("REST Service SendUpdates Called");
        JSONNotificationResponsetDTO jsonNotificationResponsetDTO = friendsManagementService.searchNotificationEligibleFriendsList(userMsgDetails);
        return jsonNotificationResponsetDTO;
    }



}
