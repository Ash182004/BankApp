import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AccountApiService {

  private baseUrl = 'http://localhost:8080/v1/accounts';

  constructor(private http: HttpClient) {}

  createAccount(holderName: string, initialBalance: number): Observable<any> {
    return this.http.post(this.baseUrl, {
      holderName,
      initialBalance
    });
  }
}
