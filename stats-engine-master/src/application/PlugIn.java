package application;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class PlugIn {
	private Class<?> entryPointClass;
	private Object instance;
    private String name;
    private Method initMethod;

	public PlugIn(Class<?> c) throws Exception {
		this.entryPointClass = c;
		this.instance = c.newInstance();
		this.name = c.getName();
		this.initMethod = getMethodWithAnnotation(PlugInInit.class);
	}

	/**
	 * Tries getting a method with passed annotation from the plug-in class
	 * @param annotationClass The annotation to search for
	 * @return
	 */
	private Method getMethodWithAnnotation(Class<? extends Annotation>
	                                           annotationClass) {
	    Method[] methods = entryPointClass.getDeclaredMethods();

	    for (int i = 0; i < methods.length; i++) {
	        if (methods[i].isAnnotationPresent(annotationClass)) {
	            return methods[i];
	        }
	    }

	    return null;
	}

    public String getName() {
        return name;
    }

    public Method getInitMethod() {
        return initMethod;
    }

    /**
     * Invokes the plug-in's initialization method
     * @param plugInManager The PlugInManager to pass to the init method
     * @throws Exception
     */
    public void init(PlugInManager plugInManager) throws Exception {
        this.initMethod.invoke(instance, plugInManager);
    }
}