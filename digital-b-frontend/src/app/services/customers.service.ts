import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CustomersService {

  constructor(private http:HttpClient) { }

  public getCustomers():Observable<any>{
    return this.http.get("http:d//localhost:8080/customers");
  }
}
