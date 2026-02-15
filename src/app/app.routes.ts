import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login/login.component';
import { ForbiddenComponent } from './shared/error/forbidden.component';
import { NotFoundComponent } from './shared/error/not-found.component';
import { authGuard } from './core/auth/auth.guard';
import { roleGuard } from './core/auth/role.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },

  { path: 'login', component: LoginComponent },

  {
    path: 'manager',
    canActivate: [authGuard, roleGuard],
    data: { role: 'MANAGER' },
    loadChildren: () =>
      import('./features/manager/manager.routes')
        .then(m => m.MANAGER_ROUTES)
  },

  {
    path: 'clerk',
    canActivate: [authGuard, roleGuard],
    data: { role: 'CLERK' },
    loadChildren: () =>
      import('./features/clerk/clerk.routes')
        .then(c => c.CLERK_ROUTES)
  },

  { path: 'forbidden', component: ForbiddenComponent },
  { path: '**', component: NotFoundComponent }
];

