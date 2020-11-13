package org.pursuit.heard.viewmodel;

import org.pursuit.heard.model.User;

import java.util.List;

public interface FetchMatchesListener {

    void getMatches(List<User> matchedUsers);
}
