package es.grise.upm.profundizacion.subscriptionService;

import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SubscriptionServiceTest {
	private SubscriptionService service;

    @Mock
    private User userMock;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new SubscriptionService(Delivery.LOCAL);
    }

    @Test
    public void testAddSubscriberSucceedsForRemoteUserWithEmail() throws Exception {
        when(userMock.getDelivery()).thenReturn(Delivery.DO_NOT_DELIVER);
        when(userMock.getEmail()).thenReturn("remote@example.com");


        service.addSubscriber(userMock);
    }

    @Test
    public void testAddSubscriberAllowsLocalUserWithNullEmail() throws Exception {
        when(userMock.getDelivery()).thenReturn(Delivery.LOCAL);
        when(userMock.getEmail()).thenReturn(null);

        service.addSubscriber(userMock);
    }

    @Test
    public void testAddDifferentUsersSucceeds() throws Exception {
        when(userMock.getDelivery()).thenReturn(Delivery.DO_NOT_DELIVER);
        when(userMock.getEmail()).thenReturn("unique1@example.com");


        service.addSubscriber(userMock);

        User second = org.mockito.Mockito.mock(User.class);
        when(second.getDelivery()).thenReturn(Delivery.DO_NOT_DELIVER);
        when(second.getEmail()).thenReturn("unique2@example.com");

        service.addSubscriber(second);
    }
}

