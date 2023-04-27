import {Injectable} from "@angular/core";
import {HTTP_INTERCEPTORS, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable()
export class HttpRequestInterceptor implements HttpInterceptor{

  //prende in update una richiesta http(tipo login) e la trasforma in un observable
  //e in next un oggetto httphandler che non sarà altro che la richiesta succesiva
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    req = req.clone({withCredentials : true});
    return next.handle(req);

  }
}

export const httpInterceptorProviders = [{ provide : HTTP_INTERCEPTORS, useClass : HttpRequestInterceptor, multi : true }];


