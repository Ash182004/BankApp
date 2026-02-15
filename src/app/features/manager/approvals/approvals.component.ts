import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators'; // Added for logging
import { PendingApproval } from '../../../core/models/pending-approval.model';
import { ManagerApiService } from '../manager-api.service';

@Component({
  standalone: true,
  imports: [CommonModule],
  selector: 'app-approvals',
  templateUrl: './approvals.component.html',
  styleUrl: './approvals.component.css'
})
export class ApprovalsComponent implements OnInit {
  // 1. Define as an Observable to work with the 'async' pipe
  pendingTransactions$!: Observable<PendingApproval[]>;

  constructor(private api: ManagerApiService) {}

  ngOnInit() {
    this.load();
  }

  load() {
    // 2. Direct assignment of the stream. 
    // We use .pipe(tap(...)) to log data without 'consuming' the subscription.
    this.pendingTransactions$ = this.api.getPendingApprovals().pipe(
      tap(data => console.log("Data received from API:", data))
    );
  }

  approve(transactionId: string) {
    this.api.approve(transactionId).subscribe(() => this.load());
  }

  reject(transactionId: string) {
    this.api.reject(transactionId).subscribe(() => this.load());
  }
}