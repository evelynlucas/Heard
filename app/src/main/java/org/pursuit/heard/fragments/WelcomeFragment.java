package org.pursuit.heard.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.jakewharton.rxbinding3.view.RxView;

import org.pursuit.heard.R;
import org.pursuit.heard.animation.BounceInterpolator;
import org.pursuit.heard.databinding.WelcomeFragmentBinding;

public class WelcomeFragment extends Fragment {

    private WelcomeFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.welcome_fragment, container, false);
        startAnimations();
        return binding.getRoot();
    }

    private void startAnimations() {
        final Animation animation = AnimationUtils.loadAnimation(requireContext(), R.anim.bounce);
        BounceInterpolator interpolator = new BounceInterpolator(0.2, 20);
        animation.setInterpolator(interpolator);
        binding.logoText.startAnimation(animation);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.signInButton.setVisibility(View.VISIBLE);
                RxView.clicks(binding.signInButton).subscribe(unit -> {
                    Navigation.findNavController(view).navigate(R.id.action_welcomeFragment_to_loginFragment);
                });
                binding.signUpButton.setVisibility(View.VISIBLE);
                RxView.clicks(binding.signUpButton).subscribe(unit -> {
                    Navigation.findNavController(view).navigate(R.id.action_welcomeFragment_to_signUpFragment);
                });
            }
        }, 2000);
    }
}
