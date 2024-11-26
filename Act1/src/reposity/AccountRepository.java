package reposity;

@Repository
public interface AccountRepository  extends JpaRepository<CustomerRepository, Long> {
}
