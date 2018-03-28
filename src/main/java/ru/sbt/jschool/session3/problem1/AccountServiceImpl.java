package ru.sbt.jschool.session3.problem1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 */
public class AccountServiceImpl implements AccountService {
    protected FraudMonitoring fraudMonitoring;
    protected HashMap<Long, ArrayList<Account>> accountListMap = new HashMap<>();
    protected HashMap<Long, Account> accountMap = new HashMap<>();
    protected HashMap<Long, Payment> paymentHashMap = new HashMap<>();



    public AccountServiceImpl(FraudMonitoring fraudMonitoring) {
        this.fraudMonitoring = fraudMonitoring;
    }

    @Override public Result create(long clientID, long accountID, float initialBalance, Currency currency) {
        ArrayList<Account> arrayList;
        Account account;

        if (fraudMonitoring.check(clientID))
            return Result.FRAUD;

        if (accountListMap.get(clientID)!=null)
        {
            arrayList = accountListMap.get(clientID);

                if (accountMap.get(accountID)!=null)
                    return Result.ALREADY_EXISTS;
                account = new Account(clientID, accountID, currency, initialBalance);
                arrayList.add(account);
                accountMap.put(accountID, account);
                return Result.OK;

        }
        account = new Account(clientID, accountID, currency, initialBalance);
        arrayList = new ArrayList<>();
        arrayList.add(account);
        accountListMap.put(clientID, arrayList);
        accountMap.put(accountID, account);
        return Result.OK;
    }

    @Override public List<Account> findForClient(long clientID) {
        return accountListMap.get(clientID);
    }

    @Override public Account find(long accountID) {
        return accountMap.get(accountID);
    }

    @Override public Result doPayment(Payment payment) {

        if (paymentHashMap.get(payment.getOperationID())!=null)
            return Result.ALREADY_EXISTS;

        if (accountListMap.get(payment.getPayerID())==null)
            return Result.PAYER_NOT_FOUND;
        else
        {
            if (accountMap.get(payment.getPayerAccountID())==null)
                return Result.PAYER_NOT_FOUND;
        }
        if (accountListMap.get(payment.getRecipientID())==null)
            return Result.RECIPIENT_NOT_FOUND;
        else
        {
            if (accountMap.get(payment.getRecipientAccountID())==null)
                return Result.RECIPIENT_NOT_FOUND;
        }

        Account accPayer = accountMap.get(payment.getPayerAccountID());
        Account accRecip = accountMap.get(payment.getRecipientAccountID());

        float amount = payment.getAmount();
        float newAmount = amount;

        if (accPayer.getCurrency()!=accRecip.getCurrency())
            newAmount = accPayer.getCurrency().to(amount, accRecip.getCurrency());

        accPayer.setBalance(accPayer.getBalance() - amount);
        accRecip.setBalance(accRecip.getBalance() + newAmount);
        paymentHashMap.put(payment.getOperationID(),payment);
        return Result.OK;
    }
}
