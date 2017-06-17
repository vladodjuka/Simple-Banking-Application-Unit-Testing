package testing;

import static org.junit.Assert.*;
import Bank.Account;

import org.junit.Test;

public class AccountTest {

	@Test
	public void testWithdraw() {//
		Account a = new Account("EUR", 200);
		/*
		 * We are checking if withdraw method works what it is supposed to and
		 * that is "Removing specified value from balance". We are testing with
		 * positive smallest number(1) as bugs will rather occur on boundary
		 * values. As result we expect 199.
		 */
		a.withdraw(1);
		assertEquals("Withdraw 20 euros from balance - error", 199, a.getBalance(), 0.1);
		/*
		 * We are testing if we can withdraw more money than we have on our
		 * debit account. We expect balance to remain unchanged (or >= 0) as it
		 * is not allowed to withdraw more money than we have on our debit
		 * account.
		 */
		a.withdraw(200);
		assertTrue("Withdraw 200 euros from balance (more than we have on our account)-  error", a.getBalance() >= 0);

		/*
		 * We are testing if we can withdraw negative amount of money from
		 * account balance as this scenario shouldn't be possible and should
		 * result in error. We expect balance to remain unchanged as it
		 * shouldn't be possible to withdraw negative amount of money.
		 */
		double oldValue = a.getBalance();
		a.withdraw(-20);
		assertEquals("Withdraw -20 euros from balance (negative value) - error ", oldValue, a.getBalance(), 0.1);

	}

	@Test
	public void testDeposit() {//
		Account a = new Account("EUR", 100);

		/*
		 * We are testing if we can deposit negative amount of money to account
		 * balance as this scenario shouldn't be possible and should result in
		 * error. We expect balance to remain unchanged.
		 */
		double oldBalanceState = a.getBalance();
		a.deposit(-20);
		assertFalse("Deposit -20 euros to balance - error", a.getBalance() != oldBalanceState);

		/*
		 * We are testing if we can deposit more than maximum double value to
		 * account balance as this scenario shouldn't be possible and should
		 * result in error. We expect balance to remain unchanged.
		 */
		double oldBalanceState2 = a.getBalance();
		a.deposit(Double.MAX_VALUE + 100);

		assertTrue("Deposit max double value incremented by 100 to balance - error", a.getBalance() == oldBalanceState2);

		/*
		 * We are testing if we can deposit positive infinity to account balance
		 * as this scenario shouldn't be possible and should result in error. We
		 * expect balance to remain unchanged.
		 */
		a.setBalance(100);
		double oldBalanceState3 = a.getBalance();
		a.deposit(Double.POSITIVE_INFINITY);

		assertEquals("Deposit positive infinity to balance - error", oldBalanceState3, a.getBalance(), 0.1);

	}

	@Test
	public void testConvertToCurrency() {//
		Account a1 = new Account("EUR", 200);

		/*
		 * We are testing if we can set transfer rate to be negative, as this
		 * scenario shouldn't be possible and should result in error. We expect
		 * balance and currency to remain unchanged.
		 */
		double oldValue = a1.getBalance();
		String oldCurrency = a1.getCurrency();
		a1.convertToCurrency("SEK", -9.70);
		assertTrue("Negative transfer rate - error", a1.getBalance() == oldValue && a1.getCurrency() == oldCurrency);

		a1.setBalance(200);
		a1.setCurrency("EUR");

		/*
		 * We are testing if we can set same exchange currency, as this scenario
		 * shouldn't be possible and should result in error. We expect balance
		 * and currency to remain unchanged.
		 */
		String oldCurrency2 = a1.getCurrency();
		a1.convertToCurrency("EUR", 9.70);
		assertFalse("Transfer to same currency - error", oldCurrency2 == a1.getCurrency());

		a1.setBalance(200);
		a1.setCurrency("EUR");
		/*
		 * We are testing if we can set rate to be 0, as this scenario shouldn't
		 * be possible and should result in error. We expect balance and
		 * currency to remain unchanged.
		 */
		String oldCurrency3 = a1.getCurrency();
		double oldValue2 = a1.getBalance();

		a1.convertToCurrency("SEK", 0);
		assertTrue("Transfer with 0 rate - error", a1.getBalance() == oldValue2 && a1.getCurrency() == oldCurrency3);

	}

	@Test
	public void testTransferToAccount() {//
		Account a1 = new Account("EUR", 100);
		Account a2 = new Account("EUR", 150);

		a1.TransferToAccount(a2);

		/*
		 * We are testing if transferToAccount method works the way it is
		 * supposed to and that is transfer whole amount of money from one
		 * account to another. We expect balance of second account to be 250 or
		 * increased by 100 (the amount of money on first account before
		 * transfer).
		 */

		assertEquals("Transfer error", 250, a2.getBalance(), 0.1);

		/*
		 * We are testing if the object of account from which we transfer money
		 * is null or if currency is null. If it is null it should not be
		 * possible to perform transfer. We expect error in case that either of
		 * the aforementioned is null.
		 */

		assertNotNull("Object exist - error", a1);

		/*
		 * We are testing if currencies on both accounts are same. If the
		 * accounts have different currencies or if currencies are not defined
		 * transfer should not execute and money should not be transfered. We
		 * expect balance to remain unchanged on both accounts in case if
		 * accounts have different currencies or if currencies are not defined
		 * on any of the accounts.
		 */
		assertTrue("Different currencies or not defined currencies on accounts - error", checkCurrency(a1, a2));

	}

	boolean checkCurrency(Account a, Account a2) {
		if (a == null || a2 == null) {
			return false;
		} else {
			if (a.getCurrency() == null || a2.getCurrency() == null) {
				return false;
			} else {
				if (a.getCurrency().equalsIgnoreCase(a2.getCurrency())) {
					return true;
				}
			}
		}
		return false;

	}

}
