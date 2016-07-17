package fr.esgi.iam.uefa;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import fr.esgi.iam.uefa.activities.TeamLiveActivity;
import fr.esgi.iam.uefa.app.MyApplication;
import fr.esgi.iam.uefa.model.Action;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by MichaelWayne on 15/06/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class TeamLiveTest {


    MyApplication spyed;
    @Mock
    TeamLiveActivity activity;
    String test = "test";
    String BASE_ENDPOINT = "http://dylan.absil.pro/projet/api";

    @Before
    public void setUp() throws Exception{
        activity = new TeamLiveActivity();
        spyed = new MyApplication();
    }

    @Test
    public void testContent() throws Exception{

        //Given
        spyed = spy(MyApplication.class);
        doNothing().when(spyed);

        //app = spy(app);
        spyed.getUefaRestClient().getApiService().getActions(new Callback<List<Action>>() {
            @Override
            public void success(List<Action> actions, Response response) {


                //When
                for (Action action : actions){
                    System.out.println(action.getComment());
                }

                //Then

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }
}
