import { Component, ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ClerkApiService } from '../clerk-api.service';

@Component({
  standalone: true,
  imports: [FormsModule, CommonModule],
  selector: 'app-deposit',
  templateUrl: './deposit.component.html',
  styleUrls: ['./deposit.component.css']
})
export class DepositComponent {

  accountNumber: string = '';
  amount: number | null = null;

  successMessage: string = '';
  errorMessage: string = '';

  constructor(
    private api: ClerkApiService,
    private cdr: ChangeDetectorRef
  ) {}

  deposit() {

    // Clear old messages
    this.successMessage = '';
    this.errorMessage = '';

    // Validation
    if (!this.accountNumber?.trim() || this.amount === null || this.amount <= 0) {
      this.errorMessage = 'Enter valid account number and amount ❌';
      this.autoHideMessage();
      return;
    }

    this.api.deposit(this.accountNumber, this.amount).subscribe({
      next: (res: any) => {

        // ✅ Show backend message correctly
        this.successMessage =
          res?.message || 'Deposit successful ✅';

        this.errorMessage = '';

        this.accountNumber = '';
        this.amount = null;

        this.cdr.detectChanges();
        this.autoHideMessage();
      },

      error: (err) => {

        console.log('Full error:', err);

        // ✅ Handle Spring Boot problem+json
        this.errorMessage =
          err.error?.detail ||
          err.error?.message ||
          err.error?.error ||
          'Deposit failed ❌';

        this.successMessage = '';

        this.cdr.detectChanges();
        this.autoHideMessage();
      }
    });
  }

  autoHideMessage() {
    setTimeout(() => {
      this.successMessage = '';
      this.errorMessage = '';
    }, 2000);
  }
}
