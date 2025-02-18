package com.dilmah.hostel.service;

import com.dilmah.hostel.model.Subscription;
import com.dilmah.hostel.model.SubscriptionFeatures;
import com.dilmah.hostel.repository.SubscriptionRepository;
import com.dilmah.hostel.repository.SubscriptionFeaturesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;
    
    @Autowired
    private SubscriptionFeaturesRepository subscriptionFeaturesRepository;

    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    public Optional<Subscription> getSubscriptionById(int id) {
        return subscriptionRepository.findById(id);
    }

    public Subscription createSubscription(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    public Subscription updateSubscription(int id, Subscription updatedSubscription) {
        return subscriptionRepository.findById(id).map(subscription -> {
            subscription.setName(updatedSubscription.getName());
            subscription.setDescription(updatedSubscription.getDescription());
            subscription.setFee(updatedSubscription.getFee());
            return subscriptionRepository.save(subscription);
        }).orElseThrow(() -> new RuntimeException("Subscription not found with id: " + id));
    }

    public void deleteSubscription(int id) {
        subscriptionRepository.deleteById(id);
    }
    
    // SubscriptionService Management
    public List<SubscriptionFeatures> getAllServices() {
        return subscriptionFeaturesRepository.findAll();
    }

    public Optional<SubscriptionFeatures> getServiceById(int id) {
        return subscriptionFeaturesRepository.findById(id);
    }

    public SubscriptionFeatures createService(SubscriptionFeatures service) {
        return subscriptionFeaturesRepository.save(service);
    }

    public SubscriptionFeatures updateService(int id, SubscriptionFeatures updatedService) {
        return subscriptionFeaturesRepository.findById(id).map(service -> {
            service.setName(updatedService.getName());
            service.setDescription(updatedService.getDescription());
            service.setSubscription(updatedService.getSubscription());
            return subscriptionFeaturesRepository.save(service);
        }).orElseThrow(() -> new RuntimeException("SubscriptionService not found with id: " + id));
    }

    public void deleteService(int id) {
        subscriptionFeaturesRepository.deleteById(id);
    }
}
