package com.rollingstone.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.HeaderIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.HeaderElementIterator;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.HeaderElement;

import java.util.concurrent.TimeUnit;


@Configuration
public class ApacheHttpClientConfiguration {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // Connection pool
    int MAX_ROUTE_CONNECTIONS     = 15; // route would be like /product
    int MAX_TOTAL_CONNECTIONS     = 50; // All routes together should not exceed 50

    // Timeouts
    int CONNECTION_TIMEOUT = 10 * 1000; // We will wait for 10 seconds until a connection is established
    int REQUEST_TIMEOUT    = 20 * 1000; // We will wait 20 seconds for getting a connection from the connection pool
    int SOCKET_TIMEOUT     = 10 * 1000; // We will wait 10 seconds, to receive the data from the external call

    // Keep alive
    int DEFAULT_KEEP_ALIVE_TIME = 10 * 1000; // One connection would be kept alive for 10 seconds

    // Idle connection monitor
    int IDLE_CONNECTION_WAIT_TIME = 30 * 1000; // If a physical connection is idling for 30 seconds or more, it will be terminated

    @Bean
    public PoolingHttpClientConnectionManager poolingConnectionManager() {
        PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager();

        // set total amount of connections across all HTTP routes
        poolingConnectionManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);

        // set maximum amount of connections for each http route in pool
        poolingConnectionManager.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);

       return poolingConnectionManager;
    }

    @Bean
    public ConnectionKeepAliveStrategy connectionKeepAliveStrategy() {
        return (httpResponse, httpContext) -> {
            HeaderIterator headerIterator = httpResponse.headerIterator(HTTP.CONN_KEEP_ALIVE);
            HeaderElementIterator elementIterator = new BasicHeaderElementIterator(headerIterator);

            while (elementIterator.hasNext()) {
                HeaderElement element = elementIterator.nextElement();
                String param = element.getName();
                String value = element.getValue();
                if (value != null && param.equalsIgnoreCase("timeout")) {
                    return Long.parseLong(value) * 1000; // convert to ms
                }
            }

            return DEFAULT_KEEP_ALIVE_TIME;
        };
    }

    @Bean
    public Runnable idleConnectionMonitor(PoolingHttpClientConnectionManager pool) {
        return new Runnable() {
            @Override
            public void run() {
                // only if connection pool is initialised
                if (pool != null) {
                    pool.closeExpiredConnections();
                    pool.closeIdleConnections(IDLE_CONNECTION_WAIT_TIME, TimeUnit.MILLISECONDS);

                    logger.info("Idle connection monitor: Closing expired and idle connections");
                }
            }
        };
    }

    @Bean
    public CloseableHttpClient httpClient() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(CONNECTION_TIMEOUT)
                .setConnectionRequestTimeout(REQUEST_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT)
                .build();

        return HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(poolingConnectionManager())
                .setKeepAliveStrategy(connectionKeepAliveStrategy())
                .build();
    }

}
