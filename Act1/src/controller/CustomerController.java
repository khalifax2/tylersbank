package controller;

import serrvice.impl.CustomerService;

@RestController
@RequiredContructor
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;


    @GetMapping("/getAllAccounts")
    public ResponseEntity<List<CustomerDetailsDto>> getAllAccounts(@RequestParam String customerId) {
        List<CustomerDetailsDto> customerDetailsDtoList = customerService.getAllAccounts(customerId);
        return new ResponseEntity.body(customerDetailsDtoList).status(HttpStatus.OK);
    }

    
}
