package donation.solutions.hamza.com.hotingoadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import donation.solutions.hamza.com.hotingoadmin.adapter.RequestAdapter;

public class RequestActivity extends AppCompatActivity {

    @BindView(R.id.requestsRV)
    RecyclerView requestsRV;

    RequestAdapter requestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        ButterKnife.bind(this);

        requestsRV.setLayoutManager
                (new GridLayoutManager(this, 1));

        requestAdapter = new RequestAdapter(
                R.layout.request_item, RequestActivity.this, new RequestAdapter.onRequestClickListner() {

            @Override
            public void onRequestClickListner() {

                startActivity(new Intent(RequestActivity.this, RequestDeatails.class));
            }
        });

        requestsRV.setAdapter(requestAdapter);

    }


}

