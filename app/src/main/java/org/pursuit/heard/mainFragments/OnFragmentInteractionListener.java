package org.pursuit.heard.mainFragments;

import android.os.Bundle;

import org.pursuit.heard.database.UserProfile;
import org.pursuit.heard.viewmodel.UserViewModel;

public interface OnFragmentInteractionListener {

    void openAddArtistFragment(UserViewModel viewModel);

    void loginToMainFragment(String username);

}
