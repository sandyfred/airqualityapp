package com.team01.subscriptionservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.team01.subscriptionservice.service.SubService;
import com.team01.subscriptionservice.exception.SubscriptionsNotFoundException;
import com.team01.subscriptionservice.model.Subscriber;

@RestController
@RequestMapping("/api/v1/subscriber")
@CrossOrigin(origins = "http://localhost:4200")
public class SubController {

    @Autowired
    private SubService subService;

    //Create new Subscriber
    @PostMapping("/")
    public ResponseEntity<?> createSubscriber(@RequestBody Subscriber subscriber) {

        boolean status = subService.createSubcriber(subscriber);
        if (status) {
            return new ResponseEntity<Subscriber>(subscriber, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<String>("Subscription not created", HttpStatus.CONFLICT);
        }

    }

    //Get All SubscriberData
    @GetMapping("/")
    public ResponseEntity<?> getAllSubscriber() {
        List<Subscriber> subscriptionList = subService.getAllSubscriber();
        return new ResponseEntity<List<Subscriber>>(subscriptionList, HttpStatus.OK);
    }

    //GetSubcriberData By Id
    @GetMapping("/{Id}")
    public ResponseEntity<?> getSubscriptionByUserId(@PathVariable("Id") String Id) {

        try {
            Subscriber subscriber = subService.getSubscriberById(Id);
            return new ResponseEntity<Subscriber>(subscriber, HttpStatus.OK);
        } catch (SubscriptionsNotFoundException e) {
            return new ResponseEntity<String>("User not subscribed", HttpStatus.NOT_FOUND);
        }
    }

    //Update Subscriber
    @PutMapping("/")
    public ResponseEntity<?> updateSubscriber(@RequestBody Subscriber subscriber) {
        try {
            Subscriber updatedSubscriber = subService.updateSubscriber(subscriber);
            return new ResponseEntity<Subscriber>(updatedSubscriber, HttpStatus.OK);
        } catch (SubscriptionsNotFoundException e) {
            return new ResponseEntity<String>("Subscription not found", HttpStatus.NOT_FOUND);
        }
    }

    //Delete Subscriber
    @DeleteMapping("/{Id}")
    public ResponseEntity<?> removeSubscription(@PathVariable("Id") String Id) {
        try {
            if (subService.removeSubscriber(Id)) {
                return new ResponseEntity<String>("Subscription deleted", HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<String>("Subscription not deleted", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>("Subscription not deleted", HttpStatus.NOT_FOUND);
    }
}
