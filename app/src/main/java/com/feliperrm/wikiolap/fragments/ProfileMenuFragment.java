package com.feliperrm.wikiolap.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.feliperrm.wikiolap.BuildConfig;
import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.interfaces.UserViewCallbacks;
import com.feliperrm.wikiolap.models.User;
import com.feliperrm.wikiolap.presenters.UserPresenter;
import com.feliperrm.wikiolap.utils.FirebaseUtil;
import com.feliperrm.wikiolap.utils.MyApp;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONObject;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileMenuFragment extends BaseFrgment implements UserViewCallbacks {

    LoginButton loginButton;
    CallbackManager callbackManager;
    UserPresenter presenter;
    ProgressDialog progressDialog;
    User user;
    ProgressBar userLoader;
    TextView welcomeText;
    CircularImageView profileImage;
    RelativeLayout readyLayout;

    public ProfileMenuFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new UserPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_menu, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        setUpViews();
    }

    private void findViews(View v) {
        loginButton = (LoginButton) v.findViewById(R.id.facebook_login);
        userLoader = (ProgressBar) v.findViewById(R.id.progressBar);
        readyLayout = (RelativeLayout) v.findViewById(R.id.readyLayout);
        welcomeText = (TextView) v.findViewById(R.id.welcome_text);
        profileImage = (CircularImageView) v.findViewById(R.id.profile_image);
    }

    private void setUpViews() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.logging_in));
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email"));
        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                requestEmail(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {
                if (BuildConfig.DEBUG) {
                    exception.printStackTrace();
                }
            }
        });


    }

    private void requestEmail(AccessToken accessToken) {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
        GraphRequest graphRequest = GraphRequest.newMeRequest(
                accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject me, GraphResponse response) {
                        if (response.getError() != null) {
                            onError(getString(R.string.facebook_email_error));
                        } else {
                            String email = me.optString("email");
                            String id = me.optString("id");
                            presenter.loadUser(email);
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, first_name, last_name, email, gender, birthday, location");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onLoadingStarted() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        userLoader.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.GONE);
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onDataLoaded(User user, String requestEmail) {
        if (user == null) {
            Profile profile = Profile.getCurrentProfile();
            user = new User(profile.getName(), profile.getProfilePictureUri(640, 640).toString(), requestEmail);
            FirebaseUtil.createUser(user);
        }
        MyApp.app.setLoggedUser(user);
        this.user = user;
        onUserDefined();
    }

    private void onUserDefined() {
        welcomeText.setText(getString(R.string.welcome_text, user.getName()));
        userLoader.setVisibility(View.GONE);
        readyLayout.setVisibility(View.VISIBLE);
        Glide.with(getContext()).load(user.getPicture())
                .placeholder(R.drawable.profile_placeholder)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(profileImage);
    }

}
