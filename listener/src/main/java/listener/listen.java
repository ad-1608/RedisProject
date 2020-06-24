package listener;
import java.util.*; 
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.JedisPubSub;
public class listen {
	
	public static ArrayList<String> Key = new ArrayList<String>();
	public static ArrayList<String> Val = new ArrayList<String>();
	public static ArrayList<String> Reason = new ArrayList<String>();
	public static void extractKeyVal(String str,String reason)
	{
		int i,n=str.length(),j;
		
		for(i=0;i<n;i++)if(str.charAt(i)==' ')break;
		String key="",val="";
		for(i=i+1;i<n;i++)
		{
			if(str.charAt(i)==' ')break;
			key+=str.charAt(i);
		}
		for(i=i+1;i<n-1;i++)
		{
			if(str.charAt(i)==' ')break;
		}
		for(j=i+1;j<n-1;j++)
		{
			val+=str.charAt(j);
		}
		Key.add(key);
		Val.add(val);
		Reason.add(reason);
		System.out.println("Key    : "+key);
		System.out.println("Val    : "+val);
		System.out.println("Reason : "+reason);
		System.out.println();
	}
	public static void main(String[] args) {
		Jedis jedis = null;
		 
	    try {
	             
	    jedis = new Jedis();
	         
	    JedisPubSub jedisPubSub = new JedisPubSub() {
	                       
	        @Override
	        public void onPSubscribe(String channel, int subscribedChannels) {
	            System.out.println("Client is Subscribed to channel : "+ channel);
	            System.out.println("Client is Subscribed to "+ subscribedChannels + " no. of channels");
	        }
	             
	        @Override
	        public void onUnsubscribe(String channel, int subscribedChannels) {
	            System.out.println("Client is Unsubscribed from channel : "+ channel);
	            System.out.println("Client is Subscribed to "+ subscribedChannels + " no. of channels");
	        }
	        @Override
            public void onPMessage(String pattern, String channel, String message) {
                extractKeyVal(channel,message);
            }
	             
	    };
	    
	             
	    jedis.psubscribe(jedisPubSub, "__key*__:*");
	             
	    } catch(Exception ex) {         
	             
	        System.out.println("Exception : " + ex.getMessage());   
	             
	    } finally {
	             
	        if(jedis != null) {
	            jedis.close();
	        }
	    }

	}

}
