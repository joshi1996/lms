package com.lms.utility;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentTask {


    public static void replaceFrgament(Fragment mfragment, FragmentManager manager, int container) {
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.add(container, mfragment, mfragment.getClass().getSimpleName()).addToBackStack(mfragment.getClass().getSimpleName());
        // Commit the transaction
        fragmentTransaction.commit();
    }
}
