package baseadd.gr.com;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import cross.gr.com.CORSFilter;




@ApplicationPath("/api")
public class BaseApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<>();
		classes.add(CORSFilter.class);
		
    	//classes.add(HelloWorld.class);
    	return classes;
	}
	
    /*@Override
    public Set<Object> getSingletons() {
    	Set<Object> classes = new HashSet<>();
    	classes.add(HelloWorld.class);
    	return classes;
    	
    }*/
    
}