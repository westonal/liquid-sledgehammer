package com.coltsoftware.liquidsledgehammer.json.streamsource;

import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.json.JsonException;
import com.coltsoftware.liquidsledgehammer.json.JsonStreamTransactionSource;

public final class RequiresDateTests extends
		StreamSourceTestBase {

	@Override
	protected String getAssetName() {
		return "datelessStatement.json";
	}

	@Test(expected = JsonException.class)
	public void must_have_dates() {
		JsonStreamTransactionSource.fromStream(stream);
	}
}
