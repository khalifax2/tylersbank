package serrvice.impl;

import domain.Customer;
import dto.CustomerDetailsDto;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredAgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private List<CustomerDetailsDto> getAllAccounts(String customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> throw);
        List<Account> accountList = customer.get().getaccountList();
        return accountList.stream()
                .map(acc -> new CustomerDetailsDto(customer.getusername(), customer.name, acc.getAccountName, acc.getAccountNo))
                .collect(Collectors.toList());
    }

}
