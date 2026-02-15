import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { environment } from '../../../environments/environment';
import { PendingApproval } from '../../core/models/pending-approval.model';
import { HttpHeaders } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class ManagerApiService {
  constructor(private http: HttpClient) {}

  getPendingApprovals(): Observable<PendingApproval[]> {
    return this.http.get<PendingApproval[]>(`${environment.apiUrl}/approvals/pending`);
  }

  approve(transactionId: string): Observable<string> {
    return this.http.post(`${environment.apiUrl}/approvals/${transactionId}/approve`, {}, { responseType: 'text' });
  }

  reject(transactionId: string): Observable<string> {
    return this.http.post(`${environment.apiUrl}/approvals/${transactionId}/reject`, {}, { responseType: 'text' });
  }
}