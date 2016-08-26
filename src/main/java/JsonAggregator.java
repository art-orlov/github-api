import org.mule.DefaultMuleEvent;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.routing.AggregationContext;
import org.mule.routing.AggregationStrategy;

public class JsonAggregator implements AggregationStrategy {

	@Override
	public MuleEvent aggregate(AggregationContext context) throws MuleException {
		
        StringBuffer buffer = new StringBuffer();
        
        for (MuleEvent event : context.collectEventsWithoutExceptions()) {
        	String message = event.getMessageAsString();
        	if (buffer.length() > 0) {
        		buffer.append(",");
        	}
        	buffer.append(message);
        }
        
        MuleEvent result = DefaultMuleEvent.copy(context.getOriginalEvent());
        result.setMessage(new DefaultMuleMessage(buffer.toString(), context.getOriginalEvent().getMuleContext()));
        
        return result;
	}
	

}
