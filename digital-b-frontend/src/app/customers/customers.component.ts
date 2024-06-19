import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { CustomersService } from '../services/customers.service';
import { Observable, catchError, map, throwError } from 'rxjs';
import { Customer } from '../model/customer.model';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrl: './customers.component.css'
})
export class CustomersComponent implements OnInit{
  customers$ !:Observable<Array<Customer>>;
  errorMessage! :string;
  searchFormGroup! : FormGroup;
  constructor(private customerService:CustomersService,private fb:FormBuilder){}

  ngOnInit(): void {
    this.searchFormGroup=this.fb.group({
      keyword:this.fb.control("")
    })
    this.handleSearchCustomers()
  }

  handleDeleteCustomer(c:Customer){
    let conf = confirm("Are you sure ?")
    if(conf){
      this.customerService.deleteCustomers(c.id).subscribe({
        next:data=>{
          this.customers$=this.customers$.pipe(
            map(data=>{
              let index=data.indexOf(c);
              data.slice(index,1)
              return data;
            })
          )
        },
        error:err=>{
          console.log(err);
        }
      });
    }
    
  }

  handleSearchCustomers(){
    let kw=this.searchFormGroup?.value.keyword;
    this.customers$=this.customerService.searchCustomers(kw).pipe(
      catchError(err=>{
        this.errorMessage=err.message;
        return throwError(() => new Error(err));
      })
    );
  }

}
