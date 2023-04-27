import { Component } from '@angular/core';
import {StorageService} from "./_services/storage.service";
import {AuthService} from "./_services/auth.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  private roles: string[] = [];
  isLoggedIn = false;
  showAdminBoard = false;
  showModeratorBoard = false;
  username?: string;

  title = 'spring-security-demo_FE';

  constructor(private storageService : StorageService, private authService : AuthService) {

  }


  ngOnInit() : void {
    //check per vedere se sei loggato
    this.isLoggedIn = this.storageService.isLoggedIn();
    //se lo sei allora prende user role e setta il valore in base al ruolo a riga 34-35
    if (this.isLoggedIn) {
      const user = this.storageService.getUser();
      this.roles = user.roles;

      //si setta i booleani in base ai ruoli per mostrare le sezioni dei ruoli
      this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
      this.showModeratorBoard = this.roles.includes('ROLE_MODERATOR');

      this.username = user.username;
    }
  }

  logout(): void {
    this.authService.logout().subscribe({
      next: res => {
        console.log(res);
        this.storageService.clean();

        window.location.reload();
      },
      error: err => {
        console.log(err);
      }
    });
  }
}
