package es.grise.upm.profundizacion.subscriptionService;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.beans.Transient;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import es.grise.upm.profundizacion.*;
import es.grise.upm.profundizacion.exceptions.NullUserException;

public class SubscriptionServiceTest {
	
    private SubscriptionService sub; 
    private User mockUser1; 
    private Delivery mockDelivery1;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void inicializar() {
        mockDelivery = mock(Delivery.class);
        sub= new SubscriptionService(mockDelivery);
        mockUser1 = mock(User.class);
        mockUser2 = mock(User.class);
    }
       
    @Test
    public void testaddSuscriber(){
       
        sub.addSubscriber(mockUser1);
        assertEquals(1,sub.getSubscribers().size());
    }

    @Test(expected = NullUserException.class)
    public void testNullUser() throws NullUserException {
        sub.addSubscriber(null);
    }
   
    @Test(expected = ExistingUserException.class)
    public void testExistingUser() throws ExistingUserException {
        sub.addSubscriber(mockUser1);
    }

    @Test(expected = LocalUserDoesNotHaveNullEmailExceptio.class)
    public void testLocal() throws LocalUserDoesNotHaveNullEmailException {
         when(mockUser2.getDelivery).thenReturn(LOCAl);
         sub.addSubscriber(2);
         assertEquals(null,mockDelivery.getEmail());
    }


}



