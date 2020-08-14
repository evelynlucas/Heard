package org.pursuit.heard.fragments;

import org.pursuit.heard.viewmodel.UserViewModel;

public interface OnFragmentInteractionListener {

    void openAddArtistFragment(UserViewModel viewModel);

    void loginToMainFragment(String username);

}
