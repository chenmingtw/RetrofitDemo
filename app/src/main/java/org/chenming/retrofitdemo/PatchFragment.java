package org.chenming.retrofitdemo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.chenming.retrofitdemo.model.Albums;
import org.chenming.retrofitdemo.model.Comments;
import org.chenming.retrofitdemo.model.MyAPIService;
import org.chenming.retrofitdemo.model.Posts;
import org.chenming.retrofitdemo.model.RetrofitManager;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class PatchFragment extends Fragment {
    private static final String TAG = "PatchFragment";

    private MyAPIService myAPIService;

    private Spinner sprRes;
    private EditText editId;
    private EditText editTitleName;
    private EditText editBodyUrl;
    private Button btnSend;
    private TextView tResponse;

    public PatchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myAPIService = RetrofitManager.getInstance().getMyAPIService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_patch, container, false);
        sprRes = rootView.findViewById(R.id.spinner_resources);
        editId = rootView.findViewById(R.id.editText_id);
        editTitleName = rootView.findViewById(R.id.editText_title);
        editBodyUrl = rootView.findViewById(R.id.editText_body_url);
        btnSend = rootView.findViewById(R.id.button_send);
        tResponse = rootView.findViewById(R.id.textView_response);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sRes = sprRes.getSelectedItem().toString();
                int resId = getYourInputOfResId();
                sendRequest(sRes, resId);
            }
        });

        return rootView;
    }

    private void sendRequest(String sRes, int resId) {
        printResponse(">>>Send Request (" + sRes.toLowerCase() + "/" + resId + ")");

        switch (sRes) {
            case "Posts": {
                String title = editTitleName.getText().toString();
                String body = editBodyUrl.getText().toString();
                Map<String, String> map = new HashMap<>();
                if (!title.isEmpty())
                    map.put("title", title);
                if (!body.isEmpty())
                    map.put("body", body);

                Call<Posts> call = myAPIService.patchPosts(resId, map);
                call.enqueue(postsCallback);
                break;
            }
            case "Comments": {
                String name = editTitleName.getText().toString();
                String body = editBodyUrl.getText().toString();
                Map<String, String> map = new HashMap<>();
                if (!name.isEmpty())
                    map.put("name", name);
                if (!body.isEmpty())
                    map.put("body", body);

                Call<Comments> call = myAPIService.patchComments(resId, map);
                call.enqueue(commentsCallback);
                break;
            }
            case "Albums": {
                String title = editTitleName.getText().toString();
                Call<Albums> call = myAPIService.patchAlbums(resId, title);
                call.enqueue(albumsCallback);
                break;
            }
            default:
                tResponse.append("Invalid request\n");
                Log.e(TAG, "Invalid request");
                break;
        }
    }

    private int getYourInputOfResId() {
        String sId = editId.getText().toString();

        if (sId.isEmpty())
            return 1;
        else
            return Integer.valueOf(sId);
    }

    private void printResponse(String log) {
        tResponse.append(log + "\n");
    }

    /**
     * Callback for Request
     */
    private Callback<Posts> postsCallback = new Callback<Posts>() {
        @Override
        public void onResponse(Call<Posts> call, Response<Posts> response) {
            Log.d(TAG, "Status code: " + response.code());
            if (response.body() != null) {
                printResponse("<userId: " + response.body().getUserId());
                printResponse("<id: " + response.body().getId());
                printResponse("<title: " + response.body().getTitle());
                printResponse("<body: " + response.body().getBody());
            } else {
                printResponse("Status: " + response.code());
            }
            printResponse("");
        }

        @Override
        public void onFailure(Call<Posts> call, Throwable t) {
            printResponse(t.getMessage());
            printResponse("");
        }
    };

    private Callback<Comments> commentsCallback = new Callback<Comments>() {
        @Override
        public void onResponse(Call<Comments> call, Response<Comments> response) {
            Log.d(TAG, "Status code: " + response.code());
            if (response.body() != null) {
                printResponse("<postId: " + response.body().getPostId());
                printResponse("<id: " + response.body().getId());
                printResponse("<name: " + response.body().getName());
                printResponse("<email: " + response.body().getEmail());
                printResponse("<body: " + response.body().getBody());
            } else {
                printResponse("Status: " + response.code());
            }
            printResponse("");
        }

        @Override
        public void onFailure(Call<Comments> call, Throwable t) {
            printResponse(t.getMessage());
            printResponse("");
        }
    };

    private Callback<Albums> albumsCallback = new Callback<Albums>() {
        @Override
        public void onResponse(Call<Albums> call, Response<Albums> response) {
            Log.d(TAG, "Status code: " + response.code());
            if (response.body() != null) {
                printResponse("<userId: " + response.body().getUserId());
                printResponse("<id: " + response.body().getId());
                printResponse("<title: " + response.body().getTitle());
            } else {
                printResponse("Status: " + response.code());
            }
            printResponse("");
        }

        @Override
        public void onFailure(Call<Albums> call, Throwable t) {
            printResponse(t.getMessage());
        }
    };
}
