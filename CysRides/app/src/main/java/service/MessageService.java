package service;

import android.content.Context;

import domain.Message;

/**
 * Created by Ryan on 12/1/2017.
 */

public interface MessageService {
    void createMessage(final Context context, final Message message);
}
