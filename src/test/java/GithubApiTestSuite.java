import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.transport.PropertyScope;
import org.mule.munit.common.mocking.Attribute;
import org.mule.munit.runner.functional.FunctionalMunitSuite;

import org.junit.Assert;

public class GithubApiTestSuite extends FunctionalMunitSuite {

	@Override
	protected String getConfigResources() {
		return "github-api.xml";
	}
	
	@Test
	public void githubApiTest() throws Exception {
		mockHttpResponse();
		
		MuleEvent inputEvent = generateEvent();
		
		MuleEvent outputEvent = runFlow("github-apiFlow", inputEvent);
		
		GithubResponse githubResponse = GithubResponseTransformer.toGithubResponse(outputEvent.getMessageAsString());

		Assert.assertEquals(githubResponse.getName(), "Castle");
		Assert.assertEquals(githubResponse.getSite(), "http://castle.co/");
		Assert.assertEquals(outputEvent.getFlowVariable("org"), getUriParams(inputEvent).get("org"));
	}

	@SuppressWarnings("unchecked")
	private Map<String, String> getUriParams(MuleEvent inputEvent) {
		MuleMessage inputMessage = inputEvent.getMessage();
		Map<String, String> uriParams = (Map<String, String>) inputMessage.getInboundProperty("http.uri.params");
		return uriParams;
	}

	private MuleEvent generateEvent() throws Exception {
		MuleEvent event = testEvent("");
		Map<String, String> uriParams = new HashMap<>();
		uriParams.put("org", "cloudcastle");
		event.getMessage().setProperty("http.uri.params", uriParams, PropertyScope.INBOUND);
		return event;
	}
	
	private void mockHttpResponse() {
		Attribute attribute = Attribute.attribute("name").ofNamespace("doc").withValue("Call Github API");
		Object payload = getClass().getResourceAsStream("github-test-output-example.json");
		MuleMessage messageToReturn = muleMessageWithPayload(payload);
		whenMessageProcessor("HTTP").withAttributes(attribute).thenReturn(messageToReturn);;
	}
	
}
