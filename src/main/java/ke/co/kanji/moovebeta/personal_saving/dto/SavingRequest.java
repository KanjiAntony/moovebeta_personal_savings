package ke.co.kanji.moovebeta.personal_saving.dto;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Data
@Getter
public class SavingRequest implements Serializable {
    public String bankName;
    public String accountName;
    public Long accountNumber;
    public double amountSaved;

    public SavingRequest(String bankName, String accountName, Long accountNumber, double amountSaved) {
        this.bankName = bankName;
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.amountSaved = amountSaved;
    }

    public SavingRequest() {
    }

    @Override
    public String toString() {
        return "SavingRequest{" +
                "bankName='" + bankName + '\'' +
                ", accountName='" + accountName + '\'' +
                ", accountNumber=" + accountNumber +
                ", amountSaved=" + amountSaved +
                '}';
    }
}
