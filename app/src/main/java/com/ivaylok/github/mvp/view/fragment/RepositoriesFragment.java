package com.ivaylok.github.mvp.view.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ivaylok.github.R;
import com.ivaylok.github.application.GithubApplication;
import com.ivaylok.github.mvp.presenter.RepoPresenter;
import com.ivaylok.github.mvp.view.activity.OverviewActivity;
import com.ivaylok.github.mvp.view.adapter.RepositoriesAdapter;
import com.ivaylok.github.mvp.model.RepoResponse;
import com.ivaylok.github.service.GithubService;
import com.ivaylok.github.service.RepoViewInterface;
import com.ivaylok.github.mvp.view.activity.RepoActivity;
import com.ivaylok.github.utils.GithubClickListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import rx.Observable;

/**
 * A simple {@link Fragment} subclass.
 */
public class RepositoriesFragment extends Fragment implements RepoViewInterface, GithubClickListener {

    @Inject
    GithubService mService;

    public static final String EXTRA_MESSAGE = "com.ivaylok.github.MESSAGE";
    private ProgressDialog mDialog;
    private RepoPresenter mPresenter;
    private RepositoriesAdapter mAdapter;

    RecyclerView mRecyclerView;

    public RepositoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        resolveDependency();
        ButterKnife.bind(getActivity());

        mPresenter = new RepoPresenter(RepositoriesFragment.this);
        mPresenter.onCreate();
    }

    private void resolveDependency() {
        ((GithubApplication) getActivity().getApplication())
                .getmApiComponent()
                .inject(RepositoriesFragment.this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recycler_layout, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);

        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new RepositoriesAdapter(RepositoriesFragment.this, inflater);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onClick(int position, String name) {
        OverviewActivity.mRepositoryPath = "";
        Toast.makeText(getActivity(), "You just clicked on " + name, Toast.LENGTH_SHORT).show();
        OverviewActivity.mCurrentRepository = name;
        Intent repoIntent = new Intent(getActivity(), RepoActivity.class);
        RepositoriesFragment.this.startActivity(repoIntent);
    }

    @Override
    public void onCompleted() { mDialog.dismiss(); }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
        mPresenter.fetchResponse();
        mDialog = new ProgressDialog(getActivity());
        mDialog.setIndeterminate(true);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setTitle("Downloading List");
        mDialog.setMessage("Please wait...");
        mDialog.show();
    }

    @Override
    public void onError(String message) {
        mDialog.dismiss();
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRepos(List<RepoResponse> repoResponses) {
        mAdapter.addRepos(repoResponses);
    }

    @Override
    public Observable<List<RepoResponse>> getRepos() {
        return mService.getPublicRepos(OverviewActivity.mCurrentUser);
    }
}
