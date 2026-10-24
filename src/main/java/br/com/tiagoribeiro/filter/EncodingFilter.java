package br.com.tiagoribeiro.filter;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {

    private static final String ENCODING = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // nada a inicializar
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        request.setCharacterEncoding(ENCODING);
        response.setCharacterEncoding(ENCODING);

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // nada a liberar
    }
}