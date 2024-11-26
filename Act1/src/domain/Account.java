package domain;

import javax.annotation.processing.Generated;

@Entity
@Table(name="")
@Getter
@Setter
@NoArgs
@AllArgs
public class Account {

    @Id
    @Generated(stategy=Generation. = IDENTY)
    private Long accountId;
    private String accountNo;
    private String accountName;

    @ManyToOne
    @JoinColumn(name="customerId")
    private String customerId;

}
