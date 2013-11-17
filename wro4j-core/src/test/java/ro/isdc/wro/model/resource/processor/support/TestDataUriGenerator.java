package ro.isdc.wro.model.resource.processor.support;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import ro.isdc.wro.util.WroTestUtils;


/**
 * @author Alex Objelean
 * @author Ivar Conradi Østhus
 * @created 09 may, 2010
 */
public class TestDataUriGenerator {
  private final DataUriGenerator dataUriGenerator = new DataUriGenerator();

  @Test
  public void shouldGenerateCorrectDataUriForPng()
      throws Exception {
    final String expected = "data:image/png;base64,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";
    final String actual = dataUriGenerator.generateDataURI(getInputStream("dataUri.png"), "dataUri.png");

    assertEquals(expected, actual);
  }

  @Test
  public void shouldGenerateCorrectDataURIForCSSWithCharset()
      throws IOException {
    final String expected = "data:text/css;charset=UTF-8;base64,aW5wdXQuYnV0dG9uIHsNCgliYWNrZ3JvdW5kOiB1cmwoaHR0cDovL3dybzRqLmdvb2dsZWNvZGUuY29tL3N2bi93aWtpL2ltZy9mb2xkZXJTdHJ1Y3R1cmUucG5nKTsNCgliYWNrZ3JvdW5kLWltYWdlOiB1cmwoImh0dHA6Ly93cm80ai5nb29nbGVjb2RlLmNvbS9zdm4vd2lraS9pbWcvZm9sZGVyU3RydWN0dXJlLnBuZyIpOw0KCWZpbHRlcjogcHJvZ2lkOkRYSW1hZ2VUcmFuc2Zvcm0uTWljcm9zb2Z0LkFscGhhSW1hZ2VMb2FkZXIoc3JjPSdodHRwOi8vd3JvNGouZ29vZ2xlY29kZS5jb20vc3ZuL3dpa2kvaW1nL2ZvbGRlclN0cnVjdHVyZS5wbmcnLCBzaXppbmdNZXRob2Q9J3NjYWxlJyk7DQp9DQoubXVsdGlsaW5lQW5kU3BhY2VzIHsNCiAgYmFja2dyb3VuZDogDQogICAgIHVybCggImh0dHA6Ly93cm80ai5nb29nbGVjb2RlLmNvbS9zdm4vd2lraS9pbWcvZm9sZGVyU3RydWN0dXJlLnBuZyAiICk7DQogIGJhY2tncm91bmQtaW1hZ2UgOiANCiAgICAgdXJsKCAiaHR0cDovL3dybzRqLmdvb2dsZWNvZGUuY29tL3N2bi93aWtpL2ltZy9mb2xkZXJTdHJ1Y3R1cmUucG5nICIgKSAgICAgDQp9DQpAZm9udC1mYWNlIHsNCiAgICBzcmM6IHVybChodHRwOi8vd3JvNGouZ29vZ2xlY29kZS5jb20vc3ZuL3dpa2kvaW1nL2ZvbGRlclN0cnVjdHVyZS5wbmcpOw0KfQ==";
    final String actual = dataUriGenerator.generateDataURI(getInputStream("dataUri.css"), "dataUri.css");
    WroTestUtils.compare(expected, actual);
  }

  private InputStream getInputStream(final String filename) {
    return getClass().getResourceAsStream(filename);
  }
}
