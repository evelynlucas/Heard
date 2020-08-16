package org.pursuit.heard.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jakewharton.rxbinding3.view.RxView;
import com.jakewharton.rxbinding3.widget.RxTextView;

import org.pursuit.heard.R;
import org.pursuit.heard.database.ProfileDatabase;
import org.pursuit.heard.databinding.FragmentLoginBinding;
import org.pursuit.heard.viewmodel.UserViewModel;
import org.pursuit.heard.viewmodel.UserViewModelFactory;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class UserAuthFragment extends Fragment {

    private FragmentLoginBinding binding;
    private FirebaseAuth firebaseAuth;

    private String emailInput;
    private String passwordInput;

    private boolean logInSuccess = false;
    private SharedPreferences preferences;
    private Disposable disposable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        setPreferences();
        return binding.getRoot();
    }

    private void setPreferences() {
        preferences = requireContext()
                .getSharedPreferences(
                        getString(R.string.shared_prefs_key),
                        Context.MODE_PRIVATE);

        if (preferences.getBoolean(getString(R.string.login_checkbox_key), true)) {
            if (preferences.contains(getString(R.string.user_name_key))) {
                String savedUser = preferences.getString(
                        getString(R.string.user_name_key), "");
                binding.emailEdittext.setText(savedUser);
            }

            if (preferences.contains(getString(R.string.password_key))) {
                String savedPassword = preferences.getString(
                        getString(R.string.password_key), "");
                binding.passwordEdittext.setText(savedPassword);
            }
            binding.rememberMeCheckbox.setChecked(true);
        }
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        disposable = RxTextView.textChanges(binding.emailEdittext)
                .subscribe(input -> emailInput = input.toString());

        disposable = RxTextView.textChanges(binding.passwordEdittext)
                .subscribe(input -> passwordInput = input.toString());

        disposable = RxView.clicks(binding.rememberMeCheckbox)
                .subscribeOn(Schedulers.io())
                .subscribe(unit -> {
                    if (binding.rememberMeCheckbox.isChecked()) {
                        preferences.edit().putBoolean(
                                UserAuthFragment.this.getString(R.string.login_checkbox_key), true).apply();
                        preferences.edit().putString(
                                UserAuthFragment.this.getString(R.string.user_name_key), emailInput).apply();
                        preferences.edit().putString(
                                UserAuthFragment.this.getString(R.string.password_key), passwordInput).apply();

                    } else {
                        preferences.edit().putBoolean(
                                UserAuthFragment.this.getString(R.string.login_checkbox_key), false).apply();
                        preferences.edit().remove(UserAuthFragment.this.getString(R.string.user_name_key)).apply();
                        preferences.edit().remove(UserAuthFragment.this.getString(R.string.password_key)).apply();
                    }
                }, Throwable::printStackTrace);

        disposable = RxView.clicks(binding.emailSignInButton).subscribe(unit -> {
                 attemptLogin();
            if (logInSuccess) {
                InputMethodManager mgr = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(binding.passwordEdittext.getWindowToken(), 0);
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_mainUserFragment);
            }
        });
    }

    private void attemptLogin() {
        firebaseAuth.signInWithEmailAndPassword(emailInput, passwordInput)
                .addOnCompleteListener((Activity) requireContext(), task -> {
                    if (task.isSuccessful()) {
                        writeToBackend(emailInput);
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        logInSuccess = true;
                    } else {
                        Toast.makeText(requireContext(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void writeToBackend(String email) {
        Application application = requireActivity().getApplication();
        ProfileDatabase profileDatabase = ProfileDatabase.getInstance(requireContext());
        UserViewModelFactory factory = new UserViewModelFactory(profileDatabase, application);
        UserViewModel userViewModel = new ViewModelProvider(requireActivity(), factory).get(UserViewModel.class);

        String username = email.split("@")[0];
        userViewModel.setCurrentUser(username.substring(0, 1).toUpperCase() + username.substring(1));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
