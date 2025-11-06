package es.grise.upm.profundizacion.subscriptionService;

import es.grise.upm.profundizacion.exceptions.ExistingUserException;
import es.grise.upm.profundizacion.exceptions.LocalUserDoesNotHaveNullEmailException;
import es.grise.upm.profundizacion.exceptions.NullUserException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.mockito.Mockito;

import static org.mockito.Mockito.when;

public class SubscriptionServiceTest {
    @Test
    void workingSubscriptionServiceTest() {
        SubscriptionService subscriptionService =
                new SubscriptionService();

        assertEquals(0, subscriptionService.getSubscribers().size());

        assertDoesNotThrow(() -> subscriptionService.addSubscriber(Mockito.mock(User.class)));

        assertEquals(1, subscriptionService.getSubscribers().size());

        User user = Mockito.mock(User.class);
        when(user.getDelivery()).thenReturn(Delivery.LOCAL);

        assertDoesNotThrow(() -> subscriptionService.addSubscriber(user));

        assertEquals(2, subscriptionService.getSubscribers().size());
    }

    @Test
    void nullUserSubscriptionServiceTest() {
        SubscriptionService subscriptionService =
                new SubscriptionService();

        assertThrows(NullUserException.class,
                () -> subscriptionService.addSubscriber(null));
    }

    @Test
    void existingUserSubscriptionServiceTest() {
        SubscriptionService subscriptionService =
                new SubscriptionService();
        User user = Mockito.mock(User.class);
        assertDoesNotThrow(()->subscriptionService.addSubscriber(user));
        assertThrows(ExistingUserException.class,
                () -> subscriptionService.addSubscriber(user));
    }

    @Test
    void localUserSubscriptionServiceTest() throws NoSuchFieldException, IllegalAccessException {
        SubscriptionService subscriptionService =
                new SubscriptionService();
        User user = Mockito.mock(User.class);
        when(user.getDelivery()).thenReturn(Delivery.LOCAL);
        when(user.getEmail()).thenReturn("abc");
        assertThrows(LocalUserDoesNotHaveNullEmailException.class,
                () -> subscriptionService.addSubscriber(user));
    }
}
