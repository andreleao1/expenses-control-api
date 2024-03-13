package br.com.agls.expensescontrolapi.api.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MutableHttpServletRequest extends HttpServletRequestWrapper {

    private final Map<String, String> customHeaders;

    public MutableHttpServletRequest(HttpServletRequest request) {
        super(request);
        this.customHeaders = new HashMap<String, String>();
    }

    public void putHeader(String name, String value) {
        this.customHeaders.put(name, value);
    }
}
