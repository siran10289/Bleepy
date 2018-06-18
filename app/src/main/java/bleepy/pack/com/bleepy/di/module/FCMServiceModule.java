package bleepy.pack.com.bleepy.di.module;

import com.google.firebase.iid.FirebaseInstanceIdService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by siranjeevi on 07/06/17.
 */
@Module
public class FCMServiceModule {

    private final FirebaseInstanceIdService fbService;

    public FCMServiceModule(FirebaseInstanceIdService fbService) {
        this.fbService = fbService;
    }

    /**
     * Expose the service to dependents in the graph.
     */
    @Provides
    FirebaseInstanceIdService fbService() {
        return this.fbService;
    }
}
