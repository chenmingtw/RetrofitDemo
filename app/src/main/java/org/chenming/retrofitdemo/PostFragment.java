package org.chenming.retrofitdemo;

import android.os.Bundle;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostFragment extends Fragment {
    private static final String TAG = "PostFragment";

    // 1. 宣告MyAPIService
    private MyAPIService myAPIService;

    private Spinner sprRes;
    private EditText editUidPidAid;
    private EditText editTitleName;
    private EditText editBodyUrl;
    private Button btnSend;
    private TextView tResponse;

    public PostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 2. 透過RetrofitManager取得連線基底
        myAPIService = RetrofitManager.getInstance().getMyAPIService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post, container, false);

        sprRes = rootView.findViewById(R.id.spinner_resources);
        editUidPidAid = rootView.findViewById(R.id.editText_uid_pid_aid);
        editTitleName = rootView.findViewById(R.id.editText_title);
        editBodyUrl = rootView.findViewById(R.id.editText_body_url);
        btnSend = rootView.findViewById(R.id.button_send);
        tResponse = rootView.findViewById(R.id.textView_response);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sRes = sprRes.getSelectedItem().toString();
                sendRequest(sRes);
            }
        });

        return rootView;
    }

    private void sendRequest(String sRes) {
        printResponse(">>>Send Request (" + sRes.toLowerCase() + ")");

        switch (sRes) {
            case "Posts": {
                int userId = getYourInputOfUidPidAid();
                String title = editTitleName.getText().toString();
                String body = editBodyUrl.getText().toString();
                Posts posts = new Posts(userId, title, body);
                Call<Posts> call = myAPIService.postPosts(posts);
                call.enqueue(postsCallback);
                break;
            }
            case "Comments": {
                int userId = getYourInputOfUidPidAid();
                String title = editTitleName.getText().toString();
                String body = editBodyUrl.getText().toString();
                Comments comments = new Comments(userId, title, body);
                Call<Comments> call = myAPIService.postComments(comments);
                call.enqueue(commentsCallback);
                break;
            }
            case "Albums": {
                int userId = getYourInputOfUidPidAid();
                String title = editTitleName.getText().toString();
                Albums albums = new Albums(userId, title);
                Call<Albums> call = myAPIService.postAlbums(albums);
                call.enqueue(albumsCallback);
                break;
            }
            default:
                tResponse.append("Invalid request\n");
                Log.e(TAG, "Invalid request");
                break;
        }
    }

    private int getYourInputOfUidPidAid() {
        String sId = editUidPidAid.getText().toString();

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
            printResponse("<userId: " + response.body().getUserId());
            printResponse("<id: " + response.body().getId());
            printResponse("<title: " + response.body().getTitle());
            printResponse("<body: " + response.body().getBody());
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
            printResponse("<postId: " + response.body().getPostId());
            printResponse("<id: " + response.body().getId());
            printResponse("<name: " + response.body().getName());
            printResponse("<email: " + response.body().getEmail());
            printResponse("<body: " + response.body().getBody());
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
            printResponse("<userId: " + response.body().getUserId());
            printResponse("<id: " + response.body().getId());
            printResponse("<title: " + response.body().getTitle());
            printResponse("");
        }

        @Override
        public void onFailure(Call<Albums> call, Throwable t) {
            printResponse(t.getMessage());
        }
    };
}
