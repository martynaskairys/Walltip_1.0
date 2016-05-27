package com.martynaskairys.walltip;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.martynaskairys.walltip.DataTypes.Folder;
import com.martynaskairys.walltip.networking.ApiService;
import com.martynaskairys.walltip.networking.RetrofitSetup;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Activity for user to choose which folder he/she wants Pictures from
 */
public class ChoosingFolderActivity extends AppCompatActivity {

    private ViewGroup content;
    private ProgressBar progressBar;
    private Button buttonA;
    private Button buttonB;
    private Button buttonC;
    private ViewGroup rootView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_choosing_folder);

        findViews();

        fetchImageUrlsAndUpdateUiAccordingly();

    }

    private void findViews() {
        rootView = (ViewGroup) findViewById(R.id.root);
        content = (ViewGroup) findViewById(R.id.content);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        buttonA = (Button) findViewById(R.id.button_folder_a);
        buttonB = (Button) findViewById(R.id.button_folder_b);
        buttonC = (Button) findViewById(R.id.button_folder_c);


    }

    private void fetchImageUrlsAndUpdateUiAccordingly() {

        showProgressBarOnly();

        ApiService service = new RetrofitSetup().getService();
        service.getFolders(new Callback<List<Folder>>() {
            @Override
            public void success(List<Folder> folders, Response response) {
                setupButtons(folders);
                showContentOnly();
            }

            @Override
            public void failure(RetrofitError error) {
                showRetryButtonOnly();
            }
        });
    }

    private void showRetryButtonOnly() {
        content.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

        Snackbar.make(rootView, R.string.something_not_right, Snackbar.LENGTH_INDEFINITE).setAction(R.string.retry, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchImageUrlsAndUpdateUiAccordingly();
            }
        }).show();
    }

    private void showContentOnly() {
        content.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void showProgressBarOnly() {
        content.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void setupButtons(List<Folder> folders) {

        String[] urlsFolderA = toArray(folders.get(0));
        String[] urlsFolderB = toArray(folders.get(1));
        String[] urlsFolderC = toArray(folders.get(2));

        setupFolderAButton(urlsFolderA);
        setupFolderBButton(urlsFolderB);
        setupFolderCButton(urlsFolderC);
    }

    private String[] toArray(Folder folder) {

        List<String> urlsList = folder.getUrls();

        String[] strings = new String[urlsList.size()];
        for (int i = 0; i < urlsList.size(); i++) {
            strings[i] = urlsList.get(i);
        }

        return strings;
    }


    private void setupFolderAButton(final String[] urlsFolderA) {
        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String folderA = getString(R.string.text_explaining_folder_content_a);
                //Intent intent = new Intent(ChoosingFolderActivity.this, ThumbnailActivity.class);
                Intent intent = new Intent(ChoosingFolderActivity.this, ThumbnailActivity1.class);
                intent.putExtra(ThumbnailActivity1.EXPLANATION, folderA);
                intent.putExtra("images", urlsFolderA);
                intent.putExtra("texts", getString(R.string.folder_a));
                Bundle bundle = new Bundle();
                bundle.putInt("image", R.drawable.pic1a);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void setupFolderBButton(final String[] urlsFolderB) {
        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String folderB = getString(R.string.text_explaining_folder_content_b);
                Intent intent = new Intent(ChoosingFolderActivity.this, ThumbnailActivity2.class);
                intent.putExtra(ThumbnailActivity1.EXPLANATION, folderB);
                intent.putExtra("images", urlsFolderB);
                intent.putExtra("texts", getString(R.string.folder_b));
                Bundle bundle = new Bundle();
                bundle.putInt("image", R.drawable.pic1b);
                intent.putExtras(bundle);

                startActivity(intent);

            }
        });
    }

    private void setupFolderCButton(final String[] urlsFolderC) {
        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String folderC = getString(R.string.text_explaining_folder_content_c);
                Intent intent = new Intent(ChoosingFolderActivity.this, ThumbnailActivity3.class);
                intent.putExtra(ThumbnailActivity1.EXPLANATION, folderC);
                intent.putExtra("images", urlsFolderC);
                intent.putExtra("texts", getString(R.string.folder_c));
                Bundle bundle = new Bundle();
                bundle.putInt("image", R.drawable.pic1c);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }

}
