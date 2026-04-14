using System;

static class SavingsAccount
{
    /*
            3.213% for a negative balance (balance gets more negative).
            0.5% for a positive balance less than 1000 dollars.
            1.621% for a positive balance greater than or equal to 1000 dollars and less than 5000 dollars.
            2.475% for a positive balance greater than or equal to 5000 dollars.
     */
    public static float InterestRate(decimal balance)
    {
        if (balance < 0)
        {
            return 3.213f;
        }
        else if (balance < 1000)
        {
            return 0.5f;
        }
        else if (balance < 5000)
        {
            return 1.621f;
        }
        else
        {
            return 2.475f;
        }
    }

    public static decimal Interest(decimal balance)
    {
        return balance * (decimal) InterestRate(balance) / 100;
    }

    public static decimal AnnualBalanceUpdate(decimal balance)
    {
        return balance + Interest(balance);
    }

    public static int YearsBeforeDesiredBalance(decimal balance, decimal targetBalance)
    {
        int count = 0;
        while (balance < targetBalance)
        {
            balance = AnnualBalanceUpdate(balance);
            count ++;
        }
        return count;
    }
}
