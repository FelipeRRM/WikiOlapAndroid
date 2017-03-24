package com.feliperrm.wikiolap.interfaces;

import com.feliperrm.wikiolap.models.DatasetMetadata;
import com.feliperrm.wikiolap.models.User;

import java.util.ArrayList;

/**
 * Created by felip on 02/03/2017.
 */

public interface UserViewCallbacks extends SimpleAsyncCallback{
    public void onDataLoaded(User user, String requestEmail);
}
