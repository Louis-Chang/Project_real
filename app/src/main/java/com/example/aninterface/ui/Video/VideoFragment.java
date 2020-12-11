package com.example.aninterface.ui.Video;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.example.aninterface.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class VideoFragment extends Fragment {

    private VideoViewModel myMenuViewModel;
    private VideoIdentificationFragment videoIdentificationFragment;
    private FragmentManager fragmentManager;
    private YouTubePlayerView youTubePlayerView;
    private Button btn_video_again;
    private Button btn_video_start;
    private StorageReference videoRef;
    private StorageMetadata storageMetadata;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myMenuViewModel =
                ViewModelProviders.of(this).get(VideoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_video, container, false);
        btn_video_again = (Button) root.findViewById(R.id.btn_video_again);
        btn_video_start = (Button) root.findViewById(R.id.btn_video_start);
        youTubePlayerView = root.findViewById(R.id.youtube_player_video);

        videoRef = FirebaseStorage.getInstance().getReference().child("Course_videos/IMG_6262.jpg");
        videoRef.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
            @Override
            public void onSuccess(StorageMetadata storageMetadata) {
                Log.e("check CS", "succeed");
                Toast.makeText(getActivity(), "succeed", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("check CS", e.toString());
                Toast.makeText(getActivity(), "Uh-oh, an error occurred", Toast.LENGTH_SHORT).show();
            }
        });

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onApiChange(YouTubePlayer youTubePlayer) {
                super.onApiChange(youTubePlayer);
                youTubePlayer.loadVideo("hvtrkKrfP9w", 0f);
            }
        });

        btn_video_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoIdentificationFragment = new VideoIdentificationFragment();
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, videoIdentificationFragment).addToBackStack(null).commit();
            }
        });

        return root;
    }
}
