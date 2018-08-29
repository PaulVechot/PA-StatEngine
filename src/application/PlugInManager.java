package application;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Hashtable;

public class PlugInManager extends URLClassLoader {
    public static final PlugInManager instance = new PlugInManager(new URL[0]);

    private Hashtable<String, PlugIn> plugIns = new Hashtable<String, PlugIn>();

    public PlugInManager(URL[] urls) {
        super(urls);
    }

    public Hashtable<String, PlugIn> getPlugIns() {
        return plugIns;
    }

    /**
     * Appends an URL to the list of URLs to search for classes
     * @param path The path of the URL to append
     * @throws MalformedURLException
     */
    public void addURL(String path) throws MalformedURLException {
        this.addURL(new URL(path));
    }

    /**
     * Tries loading passed class from passed package as a plug-in
     * @param packageName The package that should contain the plug-in class
     * @param className The class to load as a plug-in
     * @throws Exception
     */
    public void load(String packageName, String className) throws Exception {
        String resourceName = packageName + "." + className;

        if (this.plugIns.containsKey(className)) {
            throw new Exception("Class " + className + " already loaded.");
        }

        Class<?> cls = loadClass(resourceName);
        PlugIn plugIn = new PlugIn(cls);
        plugIn.init(this);
        this.plugIns.put(className, plugIn);
    }
}
