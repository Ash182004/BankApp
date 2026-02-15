import { Component, ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ClerkApiService } from '../clerk-api.service';

@Component({
  standalone: true,
  imports: [FormsModule, CommonModule],
  selector: 'app-withdraw',
  templateUrl: './withdraw.component.html',
  styleUrl: './withdraw.component.css'
})
export class WithdrawComponent {

  accountNumber: string = '';
  amount: number = 0;

  successMessage: string = '';
  errorMessage: string = '';
  isLoading: boolean = false;

  constructor(
    private api: ClerkApiService,
    private cdr: ChangeDetectorRef
  ) {}

  withdraw() {

    this.successMessage = '';
    this.errorMessage = '';

    // ✅ Empty validation
    if (!this.accountNumber || this.amount <= 0) {
      this.errorMessage = 'Enter the details ❌';
      this.autoHideMessage();
      return;
    }

    // ✅ If amount > 2,00,000 → show pending
    if (this.amount > 200000) {
      this.successMessage = 'Transaction Pending for approval ⏳';
      this.autoHideMessage();
      return;
    }

    this.isLoading = true;

    this.api.withdraw(this.accountNumber, this.amount).subscribe({
      next: (res: any) => {
  this.isLoading = false;

  this.successMessage =
    res?.message || 'Withdrawal successful ✅';

  this.accountNumber = '';
  this.amount = 0;

  this.cdr.detectChanges();
  this.autoHideMessage();
},

      error: (err) => {
        this.isLoading = false;

        console.log('Full error:', err);

        // ✅ Show real backend message (like Insufficient balance)
        this.errorMessage =
          err.error?.detail ||
          err.error?.message ||
          err.error?.error ||
          'Withdraw failed ❌';

        this.cdr.detectChanges();
        this.autoHideMessage();
      }
    });
  }

  // ✅ Must be outside withdraw()
  autoHideMessage() {
    setTimeout(() => {
      this.successMessage = '';
      this.errorMessage = '';
    }, 2000);
  }
}
