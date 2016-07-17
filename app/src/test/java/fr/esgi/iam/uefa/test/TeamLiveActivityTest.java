package fr.esgi.iam.uefa.test;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import fr.esgi.iam.uefa.app.MyApplication;
import fr.esgi.iam.uefa.model.Action;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by MichaelWayne on 15/06/2016.
 */
public class TeamLiveActivityTest {

    @Mock
    MyApplication app;

    @Before
    public void setUp() throws Exception {
        app = new MyApplication();
    }

    @Test
    public void testOnCreate() throws Exception {

        app = mock(MyApplication.class);
        app.getUefaRestClient().getApiService().getActions(new Callback<List<Action>>() {
            @Override
            public void success(List<Action> actions, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }
}