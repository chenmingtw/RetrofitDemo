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
import org.chenming.retrofitdemo.model.Photos;
import org.chenming.retrofitdemo.model.Posts;
import org.chenming.retrofitdemo.model.RetrofitManager;
import org.chenming.retrofitdemo.model.Todos;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class GetFragment extends Fragment {
    private static final String TAG = "GetFragment";

    // 1. 宣告MyAPIService
    private MyAPIService myAPIService;

    private Spinner sprRes;
    private EditText editId;
    private EditText editPostId1;
    private EditText editPostId2;
    private Button btnSend1;
    private Button btnSend2;
    private Button btnSend3;
    private TextView tResponse;

    public GetFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_get, container, false);

        sprRes = rootView.findViewById(R.id.spinner_resources);
        editId = rootView.findViewById(R.id.editText_id);
        editPostId1 = rootView.findViewById(R.id.editText_postId);
        editPostId2 = rootView.findViewById(R.id.editText_postId2);
        btnSend1 = rootView.findViewById(R.id.button_send1);
        btnSend2 = rootView.findViewById(R.id.button_send2);
        btnSend3 = rootView.findViewById(R.id.button_send3);
        tResponse = rootView.findViewById(R.id.textView_response);

        btnSend1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sRes = sprRes.getSelectedItem().toString();
                int resId = getYourInputOfResId();
                sendRequest(sRes, resId);
            }
        });

        btnSend2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int postId = getYourInputOfPostId1();
                printResponse(">>>Send Request (posts/" + postId + "/comments)");
                Call<List<Comments>> call = myAPIService.getCommentsByPostId(postId);
                call.enqueue(commentsListCallback);
            }
        });

        btnSend3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int postId = getYourInputOfPostId2();
                printResponse(">>>Send Request (comments?postId=" + postId + ")");
                Call<List<Comments>> call = myAPIService.getCommentsByQueryPostId(postId);
                call.enqueue(commentsListCallback);
            }
        });

        return rootView;
    }

    private void sendRequest(final String sRes, final int resId) {
        printResponse(">>>Send Request (" + sRes.toLowerCase() + "/" + resId + ")");

        switch (sRes) {
            case "Posts":
                // 3. 建立連線的Call，此處設置call為myAPIService中的getAlbums()連線
                Call<Posts> postsCall = myAPIService.getPostsById(resId);
                // 4. 執行call
                postsCall.enqueue(postsCallback);
                break;
            case "Comments":
                Call<Comments> commentsCall = myAPIService.getCommentsById(resId);
                commentsCall.enqueue(commentsCallback);
                break;
            case "Albums":
                Call<Albums> albumsCall = myAPIService.getAlbumsById(resId);
                albumsCall.enqueue(albumsCallback);
                break;
            case "Photos":
                Call<Photos> photosCall = myAPIService.getPhotosById(resId);
                photosCall.enqueue(photosCallback);
                break;
            case "Todos":
                Call<Todos> todosCall = myAPIService.getTodosById(resId);
                todosCall.enqueue(todosCallback);
                break;
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

    private int getYourInputOfPostId1() {
        String sId = editPostId1.getText().toString();

        if (sId.isEmpty())
            return 1;
        else
            return Integer.valueOf(sId);
    }

    private int getYourInputOfPostId2() {
        String sId = editPostId2.getText().toString();

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
                printResponse("Not found resource!!");
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
                printResponse("Not found resource!!");
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
            }
            else {
                printResponse("Not found resource!!");
            }
            printResponse("");
        }

        @Override
        public void onFailure(Call<Albums> call, Throwable t) {
            printResponse(t.getMessage());
        }
    };

    private Callback<Photos> photosCallback = new Callback<Photos>() {
        @Override
        public void onResponse(Call<Photos> call, Response<Photos> response) {
            Log.d(TAG, "Status code: " + response.code());
            if (response.body() != null) {
                printResponse("<albumId: " + response.body().getAlbumId());
                printResponse("<id: " + response.body().getId());
                printResponse("<title: " + response.body().getTitle());
                printResponse("<url: " + response.body().getUrl());
                printResponse("<thumbnailUrl: " + response.body().getThumbnailUrl());
            } else {
                printResponse("Not found resource!!");
            }
            printResponse("");
        }

        @Override
        public void onFailure(Call<Photos> call, Throwable t) {
            printResponse(t.getMessage());
            printResponse("");
        }
    };

    private Callback<Todos> todosCallback = new Callback<Todos>() {
        @Override
        public void onResponse(Call<Todos> call, Response<Todos> response) {
            Log.d(TAG, "Status code: " + response.code());
            if (response.body() != null) {
                printResponse("<userId: " + response.body().getUserId());
                printResponse("<id: " + response.body().getId());
                printResponse("<title: " + response.body().getTitle());
                printResponse("<completed: " + response.body().getCompleted());
            } else {
                printResponse("Not found resource!!");
            }
            printResponse("");
        }

        @Override
        public void onFailure(Call<Todos> call, Throwable t) {
            printResponse(t.getMessage());
            printResponse("");
        }
    };

    private Callback<List<Comments>> commentsListCallback = new Callback<List<Comments>>() {
        @Override
        public void onResponse(Call<List<Comments>> call, Response<List<Comments>> response) {
            Log.d(TAG, "Status code: " + response.code());
            if (response.body().size() > 0) {
                for (int i = 0; i < response.body().size(); i++) {
                    printResponse("<postId: " + response.body().get(i).getPostId());
                    printResponse("<id: " + response.body().get(i).getId());
                    printResponse("<name: " + response.body().get(i).getName());
                    printResponse("<email: " + response.body().get(i).getEmail());
                    printResponse("<body: " + response.body().get(i).getBody());
                    printResponse("---");
                }
            } else {
                printResponse("Not found resource!!");
            }
            printResponse("");
        }

        @Override
        public void onFailure(Call<List<Comments>> call, Throwable t) {
            printResponse(t.getMessage());
            printResponse("");
        }
    };
}
