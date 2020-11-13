package org.pursuit.heard.fragments;

import android.content.Context;
import android.os.Bundle;
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
import org.pursuit.heard.databinding.FragmentSignupBinding;
import org.pursuit.heard.viewmodel.MainUserViewModel;

import io.reactivex.disposables.Disposable;

public class SignUpFragment extends Fragment {

    private FragmentSignupBinding binding;
    private String emailInput;
    private String passwordInput;
    private String usernameInput;
    private Disposable disposable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false);
        return binding.getRoot();
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

        disposable = RxTextView
                .textChanges(binding.usernameEdittext)
                .subscribe(input -> usernameInput = input.toString(),
                        Throwable::printStackTrace);

        disposable = RxView
                .clicks(binding.signUpButton)
                .takeWhile(u -> !usernameInput.isEmpty())
                .subscribe(complete -> {
                    InputMethodManager mgr = (InputMethodManager) requireActivity()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(binding.passwordEdittext.getWindowToken(), 0);

                    MainUserViewModel viewModel = new ViewModelProvider(requireActivity()).get(MainUserViewModel.class);
                    if (viewModel.setNewUser(emailInput, passwordInput, usernameInput)) {
                        Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_mainUserFragment);
                    }
                }, Throwable::printStackTrace);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
