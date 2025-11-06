package es.grise.upm.profundizacion.subscriptionService;

import es.grise.upm.profundizacion.exceptions.ExistingUserException;
import es.grise.upm.profundizacion.exceptions.LocalUserDoesNotHaveNullEmailException;
import es.grise.upm.profundizacion.exceptions.NullUserException;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class SubscriptionServiceTest {

    private User user = new User() {
        @Override
        public String getEmail() {
            return "hola@hola.test";
        }

        @Override
        public Delivery getDelivery() {
            return Delivery.LOCAL;
        }
    };

    private Delivery delivery;

    @Test
    public void addInvalidSubscriberTest() throws NullUserException, LocalUserDoesNotHaveNullEmailException, ExistingUserException {
        SubscriptionService subscriptionService = new SubscriptionService(delivery);
        assertThrows(NullUserException.class,() -> subscriptionService.addSubscriber(null));
    }

    @Test
    public void addExistingSubscriberTest() throws NullUserException, ExistingUserException {
        //Create hashset with mock user
        Collection<User> subscribers = new HashSet<User>();
        subscribers.add(user);
        //Create subscriptionService with user
        SubscriptionService subscriptionService = new SubscriptionService(delivery);
        subscriptionService.setSubscribers(subscribers);

        assertThrows(ExistingUserException.class,() -> subscriptionService.addSubscriber(user));
    }

    @Test
    public void localUserNeedsNullEmailTest() throws NullUserException, ExistingUserException {
        SubscriptionService subscriptionService = new SubscriptionService(delivery);
        assertThrows(LocalUserDoesNotHaveNullEmailException.class,() -> subscriptionService.addSubscriber(user));
    }

    @Test
    public void addUserTest() throws NullUserException, ExistingUserException, LocalUserDoesNotHaveNullEmailException {
        SubscriptionService subscriptionService = new SubscriptionService(delivery);
        subscriptionService.addSubscriber(user);
        assertEquals(1, subscriptionService.getSubscribers().size());
    }
}
