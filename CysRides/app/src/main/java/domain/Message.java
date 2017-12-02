package domain;

import java.util.Date;
/**
 * Created by Ryan on 12/2/2017.
 */

public class Message {

  private String message;
  private String sender;
  private int groupID;
  private Date timeSent;

  public Message(String message, String sender, int groupID, Date timeSent){
    this.message = message;
    this.sender = sender;
    this.groupID = groupID;
    this.timeSent = timeSent;
  }

  public String getMessage(){
    return message;
  }

  public String getSender(){
    return sender;
  }

  public int getGroupID(){
    return groupID;
  }

  public Date getTimeSent(){
    return timeSent;
  }

  public String toString(){
    return sender + " (" + timeSent + "): " + message;
  }
}
