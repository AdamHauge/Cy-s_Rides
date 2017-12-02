package service;

import android.content.Context;

import domain.Message;
import volley.MessageVolley;
import volley.MessageVolleyImpl;

/**
 * Created by Ryan on 12/1/2017.
 */

public class MessageServiceImpl implements MessageService{

  MessageVolley mv = new MessageVolleyImpl();

  public MessageServiceImpl() { };

    @Override
    public
    void createMessage(Context context, Message message) {
        mv.createMessage(context, message);
    }


}
