import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class AuthService {

  login(token: string) {
    localStorage.setItem('token', token);
  }

  logout() {
    localStorage.clear();
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  isLoggedIn(): boolean {
    const token = this.getToken();
    if (!token) return false;

    const payload = this.decodeToken(token);
    if (!payload) return false;

    // exp is in seconds
    const isExpired = Date.now() > payload.exp * 1000;
    if (isExpired) {
      this.logout();
      return false;
    }

    return true;
  }

  getUserRole(): string | null {
    const payload = this.decodeToken(this.getToken());
    return payload?.role ?? null;
  }

  private decodeToken(token: string | null): any | null {
    if (!token) return null;

    try {
      const payload = token.split('.')[1];
      return JSON.parse(atob(payload));
    } catch {
      return null;
    }
  }
}
