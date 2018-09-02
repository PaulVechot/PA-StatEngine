package business;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;

import models.Condition;
import models.DataSourceEntry;
import models.DataSourceInfo;

/**
 * Allows interfacing with specific types of data sources
 * @author Florian CHAMPAUD
 *
 */
public class DataSourceInterface {
    private static Hashtable<String, DataSourceInterface> interfacesByType =
            new Hashtable<>();

    private Object interfaceObject;
    private Method fetchEntriesMethod;

    /**
     * Creates a DataSourceInterface object for exploiting an interface object
     * @param interfaceObject The data source interface implementation object
     */
    private DataSourceInterface(Object interfaceObject) {
        Class<?> interfaceClass = interfaceObject.getClass();
        this.interfaceObject = interfaceObject;
        this.fetchEntriesMethod =
                getAnnotatedMethod(interfaceClass, FetchEntriesMethod.class);
    }

	/**
	 * Tries getting a method with passed annotation from the passed class
	 * @param entryPointClass The class from which to get the methods
	 * @param annotationClass The annotation to search for
	 * @return The method if it was found or false otherwise
	 */
	private Method getAnnotatedMethod(Class<?> entryPointClass,
	                                       Class<? extends Annotation>
	                                                    annotationClass) {
	    Method[] methods = entryPointClass.getDeclaredMethods();

	    for (int i = 0; i < methods.length; i++) {
	        if (methods[i].isAnnotationPresent(annotationClass)) {
	            return methods[i];
	        }
	    }

	    return null;
	}

	/**
	 * Allows registering an interface object for specified data source type
	 * @param interfaceObject The interface object to register
	 * @param type The type for which to register it
	 * @return true if the interface instance was registered, false otherwise
	 */
    public static boolean registerForType(Object interfaceObject, String type) {
        if (interfacesByType.contains(type)) {
            return false;
        }

        try {
            System.out.println("Registering for type " + type + ".");
            DataSourceInterface dataInterface =
                    new DataSourceInterface(interfaceObject);

            interfacesByType.put(type, dataInterface);
            return true;
        } catch (Exception e) {
            System.err.println("[registerForType] Could not register: " + e);
            return false;
        }
    }

    /**
     * Returns the DataSourceInterface object for passed data source type
     * @param type The data source type for which to get the interface
     * @return The interface object for the specified data source type
     */
    public static DataSourceInterface getForDataSourceType(String type) {
        return interfacesByType.get(type);
    }

    /**
     * Fetches and returns entries from this data sources
     * @param conditions The conditions to apply for selecting data entries
     * @return An array with the data source entries
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public DataSourceEntry[] fetchEntries(DataSourceInfo dataSource)
            throws IllegalAccessException, IllegalArgumentException,
                   InvocationTargetException {
        return (DataSourceEntry[]) fetchEntriesMethod
                .invoke(interfaceObject, (Object) dataSource);
    }
}
