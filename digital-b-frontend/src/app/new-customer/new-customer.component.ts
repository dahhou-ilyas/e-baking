import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-new-customer',
  templateUrl: './new-customer.component.html',
  styleUrl: './new-customer.component.css'
})
export class NewCustomerComponent implements OnInit{
  newCustomerFormGroup! : FormGroup;

  constructor(private fb:FormBuilder){}

  ngOnInit(): void {
      this.newCustomerFormGroup=this.fb.group({
        name:this.fb.control(null),
        email:this.fb.control(null)
      });
  }

  handleSubmitCustomer(){
    
  }
}
