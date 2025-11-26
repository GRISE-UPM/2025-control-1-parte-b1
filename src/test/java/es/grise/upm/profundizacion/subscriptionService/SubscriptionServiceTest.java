package es.grise.upm.profundizacion.subscriptionService;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import es.grise.upm.profundizacion.exceptions.ExistingUserException;
import es.grise.upm.profundizacion.exceptions.LocalUserDoesNotHaveNullEmailException;
import es.grise.upm.profundizacion.exceptions.NullUserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;


public class SubscriptionServiceTest {

    SubscriptionService subscriptionService;

    @BeforeEach
    void setSubscriptionService(){
        subscriptionService = new SubscriptionService();
    }

    @Test
    void addUserCorrect(){
        User user = mock(User.class);
        assertDoesNotThrow(() -> subscriptionService.addSubscriber(user));
    }

    @Test
    void addNullUser(){
        User user = null;
        assertThrows(NullUserException.class, () -> subscriptionService.addSubscriber(user));
    }

    @Test
    void addDuplicatedUser(){
        User user = mock(User.class);
        try {
            subscriptionService.addSubscriber(user);
        } catch (NullUserException | ExistingUserException | LocalUserDoesNotHaveNullEmailException exception) {

        }
        assertThrows(ExistingUserException.class, () -> subscriptionService.addSubscriber(user));
    }

    @Test
    void emailCanBeNotNullWhenNotLocalDelivery() {
        User user = mock(User.class);
        when(user.getDelivery()).thenReturn(Delivery.DO_NOT_DELIVER);
        when(user.getEmail()).thenReturn("email@gmail.com");
        assertDoesNotThrow(() -> subscriptionService.addSubscriber(user));
    }

    @Test
    void emailShouldBeNullWhenLocalDelivery () {
        User user = mock(User.class);
        when(user.getDelivery()).thenReturn(Delivery.LOCAL);
        when(user.getEmail()).thenReturn("email@gmail.com");
        assertThrows(LocalUserDoesNotHaveNullEmailException.class, () -> subscriptionService.addSubscriber(user));
        when(user.getEmail()).thenReturn(null);
        assertDoesNotThrow(() -> subscriptionService.addSubscriber(user));

    }

}
