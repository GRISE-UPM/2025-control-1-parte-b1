package es.grise.upm.profundizacion.subscriptionService;

import org.junit.jupiter.api.Test;
import org.junit.Jupiter.*;
import org.mockito.Mock.*;

public class SubscriptionServiceTest {
	
	@Mock
	void SetUp() {
		
	}
	
	@Test
	void addSubscriberTest() {
		assertThrows(NullUserException。class,()->{
			
		});
		assertThrows(LocalUserDoesNotHaveNullEmailException.class,()->{
			
		});
		assertThrows(ExistingUserException.class,()->{
			
		});
	}
}
