package com.example.api.ramsha.mynotes.activities;

import static com.example.api.ramsha.mynotes.data.FileStorage.SaveData;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.api.ramsha.mynotes.MyNotes;
import com.example.api.ramsha.mynotes.R;
import com.example.api.ramsha.mynotes.model.UserModel;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Calendar;

public class SignUp extends AppCompatActivity {
    EditText dateOfBirth, usernameET, userEmailET, userPasswordET;
    Button createButton;
    SearchView searchView;
    ImageButton google, facebook;
    MyNotes myNotes;
    RadioGroup radioGroup;
    String gender;
    static GoogleSignInClient mGoogleSignInClient;
    final int RC_SIGN_IN = 1;
    String TAG = "SignUp_Activity";
    private FirebaseAuth  mAuth = FirebaseAuth.getInstance();
    private CallbackManager mCallbackManager;
    private FirebaseAnalytics FA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        FA= FirebaseAnalytics.getInstance(this);
        if (firebaseUser != null) {
            Intent intent = new Intent(SignUp.this,Home.class);
           startActivity(intent);

            // Navigate to the home screen or main activity
        } else {
            setContentView(R.layout.activity_sign_up);
            FacebookSdk.setClientToken("1f65be5481de2664c0d97e62baae8de5");
            FacebookSdk.sdkInitialize(getApplicationContext());

            gender = "";
            myNotes = MyNotes.getInstance();
            findViews();
            clickListeners();
            changeListeners();
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            mCallbackManager = CallbackManager.Factory.create();
            FirebaseCrashlytics.getInstance().log("SigUp Page");
        }





    }




    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void changeListeners() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = findViewById(checkedId);
                gender = selectedRadioButton.getText().toString();
            }
        });
    }

    void findViews() {
        dateOfBirth = (EditText) findViewById(R.id.dateOfBirthET);
        usernameET = (EditText) findViewById(R.id.userNameET);
        userEmailET = (EditText) findViewById(R.id.userEmailET);
        userPasswordET = (EditText) findViewById(R.id.userpasswordET);
        createButton = (Button) findViewById(R.id.createButton);
        radioGroup = findViewById(R.id.userGender);
        google = findViewById(R.id.google);
        facebook = findViewById(R.id.facebook);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(SignUp.this, Home.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(SignUp.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
        else{
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
    public static GoogleSignInClient getGoogleSignInClient() {
        return mGoogleSignInClient;
    }
    void clickListeners() {
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(SignUp.this, Arrays.asList("public_profile", "email"));
                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        //Sign in completed
                        Log.i(TAG, "onSuccess: logged in successfully");

                        //handling the token for Firebase Auth
                        handleFacebookAccessToken(loginResult.getAccessToken());

                        //Getting the user information
                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.i(TAG, "onCompleted: response: " + response.toString());
                                try {
                                    String email = object.getString("email");
                                    String birthday = object.getString("birthday");

                                    Log.i(TAG, "onCompleted: Email: " + email);
                                    Log.i(TAG, "onCompleted: Birthday: " + birthday);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.i(TAG, "onCompleted: JSON exception");
                                }
                            }
                        });

                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender,birthday");
                        request.setParameters(parameters);
                        request.executeAsync();

                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "facebook:onCancel");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(TAG, "facebook:onError", error);
                    }
                });
            }
        });
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle params = new Bundle();
                params.putString("image_name", "ramsha");
                params.putString("full_text", "ramsha signed in");
                FA.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, params);
                signIn();
            }
        });

        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        SignUp.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                                dateOfBirth.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myNotes.updateVariables("", usernameET.getText().toString(), userPasswordET.getText().toString(), userEmailET.getText().toString(), dateOfBirth.getText().toString(), gender);
                //save data to file
                SaveData(usernameET.getText().toString(), userEmailET.getText().toString(), dateOfBirth.getText().toString(), gender);
                //save data to database
                UserModel user = new UserModel(usernameET.getText().toString(), userEmailET.getText().toString(), dateOfBirth.getText().toString(), gender);
                myNotes.getDb().addUser(user);
                Toast.makeText(myNotes, "" + usernameET.getText().toString(), Toast.LENGTH_SHORT).show();
                dateOfBirth.setText("");
                userEmailET.setText("");
                userPasswordET.setText("");
                usernameET.setText("");
                Intent intent = new Intent(SignUp.this, Home.class);
                startActivity(intent);
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.i(TAG, "onComplete: login completed with user: " + user.getDisplayName());

                        } else {
                            // If sign in fails, displaying a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(SignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}

