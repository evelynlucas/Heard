package org.pursuit.heard.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jakewharton.rxbinding3.view.RxView;

import org.pursuit.heard.R;
import org.pursuit.heard.database.ProfileDatabase;
import org.pursuit.heard.databinding.FragmentLoginBinding;
import org.pursuit.heard.viewmodel.UserViewModel;
import org.pursuit.heard.viewmodel.UserViewModelFactory;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import kotlin.Unit;

import static com.jakewharton.rxbinding3.view.RxView.*;

public class UserAuthFragment extends Fragment {

    private FragmentLoginBinding binding;
    private FirebaseAuth firebaseAuth;
    private EditText emailInput;
    private EditText passwordInput;
    private boolean logInSuccess = false;
    private Disposable disposable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        emailInput = binding.emailEdittext;
        passwordInput = binding.passwordEdittext;
        return binding.getRoot();
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RxView.clicks(binding.emailSignInButton).subscribe(unit -> {
            attemptLogin();
            if (logInSuccess) {
                InputMethodManager mgr = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(passwordInput.getWindowToken(), 0);
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_mainUserFragment);
            }
        });


//        binding.emailSignInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                attemptLogin();
//                if (logInSuccess) {
//                    InputMethodManager mgr = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                    mgr.hideSoftInputFromWindow(passwordInput.getWindowToken(), 0);
//                    Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_mainUserFragment);
//                }
//            }
//        });
    }

    private void attemptLogin() {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) requireContext(), task -> {
                    if (task.isSuccessful()) {
                        writeToBackend(email);
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
}
