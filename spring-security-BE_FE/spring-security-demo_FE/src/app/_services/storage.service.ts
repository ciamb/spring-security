import { Injectable } from '@angular/core';
const USER_KEY = 'auth-user';

//gestisce le informazione dell utente all interno della sessione del browser,
//questo la fa grazie all'oggetto window
// sessione che verrà pulita con il logout

@Injectable({
  providedIn: 'root'
})
export class StorageService {
  constructor() { }

  clean() : void {

    window.sessionStorage.clear();
  }

  public saveUser(user : any) : void  {

    window.sessionStorage.removeItem(USER_KEY);

    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  public getUser() : any{

    const user = window.sessionStorage.getItem(USER_KEY);

    if (user){ return JSON.parse(user); }

    return  { } ;
  }

  public isLoggedIn() : boolean {

    const user = window.sessionStorage.getItem(USER_KEY);

    if (user){ return true; }

    return false;
  }
}

