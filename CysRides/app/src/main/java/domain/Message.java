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

  //constructor for pulling messages from the database
  public Message(int id, int groupID, String sender, String message, Date timeSent){
    this.id = id;
    this.message = message;
    this.sender = sender;
    this.groupID = groupID;
    this.timeSent = timeSent;
  }
  //constructor for creating a message to send to database(doesnt include message id or timeSend
  //since they will be created in the database
  public Message(int groupID, String sender, String message){
      this.groupID = groupID;
      this.sender = sender;
      this.message = message;
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
