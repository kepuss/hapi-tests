import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.context.FhirVersionEnum;
import ca.uhn.fhir.parser.IParserErrorHandler;
import ca.uhn.fhir.parser.JsonParser;
import com.google.common.collect.Sets;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.apache.commons.lang3.SerializationUtils;
import org.hl7.fhir.dstu3.model.Bundle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@RunWith(MockitoJUnitRunner.class)

public class BundleExtensionTest {


    @Mock
    private IParserErrorHandler iParserErrorHandler;

    @Test
    public void test () throws Base64DecodingException, IOException {
        String base64 = new String(Files.readAllBytes(Paths.get("src/test/resources/bundle_example")), StandardCharsets.UTF_8);
        Bundle bundle =  SerializationUtils.deserialize(Base64.decode(base64));

        FhirContext fhirContext = new FhirContext(FhirVersionEnum.DSTU3);
        JsonParser jsonParser = new JsonParser(fhirContext,iParserErrorHandler);
        Writer writer = new StringWriter();
        jsonParser.setEncodeElements(Sets.newHashSet("*.date","*.identifier","*.status","*.extension","*.meta"));
        //This parser setting triggers error
        jsonParser.setEncodeElementsAppliesToChildResourcesOnly(true);
        jsonParser.setEncodeElementsAppliesToResourceTypes(Sets.newHashSet("List"));
        jsonParser.encodeResourceToWriter(bundle, writer);

        System.out.println(writer.toString());
    }


}
