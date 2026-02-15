import { Component, ChangeDetectorRef } from '@angular/core';

import { ClerkApiService } from '../clerk-api.service';
import { Router } from '@angular/router';
import { TransferRequest } from '../../../core/models/TransferRequest.model';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-transfer',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './transfer.component.html',
  styleUrl: './transfer.component.css'
})
export class TransferComponent {

  fromAccountId: string = '';
  toAccountId: string = '';
  amount: number = 0;

  isLoading = false;
  successMessage = '';
  errorMessage = '';

  constructor(
  private accountService: ClerkApiService,
  private router: Router,
  private cd: ChangeDetectorRef   // 👈 add this
) {}


  onSubmit(): void {

    this.successMessage = '';
    this.errorMessage = '';

    const request: TransferRequest = {
  fromAccountNumber: this.fromAccountId,
  toAccountNumber: this.toAccountId,
  amount: this.amount
};


    this.isLoading = true;

    this.accountService.transfer(request).subscribe({
      next: () => {
        this.isLoading = false;
        this.successMessage = 'Transfer successful ✅';
        this.fromAccountId = '';
  this.toAccountId = '';
  this.amount = 0;
   this.cd.detectChanges(); 
      },
      error: (err) => {
        this.isLoading = false;
        this.errorMessage = err.error?.detail || 'Transfer failed ❌';
         this.cd.detectChanges(); 
      }
    });
  }
}