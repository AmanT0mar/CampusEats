package com.example.campuseats;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campuseats.viewmodel.CartItems;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    RecyclerView recyclerViewCart;
    static TextView totalamt;
    DatabaseReference databaseReferenceCart;
    int priceperitem = 0;
    RecyclerViewAdapterCart recyclerViewAdapterCart;
    ArrayList<CartItems> cartlist;
    RelativeLayout placeorderbtn;

    static int Amount;




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_cart, container, false);

        placeorderbtn = rootView.findViewById(R.id.googlePayButton);
        totalamt = rootView.findViewById(R.id.total_amount);
        recyclerViewCart = rootView.findViewById(R.id.cart_items);
        databaseReferenceCart = FirebaseDatabase.getInstance("https://campuseats-272f8-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Cart");
        recyclerViewCart.setHasFixedSize(true);
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(getContext()));
        cartlist = new ArrayList<>();
        recyclerViewAdapterCart = new RecyclerViewAdapterCart(getContext(),cartlist);
        recyclerViewCart.setAdapter(recyclerViewAdapterCart);
        databaseReferenceCart.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CartItems cartItems = dataSnapshot.getValue(CartItems.class);
                    cartlist.add(cartItems);
                    priceperitem += Integer.parseInt(cartItems.getPrice()) * Integer.parseInt(cartItems.getQuantity());
                }
                recyclerViewAdapterCart.notifyDataSetChanged();
                Amount = priceperitem;
                totalamt.setText("Total Price:   ₹ "+Amount);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });
        placeorderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PayAmount();
            }
        });
        return rootView;
    }




    private void PayAmount() {
        String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
        int GOOGLE_PAY_REQUEST_CODE = 123;

        Uri uri =
                new Uri.Builder()
                        .scheme("upi")
                        .authority("pay")
                        .appendQueryParameter("pa", "amantomar123456789@okhdfcbank")
                        .appendQueryParameter("pn", "CampusEats")
                        //.appendQueryParameter("mc", "your-merchant-code")
                        //.appendQueryParameter("tr", "your-transaction-ref-id")
                        //.appendQueryParameter("tn", "your-transaction-note")
                        .appendQueryParameter("am", Integer.toString(Amount))
                        .appendQueryParameter("cu", "INR")
                        //.appendQueryParameter("url", "your-transaction-url")
                        .build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        intent.setPackage(GOOGLE_PAY_PACKAGE_NAME);
        startActivityForResult(intent, GOOGLE_PAY_REQUEST_CODE);
    }
    public static void ChangeAmount(int amt)
    {
        Amount -= amt;
        totalamt.setText("Total Price:   ₹ "+Amount);
    }
}