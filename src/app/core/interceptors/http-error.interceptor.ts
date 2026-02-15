import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';

export const httpErrorInterceptor: HttpInterceptorFn = (req, next) => {
  const router = inject(Router);

  return next(req).pipe(
    catchError(err => {
      if (err.status === 401) {
        localStorage.clear();
        router.navigate(['/login']);
      }

      // http-error-interceptor.ts
if (err.status === 403) {
  console.error('Server 403 Reason:', err.error); // Log the actual server message
  // router.navigate(['/forbidden']); // Comment this out temporarily to debug
}

      return throwError(() => err);
    })
  );
};
