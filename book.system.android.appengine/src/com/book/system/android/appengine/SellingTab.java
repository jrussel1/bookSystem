package com.book.system.android.appengine;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import com.example.BookExchange.R;

/**
 * Created by Phillip on 2/25/14.
 */
public class SellingTab extends Fragment{
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sellingtab, container, false);
    }
}
