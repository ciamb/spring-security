import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
const TEST_API = "http://localhost:8080/api/test/"

//offre metodi per accedere alle risorse pubbliche e protette
@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http : HttpClient) { }

  getPublicContent() :Observable<any> { return this.http.get(TEST_API + 'all', { responseType : 'text'}); }

  getUserBoard() :Observable<any>{ return this.http.get(TEST_API + 'user', { responseType : 'text'}); }

  getModeratorBoard() :Observable<any>{ return this.http.get(TEST_API + 'mod', { responseType : 'text'}); }

  getAdminBoard() :Observable<any>{ return this.http.get(TEST_API + 'admin', { responseType : 'text'}); }
}
