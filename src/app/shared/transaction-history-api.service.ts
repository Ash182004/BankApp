import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Transaction } from '../core/models/transaction.model';

@Injectable({ providedIn: 'root' })
export class TransactionHistoryApiService {

  constructor(private http: HttpClient) {}

  getHistory(
    accountNumber: string,
    page = 0,
    size = 10
  ): Observable<{ content: Transaction[] }> {

    return this.http.get<{ content: Transaction[] }>(
      `${environment.apiUrl}/transactions/history/${accountNumber}?page=${page}&size=${size}`
    );
  }
}
