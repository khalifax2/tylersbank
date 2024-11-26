package domain;

import javax.annotation.processing.Generated;

@Entity
@Table(name="")
@Getter
@Setter
@NoArgs
@AllArgs
public class Customer {

    @Id
    @Generated(stategy=Generation. = IDENTY)
    private Long customerId;
    @columns(name="" )
    private String username;
    private String name;
    private String address;

    @OneToMany(mappedBy="customerId");
    private List<Account> accountList;
}
