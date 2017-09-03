package com.gamedev.firebasedemo;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.SharedPreferences;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

@RunWith(MockitoJUnitRunner.class)
public class RemoteConfigTest {

    @Mock
    FirebaseRemoteConfig firebaseRemoteConfig;
    @Mock
    SharedPreferences sharedPreferences;
    @Mock
    RemoteConfig.Callback callback;
    @Mock
    Task<Void> task;

    MySharedPreferences mySharedPreferences;

    @Before
    public void setUp() throws Exception {
        mySharedPreferences = new MySharedPreferences(sharedPreferences);
    }

    @Ignore("Log statements could not be mocked")
    @Test
    public void shouldSuccessfullyFetchUsername() {
        RemoteConfig remoteConfig = new RemoteConfig(firebaseRemoteConfig, mySharedPreferences);

        doAnswer(new Answer() {
            @Override
            public Object answer(final InvocationOnMock invocation) throws Throwable {
                ((OnCompleteListener<Void>) invocation.getArguments()[0]).onComplete(task);
                return task;
            }
        }).when(task).addOnCompleteListener((OnCompleteListener<Void>) any());

        when(task.isSuccessful()).thenReturn(true);
        when(firebaseRemoteConfig.fetch(anyLong())).thenReturn(task);

        remoteConfig.fetch(callback);

        verify(callback).onFinished();
        verify(firebaseRemoteConfig).activateFetched();
    }

    @Ignore("Log statements could not be mocked")
    @Test
    public void shouldFetchUsernameWhileRateLimited() {
        RemoteConfig remoteConfig = new RemoteConfig(firebaseRemoteConfig, mySharedPreferences);

        doAnswer(new Answer() {
            @Override
            public Object answer(final InvocationOnMock invocation) throws Throwable {
                ((OnCompleteListener<Void>) invocation.getArguments()[0]).onComplete(task);
                return task;
            }
        }).when(task).addOnCompleteListener((OnCompleteListener<Void>) any());

        when(task.isSuccessful()).thenReturn(false);
        when(task.getException()).thenReturn(new RuntimeException());
        when(firebaseRemoteConfig.fetch(anyLong())).thenReturn(task);

        remoteConfig.fetch(callback);

        verify(callback).onFinished();
    }

    @Ignore("Log statements could not be mocked")
    @Test
    public void shouldFailToFetchUsername() {
        RemoteConfig remoteConfig = new RemoteConfig(firebaseRemoteConfig, mySharedPreferences);

        doAnswer(new Answer() {
            @Override
            public Object answer(final InvocationOnMock invocation) throws Throwable {
                ((OnFailureListener) invocation.getArguments()[0]).onFailure(
                        new RuntimeException());
                return task;
            }
        }).when(task).addOnFailureListener((OnFailureListener) any());
        when(task.addOnCompleteListener((OnCompleteListener<Void>) any())).thenReturn(task);
        when(firebaseRemoteConfig.fetch(anyLong())).thenReturn(task);

        remoteConfig.fetch(callback);

        verify(callback).onFinished();
    }
}