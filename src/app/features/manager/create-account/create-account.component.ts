import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AccountApiService } from '../../../core/services/account-api.service';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  standalone: true,
  imports: [FormsModule, CommonModule],
  selector: 'app-create-account',
  templateUrl: './create-account.component.html',
  styleUrl: './create-account.component.css'
})
export class CreateAccountComponent {

  name: string = '';
  initialBalance: number = 0;

  successMessage: string = '';
  errorMessage: string = '';
  isLoading: boolean = false;

  constructor(
  private accountApi: AccountApiService,
  private cdr: ChangeDetectorRef
) {}

 createAccount(form: any) {

  if (form.invalid || this.initialBalance <= 1000) {
    this.errorMessage = 'Amount must be greater than 1000';
    this.successMessage = '';
    this.autoHideMessage();
    return;
  }

  this.isLoading = true;
  this.successMessage = '';
  this.errorMessage = '';

  this.accountApi.createAccount(this.name, this.initialBalance)
    .subscribe({
      next: () => {
        this.isLoading = false;
        this.successMessage = 'Account created successfully ✅';
        form.reset();
        this.cdr.detectChanges();
        this.autoHideMessage();
      },
      error: () => {
        this.isLoading = false;
        this.errorMessage = 'Failed to create account ❌';
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