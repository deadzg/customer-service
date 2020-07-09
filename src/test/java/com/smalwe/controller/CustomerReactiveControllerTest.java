package com.smalwe.controller;

import com.smalwe.model.Customer;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.RxStreamingHttpClient;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;
import io.reactivex.Maybe;
import io.reactivex.Single;
import net.jodah.concurrentunit.Waiter;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;

// Ref: https://piotrminkowski.com/2019/11/12/micronaut-tutorial-reactive/

@MicronautTest
public class CustomerReactiveControllerTest {

    @Inject
    EmbeddedServer server;

    @Test
    public void testAdd() throws MalformedURLException, TimeoutException, InterruptedException {
        final Waiter w = new Waiter();
        final Customer c1 = new Customer(null, "FN1", "FN2");
        RxHttpClient client = RxHttpClient.create(new URL("http://" + server.getHost() + ":" + server.getPort()));
        Single<Customer> s = client.retrieve(HttpRequest.POST("/customer/reactive", c1), Customer.class).firstOrError();

        s.subscribe(c -> {
            w.assertNotNull(c.getFname());
            w.assertNotNull(c.getId());
            w.resume();
        });

        w.await(3000, TimeUnit.MILLISECONDS);

    }
    @Test
    public void testFindById() throws MalformedURLException, TimeoutException, InterruptedException {
        final Waiter waiter = new Waiter();
        RxHttpClient client = RxHttpClient.create(new URL("http://" + server.getHost() + ":" + server.getPort()));
        Maybe<Customer> s = client.retrieve(HttpRequest.GET("/customer/reactive/1"), Customer.class).firstElement();
        s.subscribe(c -> {
            waiter.assertNotNull(c);
            waiter.assertEquals(1, c.getId());
            waiter.resume();
        });
        waiter.await(3000, TimeUnit.MILLISECONDS);
    }

    @Test
    public void testFindAllStream() throws MalformedURLException, TimeoutException, InterruptedException {
        final Waiter waiter = new Waiter();
        RxStreamingHttpClient client = RxStreamingHttpClient.create(new URL("http://" + server.getHost() + ":" + server.getPort()));
        client.jsonStream(HttpRequest.GET("/customer/reactive/stream"), Customer.class)
                .subscribe(s -> {
                    waiter.assertNotNull(s);
                    waiter.resume();
                });
        waiter.await(3000, TimeUnit.MILLISECONDS);
    }
}
