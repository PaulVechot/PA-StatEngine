package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import models.AnalysisResult;
import models.ConfigurationSet;
import models.DataSourceInfo;

public class Client {
    private String webServiceBaseURL;
    private ConfigurationSet[] configurationSets;
    private DataSourceInfo[] dataSourceInfos;
    private AnalysisResult[] analysisResults;

    public Client(String webServiceBaseURL) {
        this.webServiceBaseURL = webServiceBaseURL;
    }

    /**
     * Calls the HTTP API using passed arguments, throwing if status is not 200
     * @param URL The relative web-service API URL to query
     * @param method The HTTP method to use for the query
     * @param data The data to use as request body (POST data)
     * @throws Exception if something goes wrong or HTTP status is not 200
     */
    private void makeAPICall(String URL, String method, String data)
            throws Exception {
        try {
            URL url = new URL(this.webServiceBaseURL + URL);
            HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
            urlc.setRequestProperty("Content-Type", "application/json");
            urlc.setRequestMethod(method);

            if (data != null) {
                // Use post mode
                urlc.setDoOutput(true);
                urlc.setAllowUserInteraction(false);

                PrintStream ps = new PrintStream(urlc.getOutputStream());
                ps.print(data);
                ps.close();
            }

            int statusCode = urlc.getResponseCode();
            if (statusCode != 200 && statusCode  != 201) {
                throw new Exception("Calling API " + URL + " resulted with "
                                    + statusCode);
            }
        } catch (Exception e) {
            System.err.println(e.toString());
            throw e;
        }
    }

    /**
     * Fetches a REST resource as a JsonElement object
     * @param URL The relative web-service API URL to query
     * @return The JsonElement representing the data returned by the web-service
     */
    private JsonElement fetchResourceAsJsonElement(String URL) {
        URL url;
        try {
            url = new URL(this.webServiceBaseURL + URL);
            InputStream is = url.openStream();

            Gson gson = new Gson();
            JsonReader jr = gson.newJsonReader(new InputStreamReader(is));
            JsonParser jp = new JsonParser();

            JsonElement element = jp.parse(jr);

            return element;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Tries finding a DataSourceInfo by its label
     * @param label The label to search for in the list of DataSourceInfo items
     * @return The found DataSourceInfo object of null if none were found
     */
    private DataSourceInfo findDataSourceInfoByLabel(String label) {
        for (int i = 0; i < dataSourceInfos.length; i++) {
            if (dataSourceInfos[i].getLabel().equals(label)) {
                return dataSourceInfos[i];
            }
        }

        return null;
    }

    /**
     * Tries finding all DataSourceInfo items using an array of labels
     * @param labels The labels to search for in the DataSourceInfo items
     * @return A newly allocated array with the found DataSourceInfo items
     */
    private DataSourceInfo[] findDataSourceInfos(JsonArray labels) {
        DataSourceInfo[] dataSources = new DataSourceInfo[labels.size()];

        for (int i = 0; i < labels.size(); i++) {
            String label = labels.get(i).getAsString();
            dataSources[i] = findDataSourceInfoByLabel(label);
        }

        return dataSources;
    }

    /**
     * Fetches configuration sets from the web-service and stores them
     * @return The fetched configuration sets
     */
    public ConfigurationSet[] fetchConfigurationSets() {
        if (this.dataSourceInfos == null
                && this.fetchDataSourceInfos() == null) {
            return null;
        }

        JsonElement element = fetchResourceAsJsonElement("/configuration_set");
        JsonArray configSets = element.getAsJsonArray();

        this.configurationSets = new ConfigurationSet[configSets.size()];
        for (int i = 0; i < configSets.size(); i++) {
            JsonObject configSet = configSets.get(i).getAsJsonObject();
            JsonArray labels = configSet.getAsJsonArray("datasources");
            DataSourceInfo[] dataSources = findDataSourceInfos(labels);

            this.configurationSets[i] =
                    new ConfigurationSet(configSet, dataSources);
        }

        return this.configurationSets;
    }
    
    /**
     * Fetches analysis results from the web-service and stores them
     * @return The fetched analysis results
     */
    public AnalysisResult[] fetchAnalysisResults() {

        JsonElement element = fetchResourceAsJsonElement("/analysis_result");
        JsonArray analysisResult = element.getAsJsonArray();

        this.analysisResults = new AnalysisResult[analysisResult.size()];
        for (int i = 0; i < analysisResult.size(); i++) {
            JsonObject configSet = analysisResult.get(i).getAsJsonObject();
            
            this.analysisResults[i] =
                    new AnalysisResult(null);
        }

        return this.analysisResults;
    }

    /**
     * Fetches data source informations from the web-service and stores them
     * @return The fetched data source informations
     */
    public DataSourceInfo[] fetchDataSourceInfos() {
        JsonElement element = fetchResourceAsJsonElement("/data_source");
        if (element == null) {
            return null;
        }
        JsonArray dataSources = element.getAsJsonArray();

        this.dataSourceInfos = new DataSourceInfo[dataSources.size()];
        for (int i = 0; i < dataSources.size(); i++) {
            this.dataSourceInfos[i] =
                    new DataSourceInfo(dataSources.get(i).getAsJsonObject());
        }

        return this.dataSourceInfos;
    }

    /**
     * Inserts a data source into the database, via the web-service
     * @param dataSource The new data source to be inserted into the database
     * @throws Exception if the web-service API call goes wrong
     */
    public void insertNewDataSource(DataSourceInfo dataSource)
            throws Exception {
        Gson gson = new Gson();
        String data = gson.toJson(dataSource);

        makeAPICall("/data_source", "POST", data);
    }

    /**
     * Inserts a configuration set into the database, via the web-service
     * @param configSet The new configuration set to be inserted in the database
     * @throws Exception if the web-service API call goes wrong
     */
    public void insertNewConfigSet(ConfigurationSet configSet)
            throws Exception {
        Gson gson = new Gson();
        String data = gson.toJson(configSet);

        makeAPICall("/configuration_set", "POST", data);
    }

    /**
     * Deletes a data source from the database, via the web-service
     * @param dataSource The data source to be deleted from the database
     * @throws Exception if the web-service API call goes wrong
     */
    public void deleteDataSource(DataSourceInfo dataSource) throws Exception {
        makeAPICall("/data_source" + dataSource.getLabel(), "DELETE", null);
    }

    /**
     * Deletes a configuration set from the database, via the web-service
     * @param configSet The configuration set to be deleted from the database
     * @throws Exception if the web-service API call goes wrong
     */
    public void deleteConfigSet(ConfigurationSet configSet) throws Exception {
        makeAPICall("/configuration_set/" + configSet.getLabel(), "DELETE", null);
    }
}
