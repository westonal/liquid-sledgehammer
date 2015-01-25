package com.coltsoftware.liquidsledgehammer.json.streamsource;

import java.io.IOException;
import java.io.InputStream;

import org.junit.After;
import org.junit.Before;

import com.coltsoftware.liquidsledgehammer.MoneyTestBase;

public abstract class StreamSourceTestBase {

	public static InputStream loadAsset(String assetName) {
		InputStream stream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(assetName);
		return stream;
	}

	protected InputStream stream;

	@Before
	public void setup() {
		stream = loadAsset(getAssetName());
	}

	@After
	public void teardown() throws IOException {
		stream.close();
	}

	protected abstract String getAssetName();

}
