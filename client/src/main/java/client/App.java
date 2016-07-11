package client;

import java.io.IOException;
import java.util.Iterator;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws JsonProcessingException, IOException
    {
    	String str="{\"bean.connString\":\"192.168.1.1\",\"bean.age\":\"2\",\"bean.name\":\"bean2\"}";
    	ObjectMapper mapper = new ObjectMapper();
    	JsonNode obj = mapper.reader().readTree(str);				
		Iterator<String> node = obj.getFieldNames();
		while(node.hasNext()){
			String key = node.next();
			System.out.println("key="+key+",value="+obj.get(key));
		}
    }
}
