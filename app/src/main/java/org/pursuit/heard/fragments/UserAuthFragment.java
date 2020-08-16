package org.pursuit.heard.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.jakewharton.rxbinding3.view.RxView;
import com.jakewharton.rxbinding3.widget.RxTextView;

import org.pursuit.heard.R;
import org.pursuit.heard.databinding.FragmentLoginBinding;
import org.pursuit.heard.viewmodel.UserViewModel;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserAuthFragment extends Fragment {

    private FragmentLoginBinding binding;
    private UserViewModel viewModel;

    private String emailInput;
    private String passwordInput;

    private SharedPreferences preferences;
    private Disposable disposable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        checkPreferences();
        return binding.getRoot();
    }

    private void checkPreferences() {
        preferences = requireContext()
                .getSharedPreferences(
                        getString(R.string.shared_prefs_key),
                        Context.MODE_PRIVATE);

        if (preferences.contains(getString(R.string.login_checkbox_key))) {
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

        disposable = RxTextView
                .textChanges(binding.emailEdittext)
                .subscribe(input -> emailInput = input.toString());

        disposable = RxTextView
                .textChanges(binding.passwordEdittext)
                .subscribe(input -> passwordInput = input.toString());

        disposable = RxView
                .clicks(binding.rememberMeCheckbox)
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

        disposable = RxView.clicks(binding.emailSignInButton)
                .subscribe(unit -> {
                    if (viewModel.verifyLogin(emailInput, passwordInput)) {
                        Log.d("USERAUTH", "Login success");
                        InputMethodManager mgr = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        mgr.hideSoftInputFromWindow(binding.passwordEdittext.getWindowToken(), 0);
                        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_mainUserFragment);
                    }
                });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
