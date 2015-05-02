#liquid-sledgehammer

Financial transaction consolidation framework. Processes all your financial transactions, grouping them together into a tree of expenses as deep as you like and gives totals, balances, combined statements.

Many institutions provide similar functionailty where they decide that a transaction is groceries or fuel, but LS goes further:

- Nested groups allow you to sub divide spending
- A single transaction can be split amoung different groups
- Movements between your accounts can be excluded
- Refunds or partial refunds can be excluded

##Design

The design of the system allows anyone to write a new source. Sources are unordered buckets of transactions. All filtering is done later so the system is easy to extend via the `FinancialTransactionSource` interface.

``
public interface FinancialTransactionSource extends
		Iterable<FinancialTransaction> {
}
``

A json source is already written, but you can source transactions from databases or anywhere.

The system is designed for personal finance, for processing 10's of thousands of transactions, not millions and so doesn't filter transactions at source, but your source can filter transactions in provides to the system if you have too many.


A `FinancialTransaction` consists of value, description, date, and a `groupPattern`. The `groupPattern` is used to override the automattic assignment of transactions to groups and can be used to divide a transaction amoung groups

###Group Patterns

These are simple lists used to assign values to groups be an alias.

Examples:

- `petrol` the whole value of this transaction belongs in the group with the alias petrol
- `food=-3.99,petrol` 3.99 belongs in food, the rest was petrol
- `food=-3.99` 3.99 belongs in food, the rest assign automatically
