package br.com.agls.expensescontrolapi.util;
public abstract class UriExtractorUtil {

    private UriExtractorUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String execute(String request) {
        String[] requestArray = request.split(";");
        String[] uri = requestArray[0].split("=");

        return uri[uri.length - 1];
    }
}
