export interface AccountDetails {
    accountId:            string;
    balance:              number;
    currentPage:          number;
    totalPages:           number;
    pageSize:             number;
    accountOperationDTOS: AccountOperation[];
  }
  
  export interface AccountOperation {
    id:            number;
    oprationDate: Date;
    amount:        number;
    type:          string;
    description:   string;
  }