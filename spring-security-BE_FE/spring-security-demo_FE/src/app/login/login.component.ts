import { Component, OnInit } from '@angular/core';
import {AuthService} from "../_services/auth.service";
import {StorageService} from "../_services/storage.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
//form per il login
  form: any = {
    username: null,
    password: null
  };
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];

  constructor(private authService : AuthService, private storageService : StorageService) { }

  ngOnInit(): void {

    //check se è loggato
    if (this.storageService.isLoggedIn()){
      this.isLoggedIn = true;
      this.roles = this.storageService.getUser().roles;
    }

  }

  onSubmit() {


    const { username, password } = this.form;

    //invia tramite authService la login
    this.authService.login(username, password).subscribe({
      next: data => {
        this.storageService.saveUser(data); //salva lo user in sessione

        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.roles = this.storageService.getUser().roles;
        this.reloadPage();
      },
      error: err => {
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;
      }
    });


  }


  reloadPage() : void {
    window.location.reload();
  }

}
