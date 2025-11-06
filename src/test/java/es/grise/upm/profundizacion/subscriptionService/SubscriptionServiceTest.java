package es.grise.upm.profundizacion.subscriptionService;

import es.grise.upm.profundizacion.exceptions.ExistingUserException;
import es.grise.upm.profundizacion.exceptions.LocalUserDoesNotHaveNullEmailException;
import es.grise.upm.profundizacion.exceptions.NullUserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SubscriptionServiceTest {

    @BeforeEach
    public void setUp() {
        org.mockito.MockitoAnnotations.openMocks(this);
    }
	@Mock
    private User user;
    @Mock
    private Delivery delivery;

    @Test
    public void testConstructor() {
        new SubscriptionService(delivery);
    }
    @Test
    public void testAddSubscriberNullUser() throws NullUserException, LocalUserDoesNotHaveNullEmailException, ExistingUserException {
        SubscriptionService service = new SubscriptionService(delivery);
        try {
            service.addSubscriber(null);
        } catch (NullUserException e) {}
        catch (LocalUserDoesNotHaveNullEmailException e) {}
        catch (ExistingUserException e) {}
        assertTrue(service.getSubscribers().isEmpty());
    }
    @Test
    public void testAddSubscriberExistingUser() throws NullUserException, LocalUserDoesNotHaveNullEmailException, ExistingUserException {
        SubscriptionService service = new SubscriptionService(delivery);
        service.addSubscriber(user);
        try {
            service.addSubscriber(user);
        } catch (ExistingUserException e) {}
        catch (NullUserException e) {}
        catch (LocalUserDoesNotHaveNullEmailException e) {}
        assertTrue(service.getSubscribers().size() == 1);
        assertTrue(service.getSubscribers().contains(user));
    }
    @Test
    public void testLocalUserHasNullEmail() throws NullUserException, LocalUserDoesNotHaveNullEmailException, ExistingUserException {
        SubscriptionService service = new SubscriptionService(delivery);
        User user2 = new User() {
            @Override
            public String getEmail() {
                return null;
            }

            @Override
            public Delivery getDelivery() {
                return Delivery.LOCAL;
            }
        };
        try {
            service.addSubscriber(user2);
        }catch (LocalUserDoesNotHaveNullEmailException e) {}
        catch (ExistingUserException e) {}
        catch (NullUserException e) {}
        assertTrue(service.getSubscribers().contains(user2));
    }
    public void testLocalUserDoesNotHaveNullEmail() throws NullUserException, LocalUserDoesNotHaveNullEmailException, ExistingUserException {
        SubscriptionService service = new SubscriptionService(delivery);
        User user3 = new User() {
            @Override
            public String getEmail() {
                return "null";
            }

            @Override
            public Delivery getDelivery() {
                return Delivery.LOCAL;
            }
        };
        try {
            service.addSubscriber(user3);
        }catch (LocalUserDoesNotHaveNullEmailException e) {}
        catch (ExistingUserException e) {}
        catch (NullUserException e) {}
        assertTrue(service.getSubscribers().isEmpty());
    }
    @Test
    public void testAddSubscriber() throws NullUserException, LocalUserDoesNotHaveNullEmailException, ExistingUserException {
        SubscriptionService service = new SubscriptionService(delivery);
        service.addSubscriber(user);
        assertTrue(service.getSubscribers().size() == 1);
        assertTrue(service.getSubscribers().contains(user));
    }
    @Test
    public void testGetSubscribers() {
        SubscriptionService service = new SubscriptionService(delivery);
        assertTrue(service.getSubscribers().isEmpty());
    }

}
