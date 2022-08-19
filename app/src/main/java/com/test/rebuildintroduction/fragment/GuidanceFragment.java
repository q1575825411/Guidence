package com.test.rebuildintroduction.fragment;

import static com.test.rebuildintroduction.utils.ExpandableViewHoldersUtil.imageAnimation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.rebuildintroduction.adapter.MainAdapter;
import com.test.rebuildintroduction.base.BaseFragment;
import com.test.rebuildintroduction.bean.GuideBean;
import com.test.rebuildintroduction.databinding.FragmentT2sBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 大屏幕Fragment
 *
 * @author sm2887
 */
public class GuidanceFragment extends BaseFragment {
    private static final String DEVICE_NAME = "DEVICE_NAME";

    private static final String TAG = "GuidanceFragment";
    private String mDeviceName;
    private Context mContext;
    private FragmentT2sBinding mFragmentT2sBinding;
    private MainAdapter mAdapter;
    private List<GuideBean> mData;

    public GuidanceFragment() {
        // Required empty public constructor

    }

    public static GuidanceFragment newInstance(String deviceName) {
        GuidanceFragment fragment = new GuidanceFragment();
        Bundle args = new Bundle();
        args.putString(DEVICE_NAME, deviceName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        if (getArguments() != null) {
            mDeviceName = getArguments().getString(DEVICE_NAME);
        }
        initData();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentT2sBinding = FragmentT2sBinding.inflate(getLayoutInflater(), container, false);
        mFragmentT2sBinding.backgroundVideo.setOnPreparedListener(mediaPlayer -> mediaPlayer.setOnInfoListener((mediaPlayer1, i, i1) -> {
            if (i == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                imageAnimation(mFragmentT2sBinding, 1, 0);
                mFragmentT2sBinding.buttonPlay.setVisibility(View.GONE);
            }
            return true;
        }));
        mFragmentT2sBinding.backgroundVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                imageAnimation(mFragmentT2sBinding, 0, 1);
                mFragmentT2sBinding.buttonPlay.setVisibility(View.VISIBLE);
            }
        });
        mAdapter = new MainAdapter(mData, (view, position, isExpanded) -> {
            if (!isExpanded) {
                mFragmentT2sBinding.backgroundVideo.setVideoURI(Uri.parse("android.resource://" + mContext.getPackageName() + "/" + mData.get(position).getResourceId()));
                mFragmentT2sBinding.backgroundVideo.start();
            }
        });

        initView();
        recyclerInit();
        mFragmentT2sBinding.buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageAnimation(mFragmentT2sBinding, 1, 0);
                mFragmentT2sBinding.buttonPlay.setVisibility(View.GONE);
                mFragmentT2sBinding.backgroundVideo.start();
            }
        });
        return mFragmentT2sBinding.getRoot();
    }

    @Override
    public void onStop() {
        /* 防止锁屏或者弹出的时候，音乐在播放 */
        mFragmentT2sBinding.backgroundVideo.stopPlayback();
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        mFragmentT2sBinding.backgroundVideo.start();
    }

    /**
     * 初始化数据
     * 对型号做判断，拿到对应型号下的数据
     */
    @Override
    public void initData() {
        mDeviceName = Build.MODEL;
        // TODO 数据 Base
        int modelId = stringToId(mDeviceName);
        // 为了能够在虚拟机运行成功
        if (modelId == 0) {
            mDeviceName = "T2s";
            modelId = stringToId(mDeviceName);
        }

        TypedArray menuResources = getResources().obtainTypedArray(modelId);

        @SuppressLint({"ResourceType"})
        TypedArray titles = getResources().obtainTypedArray(menuResources.getResourceId(1, -1));
        @SuppressLint({"ResourceType"})
        TypedArray des = getResources().obtainTypedArray(menuResources.getResourceId(2, -1));
        @SuppressLint({"ResourceType"})
        TypedArray ids = getResources().obtainTypedArray(menuResources.getResourceId(3, -1));

        assert titles.length() == des.length() : "list中title的数量应与description的数量相等";
        int itemNum = titles.length();
        mData = new ArrayList<>();
        for (int i = 0; i < itemNum; i++) {
            mData.add(new GuideBean(ids.getResourceId(i, 0), titles.getString(i), des.getString(i)));
        }
        ids.recycle();
        des.recycle();
        titles.recycle();
        menuResources.recycle();
    }

    @Override
    public void initView() {
        // 初始化隐藏按钮
        mFragmentT2sBinding.buttonPlay.setVisibility(View.GONE);
        mFragmentT2sBinding.deviceName .setText(mDeviceName);
    }

    /**
     * 初始化RecyclerView
     */
    private void recyclerInit() {
        final RecyclerView recyclerView = mFragmentT2sBinding.rv;
        ((DefaultItemAnimator) Objects.requireNonNull(recyclerView.getItemAnimator())).setSupportsChangeAnimations(false);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.addItemDecoration(new MyDividerItemDecoration(mContext, layoutManager.getOrientation()));
        recyclerView.setAdapter(mAdapter);
    }

}
