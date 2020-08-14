package org.pursuit.heard.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import org.pursuit.heard.R;

import static android.content.Context.MODE_PRIVATE;

public class LoginFragment extends Fragment {
    private static final String SHARED_PREFS_KEY = "org.pursuit.heard";
    private static final String SHARED_PREFS_USERNAME_KEY = "Username";
    private static final String SHARED_PREFS_CHECKBOX = "isCheckBoxChecked";

    private EditText emailView;
    private EditText passwordView;
    private CheckBox usernameCheckbox;
    private SharedPreferences sharedPreferences;

    private OnFragmentInteractionListener mListener;

    public LoginFragment() {}

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emailView = view.findViewById(R.id.email_edittext);
        passwordView = view.findViewById(R.id.password_edittext);
        usernameCheckbox = view.findViewById(R.id.remember_username_checkbox);

        passwordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        sharedPreferences = view.getContext().getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        if ((view.getContext().getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE).contains(SHARED_PREFS_USERNAME_KEY))
                && (view.getContext().getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE).contains(SHARED_PREFS_CHECKBOX))) {
            String savedUser = view.getContext().getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE).getString(SHARED_PREFS_USERNAME_KEY, "");
            emailView.setText(savedUser);
            usernameCheckbox.setChecked(true);
        }

        Button mEmailSignInButton = view.findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    private void attemptLogin() {
        emailView.setError(null);
        passwordView.setError(null);

        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            passwordView.setError("This password is too short.");
            focusView = passwordView;
            cancel = true;
        }
        if (TextUtils.isEmpty(email)) {
            emailView.setError("This field is required.");
            focusView = emailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailView.setError("This email address is invalid.");
            focusView = emailView;
            cancel = true;
        } else {
            String userValue = getResources().getString(R.string.dummy_username);
            String passwordValue = getResources().getString(R.string.dummy_password);

            if (usernameCheckbox.isChecked()) {
                if (email.equals(userValue) && password.equals(passwordValue)) {
                    sharedPreferences.edit().putString(SHARED_PREFS_USERNAME_KEY, email).apply();
                    sharedPreferences.edit().putBoolean(SHARED_PREFS_CHECKBOX, true).apply();
                    String username = "Alex";
                    mListener.loginToMainFragment(username);
                }
            }

            if (!usernameCheckbox.isChecked()) {
                sharedPreferences.edit().remove(SHARED_PREFS_USERNAME_KEY).apply();
                if (email.equals(userValue) && password.equals(passwordValue)) {
                    String username = "Alex";
                    mListener.loginToMainFragment(username);
                }
            }
        }

        if (cancel) {
            focusView.requestFocus();
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }
}
