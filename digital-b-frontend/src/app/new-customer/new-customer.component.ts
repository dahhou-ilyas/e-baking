import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Customer } from '../model/customer.model';
import { CustomersService } from '../services/customers.service';

@Component({
  selector: 'app-new-customer',
  templateUrl: './new-customer.component.html',
  styleUrl: './new-customer.component.css'
})
export class NewCustomerComponent implements OnInit{
  newCustomerFormGroup! : FormGroup;

  constructor(private fb:FormBuilder,private customerService:CustomersService){}

  ngOnInit(): void {
      this.newCustomerFormGroup=this.fb.group({
        name:this.fb.control(null,[Validators.required]),
        mail:this.fb.control(null,[Validators.required,Validators.email]),
        phoneNumber:this.fb.control(null,[Validators.required])
      });
  }

  handleSubmitCustomer(){
    let customer:Customer =this.newCustomerFormGroup.value;
    console.log(customer);
    this.customerService.saveCustomers(customer).subscribe({
      next: data =>{
  
        alert("new cutomer added");
      },
      error:err=>{
        console.log(err);
      }
    });
  }
}
