package txp.prottoy.shafee.tanveer.facebookaccountkitapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;

public class MainActivity extends AppCompatActivity {
    private static final int APP_REQUEST_CODE = 99;
    private TextView successTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        successTxtView = findViewById(R.id.activity_main_txt_view_congrats);
        phoneLogin();
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == APP_REQUEST_CODE) {
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            if(loginResult.getError() != null) {
                successTxtView.setText(loginResult.getError().getErrorType().getMessage());
            }
            else if(loginResult.wasCancelled()) {
                successTxtView.setText("Login Cancelled");
            }
            else {
                if(loginResult.getAccessToken() != null) {
                    successTxtView.setText("Success:" + loginResult.getAccessToken().getApplicationId());
                }
                else {
                    successTxtView.setText(String.format("Success:%s...",
                            loginResult.getAuthorizationCode().substring(0, 10)));
                }
            }
        }
    }

    public void phoneLogin() {
        final Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN);
        intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        startActivityForResult(intent, APP_REQUEST_CODE);
    }
}