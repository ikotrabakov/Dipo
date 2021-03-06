package com.ivaylok.github.service;


import com.ivaylok.github.BuildConfig;
import com.ivaylok.github.mvp.model.FollowersResponse;
import com.ivaylok.github.mvp.model.FollowingsResponse;
import com.ivaylok.github.mvp.model.NewsResponse;
import com.ivaylok.github.mvp.model.RepoResponse;
import com.ivaylok.github.mvp.model.SingleRepoResponse;
import com.ivaylok.github.mvp.model.StarsResponse;
import com.ivaylok.github.mvp.model.UserResponse;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import rx.Observable;

public interface GithubService {

    String api_key = BuildConfig.API_KEY;


//    Registered user -----------

    @Headers({"Accept: application/vnd.github.v3+json", "Authorization: token " + api_key})
    @GET("/user/repos")
    Observable<List<RepoResponse>> getRepos();

    @Headers({"Accept: application/vnd.github.v3+json", "Authorization: token " + api_key})
    @GET("/user/followers")
    Observable<List<FollowersResponse>> getFollowers();



//    Public -----------------

    @Headers({"Accept: application/vnd.github.v3+json", "Authorization: token " + api_key})
    @GET("/users/{user}")
    Observable<UserResponse> getPublicUser(@Path("user") String user);

    @Headers({"Accept: application/vnd.github.v3+json", "Authorization: token " + api_key})
    @GET("/users/{user}/received_events")
    Observable<List<NewsResponse>> getPublicNews(@Path("user") String user);

    @Headers({"Accept: application/vnd.github.v3+json", "Authorization: token " + api_key})
    @GET("/users/{user}/repos")
    Observable<List<RepoResponse>> getPublicRepos(@Path("user") String user);

    @Headers({"Accept: application/vnd.github.v3+json", "Authorization: token " + api_key})
    @GET("/users/{user}/starred")
    Observable<List<StarsResponse>> getPublicStars(@Path("user") String user);

    @Headers({"Accept: application/vnd.github.v3+json", "Authorization: token " + api_key})
    @GET("/users/{user}/followers")
    Observable<List<FollowersResponse>> getPublicFollowers(@Path("user") String user);

    @Headers({"Accept: application/vnd.github.v3+json", "Authorization: token " + api_key})
    @GET("/users/{user}/following")
    Observable<List<FollowingsResponse>> getPublicFollowings(@Path("user") String user);

    @Headers({"Accept: application/vnd.github.v3+json", "Authorization: token " + api_key})
    @GET("/repos/{user}/{repository}/contents{path}")
    Observable<List<SingleRepoResponse>> getSingleRepo(@Path("user") String user, @Path("repository") String repository, @Path("path") String path);

}
