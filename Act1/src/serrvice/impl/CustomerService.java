package serrvice.impl;

public interface CustomerService {
    List<CustomerDetailsDto> getAllAccounts(String customerId);
}
