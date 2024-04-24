import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { CustomersService } from '../services/customers.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrl: './customers.component.css'
})
export class CustomersComponent implements OnInit{
  customers$ !:Observable<any>;
  errorMessage! :string;
  constructor(private customerService:CustomersService){}

  ngOnInit(): void {
    this.customers$=this.customerService.getCustomers();
  }

}
