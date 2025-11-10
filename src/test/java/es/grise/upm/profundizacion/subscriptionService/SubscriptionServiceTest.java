package es.grise.upm.profundizacion.subscriptionService;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.Test;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import es.grise.upm.profundizacion.exceptions.ExistingUserException;
import es.grise.upm.profundizacion.exceptions.LocalUserDoesNotHaveNullEmailException;
import es.grise.upm.profundizacion.exceptions.NullUserException;
import es.grise.upm.profundizacion.subscriptionService.Delivery;
import es.grise.upm.profundizacion.subscriptionService.SubscriptionService;
import es.grise.upm.profundizacion.subscriptionService.User;


public class SubscriptionServiceTest {
	
    private SubscriptionService service;
    private User localUserCorrect;
    private User localUserIncorrect;
    private User doNotDeliverUser;


    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void inicializar() {
        service = new SubscriptionService(Delivery.LOCAL);

        //No me deja hacer mockito
        localUserCorrect = new User() {
            @Override
            public Delivery getDelivery() { return Delivery.LOCAL; }
            @Override
            public String getEmail() { return null; }
        };

        localUserIncorrect = new User() {
            @Override
            public Delivery getDelivery() { return Delivery.LOCAL; }
            @Override
            public String getEmail() { return "mail@ejemplo.com"; }
        };

        doNotDeliverUser = new User() {
            @Override
            public Delivery getDelivery() { return Delivery.DO_NOT_DELIVER; }
            @Override
            public String getEmail() { return "mail@ejemplo.com"; }
        };
    }
       
    @Test
    public void testaddSubscriberNullUser() throws Exception{
        exceptionRule.expect(NullUserException.class);
        service.addSubscriber(null);
    }
   
    @Test
    public void testaddSubscriberExistingUser() throws Exception {
        /* No me deja hacer mockito
        User user = mock(User.class); 
        when(user.getDelivery()).thenReturn(Delivery.DO_NOT_DELIVER);
        when(user.getEmail()).thenReturn("mail@upm.es");
        */
        service.addSubscriber(doNotDeliverUser);

        exceptionRule.expect(ExistingUserException.class);
        service.addSubscriber(doNotDeliverUser);
    }

    @Test
    public void testaddSubscriberLocalCorrect() throws Exception {
        /* No me deja hacer mockito
        User user = mock(User.class);
        when(user.getDelivery()).thenReturn(Delivery.LOCAL);
        when(user.getEmail()).thenReturn(null);
        */
        service.addSubscriber(localUserCorrect);
        assertTrue(service.getSubscribers().contains(localUserCorrect));
    }

    @Test
    public void testaddSubscriberLocalIncorrect() throws Exception {
        /* No me deja hacer mockito
        User user = mock(User.class);
        when(user.getDelivery()).thenReturn(Delivery.LOCAL);
        when(user.getEmail()).thenReturn("mail@upm.es");
        */
        exceptionRule.expect(LocalUserDoesNotHaveNullEmailException.class);
        service.addSubscriber(localUserIncorrect);
    }

}



