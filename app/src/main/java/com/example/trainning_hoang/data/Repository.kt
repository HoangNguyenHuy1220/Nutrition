package com.example.trainning_hoang.data

import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    val remote: RemoteDataSource,
    val local: LocalDataSource
)