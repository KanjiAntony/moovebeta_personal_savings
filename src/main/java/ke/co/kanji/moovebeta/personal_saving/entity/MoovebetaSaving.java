package ke.co.kanji.moovebeta.personal_saving.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class MoovebetaSaving {

    @Id
    @SequenceGenerator(
            name="moovebeta_savings_sequence",
            sequenceName = "moovebeta_savings_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "moovebeta_savings_sequence")
    public Long id;

    @Column(nullable = false)
    public String bankName;

    @Column(nullable = false)
    public String accountName;

    @Column(nullable = false)
    public Long accountNumber;

    @Column(nullable = false)
    public double amountSaved;

    @ManyToOne
    @JoinColumn(name = "account_owner_id", nullable = false)
    public MoovebetaUser accountOwner;

    public MoovebetaSaving(String bankName,
                           String accountName,
                           Long accountNumber,
                           double amountSaved,
                           MoovebetaUser accountOwner) {
        this.bankName = bankName;
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.amountSaved = amountSaved;
        this.accountOwner = accountOwner;
    }

    public MoovebetaSaving() {

    }
}
