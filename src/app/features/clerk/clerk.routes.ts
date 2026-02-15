import { Routes } from '@angular/router';

export const CLERK_ROUTES: Routes = [
  { path: '', component: undefined, redirectTo: 'dashboard', pathMatch: 'full' },

  {
    path: 'dashboard',
    loadComponent: () =>
      import('./dashboard/dashboard.component')
        .then(c => c.ClerkDashboardComponent)
  },
  {
    path: 'deposit',
    loadComponent: () =>
      import('./deposit/deposit.component')
        .then(c => c.DepositComponent)
  },
  {
    path: 'withdraw',
    loadComponent: () =>
      import('./withdraw/withdraw.component')
        .then(c => c.WithdrawComponent)
  },
  {
  path: 'transfer',
  loadComponent: () =>
    import('./transfer/transfer.component')
      .then(c => c.TransferComponent)
}
  
];
