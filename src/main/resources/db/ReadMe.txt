Project Title
FriendManagement System

Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

Prerequisites
PostMan Tool have to be installed in order to execute the Rest Webservice. Junit cases can also be executed with preloaded request data with Rest template to invoke the Rest Webservices

1. As a user, I need an API to create a friend connection between two email addresses.
/connectFriends

{
  "friends":
    [
      "andy@example.com",
      "john@example.com"
    ]
}

2. As a user, I need an API to retrieve the friends list for an email address.
/fetchFriends/{emailAddress}
/fetchFriends/andy@example.com

{
  email: 'andy@example.com'
}


3. As a user, I need an API to retrieve the common friends list between two email addresses.
/fetchCommonFriends
{
  "friends":
    [
      "andy@example.com",
      "john@example.com"
    ]
}

4. As a user, I need an API to subscribe to updates from an email address.
/subscribeFriends/Yes

{
  "requestor": "lisa@example.com",
  "target": "john@example.com"
}

5. As a user, I need an API to block updates from an email address.
/subscribeFriends/Blocked

{
  "requestor": "andy@example.com",
  "target": "john@example.com"
}

6. As a user, I need an API to retrieve all email addresses that can receive updates from an email address.
/sendUpdates

{
  "sender":  "john@example.com",
  "text": "Hello World! kate@example.com"
}

Built With
SpringBoot - The web framework is built but not used
Maven - Dependency Management
Rest Webservices - Used for micro services

Authors
Salma
