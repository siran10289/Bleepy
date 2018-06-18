/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package bleepy.pack.com.bleepy.di.component;

import android.content.Context;



import javax.inject.Singleton;

import bleepy.pack.com.bleepy.apiservice.Api;
import bleepy.pack.com.bleepy.di.module.ApplicationModule;
import bleepy.pack.com.bleepy.di.module.NetworkModule;
import bleepy.pack.com.bleepy.di.module.PresentationModule;
import bleepy.pack.com.bleepy.di.scope.GsonRestAdapter;
import bleepy.pack.com.bleepy.interactor.ApiInteractor;
import bleepy.pack.com.bleepy.utils.preferences.PrefsManager;
import bleepy.pack.com.bleepy.view.base.BaseActivity;
import bleepy.pack.com.bleepy.view.signup.PackageInfoInteractor;
import dagger.Component;
import retrofit2.Retrofit;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = {ApplicationModule.class, NetworkModule.class, PresentationModule.class})
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);

    //Exposed to sub-graphs.
    Context context();
    Api apiHal();
    @GsonRestAdapter
    Api apiGson();
    Retrofit retrofit();
    ApiInteractor driverInteractor();
    PrefsManager prefsManager();
    PackageInfoInteractor packageInteractor();
}
