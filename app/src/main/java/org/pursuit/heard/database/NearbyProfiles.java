package org.pursuit.heard.database;

import org.pursuit.heard.network.networkmodel.ArtistModel;

import java.util.ArrayList;
import java.util.List;

public class NearbyProfiles {

    UserProfile user1, user2, user3;

    public NearbyProfiles() {
        createUserProfiles();
    }

    public void createUserProfiles() {

        List<ArtistModel> user1artistsList = new ArrayList<ArtistModel>(){{
            add(new ArtistModel("Miles Davis", "https://is5-ssl.mzstatic.com/image/thumb/Music/v4/54/2b/d3/542bd385-012e-7a23-3f03-200b1a7c518c/source/100x100bb.jpg"));
            add(new ArtistModel("Jorja Smith", "https://is2-ssl.mzstatic.com/image/thumb/Music118/v4/d4/be/89/d4be8988-dd4b-913d-7c05-0db1757a396a/source/100x100bb.jpg"));
            add(new ArtistModel("Primus", "https://is3-ssl.mzstatic.com/image/thumb/Music/v4/5b/ae/02/5bae026a-545b-b195-c5d4-aa1ca0463932/source/100x100bb.jpg"));
            add(new ArtistModel("Buddy Holly", "https://is2-ssl.mzstatic.com/image/thumb/Music118/v4/96/53/ba/9653ba07-6730-87d1-8cdb-0c77875c8c49/source/100x100bb.jpg"));
        }};
        user1 = new UserProfile("Jeremy", 38745783, user1artistsList);

        List<ArtistModel> user2artistsList = new ArrayList<ArtistModel>(){{
            add(new ArtistModel("Taylor Swift", "https://is1-ssl.mzstatic.com/image/thumb/Music/v4/11/b7/3f/11b73fb0-46af-42b5-111a-6bce1815562f/source/100x100bb.jpg"));
            add(new ArtistModel("The Drums", "https://is2-ssl.mzstatic.com/image/thumb/Music62/v4/e5/87/05/e587052d-288e-1182-b894-4a68b837f4c2/source/100x100bb.jpg"));
            add(new ArtistModel("Pink Floyd", "https://is4-ssl.mzstatic.com/image/thumb/Music7/v4/e5/9f/fb/e59ffb3b-1283-ee80-56a5-1b983df53ab2/source/100x100bb.jpg"));
        }};
        user2 = new UserProfile("Finn", 23490, user2artistsList);

        List<ArtistModel> user3artistList = new ArrayList<ArtistModel>(){{
            add(new ArtistModel("Beyonce", "https://is5-ssl.mzstatic.com/image/thumb/Music/v4/96/b5/28/96b5280d-379c-af56-a0f5-1825b7a411b4/source/100x100bb.jpg"));
            add(new ArtistModel("Ariana Grande", "https://is4-ssl.mzstatic.com/image/thumb/Music114/v4/44/a8/aa/44a8aadf-ba22-8bb2-8b61-b5aa268a6ef3/source/100x100bb.jpg"));
            add(new ArtistModel("Metallica", "https://is1-ssl.mzstatic.com/image/thumb/Music/v4/0b/9c/d2/0b9cd2e7-6e76-8912-0357-14780cc2616a/source/100x100bb.jpg"));
        }};
        user3 = new UserProfile("Chloe", 23424, user3artistList);

    }

    public UserProfile getUser1() {
        return user1;
    }

    public UserProfile getUser2() {
        return user2;
    }

    public UserProfile getUser3() {
        return user3;
    }
}
