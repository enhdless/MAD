package com.hhsfbla.launch;

import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * Created by zhenfangchen on 1/26/17.
 */

public class YourFundraisersPageFragment extends Fragment {
    private View yourFundraiserView;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        yourFundraiserView = inflater.inflate(R.layout.fragment_your_fundraisers, container, false);

        mRecyclerView = (RecyclerView) yourFundraiserView.findViewById(R.id.your_fundraisers_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final ArrayList<Fundraiser> fundraisers = new ArrayList<Fundraiser>();

        final StorageReference storageRef = FirebaseStorage.getInstance().getReference("/fundraisers");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/fundraisers");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fundraisers.clear();

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    final Fundraiser f = child.getValue(Fundraiser.class);
                    if (f.uid.equals(NavDrawerActivity.userID)) {
                        f.setId(child.getKey());
                        storageRef.child(child.getKey() + ".jpg").getBytes(1024 * 1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                BitmapFactory.Options options = new BitmapFactory.Options();
                                options.inSampleSize = 2;
                                f.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options));
                                fundraisers.add(f);
                                mAdapter = new FundraiserRecyclerViewAdapter(getActivity(), fundraisers);
                                mRecyclerView.setAdapter(mAdapter);
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {

            }
        });

        mAdapter = new FundraiserRecyclerViewAdapter(getActivity(), fundraisers);
        mRecyclerView.setAdapter(mAdapter);
        return yourFundraiserView;
    }
}
