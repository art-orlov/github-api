import java.io.IOException;
import java.io.InputStream;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.mule.api.annotations.ContainsTransformerMethods;
import org.mule.api.annotations.Transformer;

import com.mulesoft.weave.reader.ByteArraySeekableStream;

@ContainsTransformerMethods
public class GithubResponseTransformer {

	@Transformer(sourceTypes = {InputStream.class, ByteArraySeekableStream.class})
	public static GithubResponse toGithubResponse(String json) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return (GithubResponse) mapper.readValue(json, GithubResponse.class);
	}

}
