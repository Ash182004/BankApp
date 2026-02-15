import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TransactionHistoryApiService } from '../../../shared/transaction-history-api.service';
import { Transaction } from '../../../core/models/transaction.model';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  standalone: true,
  imports: [CommonModule, FormsModule],
  selector: 'app-manager-transaction-history',
  templateUrl: './transaction-history.component.html',
  styleUrl: './transaction-history.component.css'
})
export class ManagerTransactionHistoryComponent {

  accountNumber = '';
  transactions: Transaction[] = [];
  message = '';
  isLoaded = false;   // 👈 track if load button was clicked

  constructor(
  private api: TransactionHistoryApiService,
  private cdr: ChangeDetectorRef
) {}
  load() {
  this.message = '';
  this.transactions = [];

  this.api.getHistory(this.accountNumber).subscribe({
    next: res => {
      this.transactions = [...res.content];
      this.isLoaded = true;     // ✅ move here
      this.cdr.detectChanges();  
    },
    error: err => {
      this.message = err.error?.detail || 'Failed to load history';
      this.isLoaded = true;
      this.cdr.detectChanges();  
    }
  });
}
}
