package org.pursuit.heard.fragments;


import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import org.pursuit.heard.R;
import org.pursuit.heard.database.ProfileDatabase;
import org.pursuit.heard.databinding.FragmentLoginBinding;
import org.pursuit.heard.viewmodel.UserViewModel;
import org.pursuit.heard.viewmodel.UserViewModelFactory;

import static android.content.Context.MODE_PRIVATE;

public class LoginFragment extends Fragment {

    private static final String SHARED_PREFS_KEY = "org.pursuit.heard";
    private static final String SHARED_PREFS_USERNAME_KEY = "Username";
    private static final String SHARED_PREFS_CHECKBOX = "isCheckBoxChecked";

    private FragmentLoginBinding binding;

    private EditText emailView;
    private EditText passwordView;
    private CheckBox usernameCheckbox;
    private SharedPreferences sharedPreferences;
    private boolean logInSuccess = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_login, container, false);
        emailView = binding.emailEdittext;
        passwordView = binding.passwordEdittext;
        usernameCheckbox = binding.rememberUsernameCheckbox;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

        if (sharedPreferences.contains(SHARED_PREFS_USERNAME_KEY)
                && sharedPreferences.contains(SHARED_PREFS_CHECKBOX)) {
            String savedUser = sharedPreferences.getString(SHARED_PREFS_USERNAME_KEY, "");
            emailView.setText(savedUser);
            usernameCheckbox.setChecked(true);
        }

        binding.emailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
                if (logInSuccess) {
                    Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_mainUserFragment);
                }
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

        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            passwordView.setError("This password is invalid.");
            focusView = passwordView;
            cancel = true;
        }
        if (TextUtils.isEmpty(email) && !isEmailValid(email)) {
            emailView.setError("This email is invalid.");
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
                    writeToBackend(username);
                    logInSuccess = true;
                }
            }

            if (!usernameCheckbox.isChecked()) {
                sharedPreferences.edit().remove(SHARED_PREFS_USERNAME_KEY).apply();
                if (email.equals(userValue) && password.equals(passwordValue)) {
                    String username = "Alex";
                    writeToBackend(username);
                    logInSuccess = true;
                }
            }
        }

        if (cancel) {
            focusView.requestFocus();
        }
    }

    private void writeToBackend(String username) {
        Application application = requireActivity().getApplication();
        ProfileDatabase profileDatabase = ProfileDatabase.getInstance(requireContext());
        UserViewModelFactory factory = new UserViewModelFactory(profileDatabase, application);
        UserViewModel userViewModel = new ViewModelProvider(this, factory).get(UserViewModel.class);
        if (profileDatabase.getProfile(username) == 0) {
            profileDatabase.addProfile(username);
        }
        userViewModel.setCurrentUser(username.split("@")[0]);
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }
}
