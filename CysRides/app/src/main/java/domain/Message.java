package domain;

import java.util.Date;
/**
 * Created by Ryan on 12/2/2017.
 */

public class Message {

  private String message;
  private String sender;
  private int groupID;
  private int id;
  private Date timeSent;


  public Message(int id, int groupID, String sender, String message, Date timeSent){
    this.id = id;
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

  public int getID(){
    return id;
  }

  public String toString(){
    return sender + " (" + timeSent + "): " + message;
  }
}
