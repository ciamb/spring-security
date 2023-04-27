import { Component, OnInit } from '@angular/core';
import {StorageService} from "../_services/storage.service";

//mostra la pagine del profile dello user loggato, che sia admin o user
@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  currentUser : any;


  constructor(private storageService : StorageService) { }

  ngOnInit(): void {
    this.currentUser = this.storageService.getUser();
  }

}
