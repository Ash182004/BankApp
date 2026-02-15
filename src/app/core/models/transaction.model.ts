import { Account } from './account.model';

export interface Transaction {
  transactionId: string;
  type: 'DEPOSIT' | 'WITHDRAWAL';
  amount: number;
  status: 'COMPLETED' | 'PENDING_APPROVAL' | 'REJECTED';
  account: Account;
  createdAt: string;   // ✅ real timestamp from backend
}
