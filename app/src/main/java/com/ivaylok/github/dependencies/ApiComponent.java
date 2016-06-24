package com.ivaylok.github.dependencies;

import com.ivaylok.github.mvp.view.fragment.FollowersFragment;
import com.ivaylok.github.mvp.view.fragment.FollowingsFragment;
import com.ivaylok.github.mvp.view.fragment.RepositoriesFragment;

import dagger.Component;

@CustomScope
@Component(modules = ApiModule.class, dependencies = NetworkComponent.class)
public interface ApiComponent {

    void inject(RepositoriesFragment fragment);
    void inject(FollowersFragment fragment);
    void inject(FollowingsFragment fragment);
}
