import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.munit.common.mocking.Attribute;
import org.mule.munit.runner.functional.FunctionalMunitSuite;

public class MyTestSuite extends FunctionalMunitSuite {

	@Test
	public void myTest() {
		Attribute attribute = Attribute.attribute("name").
				withValue("#[matchContains('github-apiSub_Flow')]"); 

	    MuleMessage messageToBeReturned =
	    		muleMessageWithPayload("#[getResource('github-test-output-example.json').asString()]"); 

	    whenMessageProcessor("sub-flow") 
	    .ofNamespace("mule")                
	    .withAttributes(attribute)          
	    .thenReturn(messageToBeReturned);
	    
//	    runFlow("", event)
	}
	
}
