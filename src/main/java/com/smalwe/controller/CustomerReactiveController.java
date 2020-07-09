package com.smalwe.controller;

import com.smalwe.model.Customer;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Controller("/customer/reactive")
public class CustomerReactiveController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerReactiveController.class);


    List<Customer> customers = new ArrayList<>();

    @Post
    public Single<Customer> add(@Body Customer customer) {
        /**
         * Single is an Observable which emits only one item and then completes
         * This ensures that one item will be sent => non empty outputs
         */
        customer.setId(customers.size() + 1);
        customers.add(customer);
        return Single.just(customer);
    }

    @Get("/{id}")
    public Maybe<Customer> findById(@PathVariable Integer id) {
        /**
         * Maybe is similar to Single but can complete without emitting
         * Useful for optional emissions
         */
        return Maybe.just(customers.stream().filter(c-> c.getId().equals(id)).findAny().get());
    }

    @Get(value="/stream", produces = MediaType.APPLICATION_JSON_STREAM)
    public Flowable<Customer> findAllStream() {
        /**
         * Flowable emits a stream of elements and supports backpressure mechanism
         */
        return Flowable.fromIterable(customers).doOnNext(c -> LOGGER.info("Server: {}", c));
    }
}
