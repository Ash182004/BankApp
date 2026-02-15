import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { TransferRequest } from '../../core/models/TransferRequest.model';

@Injectable({ providedIn: 'root' })
export class ClerkApiService {

  constructor(private http: HttpClient) {}

  deposit(accountNumber: string, amount: number) {
  return this.http.post<{ message: string }>(
    `http://localhost:8080/v1/transactions/deposit`,
    { accountNumber, amount }
  );
}

 // clerk-api.service.ts

transfer(request: TransferRequest) {
  return this.http.post('http://localhost:8080/v1/transactions/transfer', request);
}




 withdraw(accountNumber: string, amount: number) {
  return this.http.post<any>(
    `http://localhost:8080/v1/transactions/withdraw`,
    { accountNumber, amount }
  );
}
}
