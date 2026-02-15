import { inject } from '@angular/core';
import { CanActivateFn, ActivatedRouteSnapshot, Router } from '@angular/router';
import { AuthService } from './auth.service';

export const roleGuard: CanActivateFn = (route: ActivatedRouteSnapshot) => {
  const auth = inject(AuthService);
  const router = inject(Router);

  const requiredRole = route.data['role'];
  const userRole = auth.getUserRole();

  if (userRole === requiredRole) return true;

  router.navigate(['/forbidden']);
  return false;
};
