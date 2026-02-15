import { bootstrapApplication } from '@angular/platform-browser';
import { provideHttpClient, withInterceptors } from '@angular/common/http';

import { App } from './app/app';
import { appConfig } from './app/app.config';

import { jwtInterceptor } from './app/core/interceptors/jwt.interceptor';
import { httpErrorInterceptor } from './app/core/interceptors/http-error.interceptor';

bootstrapApplication(App, {
  providers: [
    // ✅ keep router & other providers
    ...(appConfig.providers ?? []),

    // ✅ activate HttpClient + interceptors
    provideHttpClient(
      withInterceptors([
        jwtInterceptor,
        httpErrorInterceptor
      ])
    )
  ]
}).catch(err => console.error(err));
