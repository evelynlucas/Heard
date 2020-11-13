package org.pursuit.heard.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.jakewharton.rxbinding3.view.RxView;
import com.jakewharton.rxbinding3.widget.RxTextView;

import org.pursuit.heard.R;
import org.pursuit.heard.databinding.FragmentLoginBinding;
import org.pursuit.heard.utils.C;
import org.pursuit.heard.viewmodel.MainUserViewModel;

import io.reactivex.disposables.Disposable;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private SharedPreferences preferences;
    private Disposable disposable;

    private String emailInput;
    private String passwordInput;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        checkPreferences();
        return binding.getRoot();
    }

    private void checkPreferences() {
        preferences = requireContext()
                .getSharedPreferences(C.SHARED_PREFS, Context.MODE_PRIVATE);

        if (preferences.getBoolean(
                C.SP_LOGIN_CHECKBOX, true)) {
            if (preferences.contains(C.SP_USERNAME) &&
                    preferences.contains(C.SP_PASSWORD)) {

                String savedUser = preferences.getString(C.SP_USERNAME, "");
                String savedPassword = preferences.getString(C.SP_PASSWORD, "");

                binding.emailEdittext.setText(savedUser);
                binding.passwordEdittext.setText(savedPassword);
            }
            binding.rememberMeCheckbox.setChecked(true);
        }

        disposable = RxView
                .clicks(binding.rememberMeCheckbox)
                .subscribe(unit -> {
                    if (binding.rememberMeCheckbox.isChecked()) {
                        preferences.edit().putBoolean(
                                C.SP_LOGIN_CHECKBOX, true).apply();
                        preferences.edit().putString(
                                C.SP_USERNAME, emailInput).apply();
                        preferences.edit().putString(
                                C.SP_PASSWORD, passwordInput).apply();

                    } else {
                        preferences.edit().putBoolean(
                                C.SP_LOGIN_CHECKBOX, false).apply();
                        preferences.edit().remove(C.SP_USERNAME).apply();
                        preferences.edit().remove(C.SP_PASSWORD).apply();
                    }
                }, Throwable::printStackTrace);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        disposable = RxTextView
                .textChanges(binding.emailEdittext)
                .subscribe(input -> emailInput = input.toString(),
                        Throwable::printStackTrace);

        disposable = RxTextView
                .textChanges(binding.passwordEdittext)
                .subscribe(input -> passwordInput = input.toString(),
                        Throwable::printStackTrace);


        disposable = RxView
                .clicks(binding.signInButton)
                .subscribe(unit -> {
                    InputMethodManager mgr = (InputMethodManager) requireActivity()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(binding.passwordEdittext.getWindowToken(), 0);

                    MainUserViewModel viewModel = new ViewModelProvider(requireActivity()).get(MainUserViewModel.class);
                    if (viewModel.verifyLogin(emailInput, passwordInput)) {
                        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_mainUserFragment);
                    }
                }, e -> {
                    Log.d("LOGIN", "message " + e.getMessage());
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
