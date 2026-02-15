import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/auth/auth.service';
import { AuthApiService } from '../../../core/auth/auth-api.service';

@Component({
  standalone: true,
  imports: [FormsModule],
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  username: string = '';
  password: string = '';

  constructor(
    private api: AuthApiService,
    private auth: AuthService,
    private router: Router
  ) {}

  login() {
  this.api.login(this.username, this.password).subscribe({
    next: (res) => {
      this.auth.login(res.token);

      const role = this.auth.getUserRole()?.toLowerCase();
      this.router.navigate([role]);
    },
    error: (err) => {
      console.error('Login error:', err);
      alert('Login failed');
    }
  });
}

}
