package br.com.tiagoribeiro.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        long inicio = System.currentTimeMillis();
        try {
            chain.doFilter(request, response);
        } finally {
            long duracao = System.currentTimeMillis() - inicio;
            logger.info("{} {} -> {} ({} ms)",
                    req.getMethod(), req.getRequestURI(), resp.getStatus(), duracao);
        }
    }
    @Override
    public void destroy() {
    }
}