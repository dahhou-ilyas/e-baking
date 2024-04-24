import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { CustomersService } from '../services/customers.service';

@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrl: './customers.component.css'
})
export class CustomersComponent implements OnInit{
  customers:any;
  errorMessage! :string;
  constructor(private customerService:CustomersService){}

  ngOnInit(): void {
    this.customerService.getCustomers().subscribe({
      next:(data)=>{
        this.customers=data;
      },
      error:(err)=>{
        this.errorMessage=err.message;
      }
    })
  }

}
