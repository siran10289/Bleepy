/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package bleepy.pack.com.bleepy;

import android.support.multidex.MultiDexApplication;

import com.google.firebase.iid.FirebaseInstanceId;
import com.karumi.dexter.Dexter;

import bleepy.pack.com.bleepy.di.component.ApplicationComponent;
import bleepy.pack.com.bleepy.di.component.DaggerApplicationComponent;
import bleepy.pack.com.bleepy.di.module.ApplicationModule;


/**
 * Android Main Application
 */
public class BleepyApplication extends MultiDexApplication {
  public static String refreshedFCMToken;
  private ApplicationComponent applicationComponent;
  @Override
  public void onCreate() {
    super.onCreate();
    refreshedFCMToken = FirebaseInstanceId.getInstance().getToken();

    this.initializeInjector();
    Dexter.initialize(this);
  }

  private void initializeInjector() {
    this.applicationComponent = DaggerApplicationComponent.builder()
        .applicationModule(new ApplicationModule(this))
        .build();
  }

  public  ApplicationComponent getApplicationComponent() {
    return this.applicationComponent;
  }

}
