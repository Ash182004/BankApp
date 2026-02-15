import { Routes } from '@angular/router';

export const MANAGER_ROUTES: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },

  {
    path: 'dashboard',
    loadComponent: () =>
      import('./dashboard/dashboard.component')
        .then(c => c.ManagerDashboardComponent)
  },
  {
    path: 'create-account',
    loadComponent: () =>
      import('./create-account/create-account.component')
        .then(c => c.CreateAccountComponent)
  },
  {
    path: 'approvals',
    loadComponent: () =>
      import('./approvals/approvals.component')
        .then(c => c.ApprovalsComponent)
  },
  {
    path: 'transactions',
    loadComponent: () =>
      import('./transaction-history/transaction-history.component')
        .then(c => c.ManagerTransactionHistoryComponent)
  }
];
