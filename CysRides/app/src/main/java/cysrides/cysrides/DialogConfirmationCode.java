package cysrides.cysrides;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import domain.UserInfo;
import service.UserIntentService;
import service.UserIntentServiceImpl;

public class DialogConfirmationCode extends AppCompatActivity {

    private UserIntentService userIntentService = new UserIntentServiceImpl();
    private EditText resultView;
    private UserInfo user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_confirmation_code);
        resultView = (EditText) findViewById(R.id.resultText);
        user = userIntentService.getUserFromIntent(this.getIntent());

    }


}
