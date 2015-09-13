package com.coltsoftware.liquidsledgehammer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.coltsoftware.liquidsledgehammer.collections.AliasPathResolver;
import com.coltsoftware.liquidsledgehammer.collections.FinancialTreeNode;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.Money;
import com.coltsoftware.liquidsledgehammer.model.SubTransaction;

public class CSVOutput {

	private final FinancialTreeNode root;
	private final ArrayList<String> data = new ArrayList<String>();
	private final AliasPathResolver aliasPathResolver;

	public CSVOutput(FinancialTreeNode root,
			AliasPathResolver aliasPathResolver, String path, String fileName) {
		this.root = root;
		this.aliasPathResolver = aliasPathResolver;
		File file = new File(new File(path), fileName);
		writeHeader();
		writeNode(root);
		try {
			save(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void writeHeader() {
		StringBuilder sb = new StringBuilder();
		sb.append("Date");
		sb.append(",");
		sb.append("Account");
		sb.append(",");
		sb.append("Description");
		sb.append(",");
		sb.append("Value");
		sb.append(",");
		sb.append("Adjusted Value");
		sb.append(",");
		sb.append("Balance");
		sb.append(",");
		sb.append("Group");
		sb.append(",");
		data.add(sb.toString());
	}

	private void save(File file) throws FileNotFoundException, IOException {
		FileOutputStream fos = new FileOutputStream(file);
		PrintStream printStream = new PrintStream(fos, true, "UTF-8");
		try {
			for (String s : data) {
				printStream.append(s);
				printStream.append(System.getProperty("line.separator"));
			}
		} finally {
			printStream.close();
			fos.close();
		}
	}

	private void writeNode(FinancialTreeNode parent) {
		ArrayList<SubTransaction> allTransations = new ArrayList<SubTransaction>();
		addSubTransactions(allTransations, root);
		Collections.sort(allTransations, new Comparator<SubTransaction>() {

			@Override
			public int compare(SubTransaction o1, SubTransaction o2) {
				return o1.getTransaction().getDate()
						.compareTo(o2.getTransaction().getDate());
			}

		});
		Money balance = Money.Zero;
		for (SubTransaction subTransaction : allTransations) {
			balance = balance.add(subTransaction.getValue());
			writeNode(subTransaction, balance);
		}
	}

	private void addSubTransactions(ArrayList<SubTransaction> allTransations,
			FinancialTreeNode parent) {
		for (FinancialTreeNode child : parent)
			addSubTransactions(allTransations, child);
		for (SubTransaction subTransaction : parent.getSubTransactions())
			allTransations.add(subTransaction);
	}

	private void writeNode(SubTransaction subTransaction, Money balance) {
		FinancialTransaction transaction = subTransaction.getTransaction();
		StringBuilder sb = new StringBuilder();
		sb.append(transaction.getDate());
		sb.append(",");
		sb.append(csvEscape(transaction.getSource().getName()));
		sb.append(",");
		sb.append(csvEscape(transaction.getDescription()));
		sb.append(",");
		sb.append(csvEncode(transaction.getValue()));
		sb.append(",");
		sb.append(csvEncode(subTransaction.getValue()));
		sb.append(",");
		sb.append(csvEncode(balance));
		sb.append(",");
		sb.append(csvEscape(aliasPathResolver.resolve(subTransaction.getGroup())));
		sb.append(",");
		data.add(sb.toString());
	}

	private static String csvEncode(Money value) {
		return value.toStringNoSymbol();
	}

	private static String csvEscape(String csvData) {
		return "\"" + csvData.replace("\"", "\"\"") + "\"";
	}

}
