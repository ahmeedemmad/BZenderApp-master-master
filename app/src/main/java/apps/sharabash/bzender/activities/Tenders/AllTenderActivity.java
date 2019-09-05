package apps.sharabash.bzender.activities.Tenders;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.ArrayList;
import java.util.List;

import apps.sharabash.bzender.Models.AddTenders.TendersModelResponse;
import apps.sharabash.bzender.Models.allTinders.AllTenderRecyclerItem;
import apps.sharabash.bzender.Models.allTinders.car.AllTender;
import apps.sharabash.bzender.Models.allTinders.electrical.AllTenderElectrical;
import apps.sharabash.bzender.R;
import apps.sharabash.bzender.Utills.Constant;
import apps.sharabash.bzender.Utills.MyTextViewBold;
import apps.sharabash.bzender.activities.TenderDetails.TinderDetails;
import apps.sharabash.bzender.adapters.AllTenderRecyclerAdapter;

public class AllTenderActivity extends AppCompatActivity implements TendersInterface, AllTenderRecyclerAdapter.OnClickHandler {


    private final List<String> tenderElectricalId = new ArrayList<>();
    private final List<String> tenderCarId = new ArrayList<>();
    private final List<AllTenderRecyclerItem> allTenderRecyclerItem = new ArrayList<>();
    private String dateTime;
    private RecyclerView mRecyclerViewAllTender;
    private LinearLayoutCompat mTxtEmpty;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        NavUtils.navigateUpFromSameTask(this);
        Animatoo.animateFade(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Constant.ENTERED_HERE) {
            finish();
            Intent starterIntent = new Intent(this, AllTenderActivity.class);
            startActivity(starterIntent);

            Constant.ENTERED_HERE = false;
        }
        // recreate();
    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Constant.changeLang(this, Constant.getLng(this));
        setContentView(R.layout.activity_list_items);

        mTxtEmpty = findViewById(R.id.txt_empty);


        AppCompatImageView mImgBack = findViewById(R.id.imageNavigationIcon);
        mImgBack.setOnClickListener(v -> {
            NavUtils.navigateUpFromSameTask(this);
            Animatoo.animateFade(this);
        });

        mRecyclerViewAllTender = findViewById(R.id.recycler);

        TendersPresenter tendersPresenter = new TendersPresenter(this, this);


        if (Constant.categoryId.equals(String.valueOf(10021))) {
            tendersPresenter.getAllTenderItems(String.valueOf(10021));
        } else if (Constant.categoryId.equals(String.valueOf(10022)))
            tendersPresenter.getAllTenderItemsElectrical(String.valueOf(10022));

        MyTextViewBold mTxtTitle = findViewById(R.id.title_appbar);
        mTxtTitle.setText(getString(R.string.item_details));

        Log.d("TEST", "onCreate: " + Constant.categoryId);
    }

    @Override
    public void handleCategoryList(List<TendersModelResponse> tendersModelResponses) {

    }

    @Override
    public void getAllTenderList(List<AllTender> allTenders) {

        Log.d("RESPONSE", "START___ : " + allTenders.get(0).getStartDateTender() + " END__" + allTenders.get(0).getEndDateTender());

        if (allTenders.isEmpty()) {
            //Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
            //Log.d("TESTTESTTEST", "getMyTender: " + "nullllll");

            mTxtEmpty.setVisibility(View.VISIBLE);

            return;
        }

        mTxtEmpty.setVisibility(View.GONE);

        for (int i = 0; i < allTenders.size(); i++) {
            allTenderRecyclerItem.add(new AllTenderRecyclerItem(allTenders.get(i).getStartDateTender()
                    , allTenders.get(i).getEndDateTender()
                    , allTenders.get(i).getCategoryID()
                    , allTenders.get(i).getTenderName()
                    , allTenders.get(i).getBookingCount()));

            tenderCarId.add(allTenders.get(i).getId());

        }


        mRecyclerViewAllTender.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerViewAllTender.setHasFixedSize(true);
        AllTenderRecyclerAdapter recyclerAdapter = new AllTenderRecyclerAdapter(this, allTenderRecyclerItem, this);
        mRecyclerViewAllTender.setAdapter(recyclerAdapter);

    }

    @Override
    public void getAllTenderElectrical(List<AllTenderElectrical> allTenderElectricals) {


        Log.d("TESTTESTTEST", "getAllTenderElectrical: " + allTenderElectricals.toString());
        if (allTenderElectricals.isEmpty()) {
            //   Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
            //  Log.d("TESTTESTTEST", "getMyTender: " + "nullllll");

            mTxtEmpty.setVisibility(View.VISIBLE);

            return;
        }


        mTxtEmpty.setVisibility(View.GONE);
        Log.d("RESPONSE", "START___ : " + allTenderElectricals.get(0).getStartDateTender() + " END__" + allTenderElectricals.get(0).getEndDateTender());


        for (int i = 0; i < allTenderElectricals.size(); i++) {
            allTenderRecyclerItem.add(new AllTenderRecyclerItem(allTenderElectricals.get(i).getStartDateTender()
                    , allTenderElectricals.get(i).getEndDateTender()
                    , allTenderElectricals.get(i).getCategoryID()
                    , allTenderElectricals.get(i).getTenderName()
                    , allTenderElectricals.get(i).getBookingCount()));

            tenderElectricalId.add(allTenderElectricals.get(i).getId());
        }


        mRecyclerViewAllTender.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerViewAllTender.setHasFixedSize(true);
        AllTenderRecyclerAdapter recyclerAdapter = new AllTenderRecyclerAdapter(this, allTenderRecyclerItem, this);
        mRecyclerViewAllTender.setAdapter(recyclerAdapter);
        Constant.runLayoutAnimation(mRecyclerViewAllTender);


    }


    @Override
    public void onClick(int position) {


        Intent intent = new Intent(this, TinderDetails.class);

        if (!tenderCarId.isEmpty()) {
            Log.d("TESST", "onClick: " + tenderCarId.size());
            intent.putExtra(Constant.TENDER_ID, tenderCarId.get(position));
        }
        if (!tenderElectricalId.isEmpty()) {
            Log.d("TESST", "onClick: " + tenderElectricalId.size());
            Log.d("TESST", "onClick: " + tenderElectricalId.get(position));
            intent.putExtra(Constant.TENDER_ID, tenderElectricalId.get(position));
        }

        startActivity(intent);
        Animatoo.animateSlideRight(this);


//        //intent.putExtra(Constant.TENDER_ID)
//        if (tenderCarId.size() > 1) {
//           // Constant.TENDER_ID = String.valueOf(tenderCarId.get(position));
//            Log.d("IDDD", "onClick: " + tenderCarId.get(position));
//        }
//
//        if (tenderElectricalId.size() > 1) {
//            //Constant.TENDER_ID = String.valueOf(tenderCarId.get(position));
//            Log.d("IDDD", "onClick: " + tenderCarId.get(position));
//        }
//

    }

}
