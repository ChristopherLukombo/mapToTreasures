package maptotreasures;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.carbon.maptotreasures.config.ReaderConfig;

class ReaderConfigTest {

	private ReaderConfig readerConfig;
	
	@BeforeEach
	void setUp() {
		readerConfig = new ReaderConfig(); 
	}
	
	@Test
	void should_getCard_successfully() {
		assertThat(readerConfig.getCard())
		.hasDimensions(4, 3)
		.hasNumberOfRows(4);
	}
 }
