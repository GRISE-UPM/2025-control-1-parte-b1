package es.grise.upm.profundizacion.subscriptionService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.grise.upm.profundizacion.exceptions.ExistingUserException;
import es.grise.upm.profundizacion.exceptions.LocalUserDoesNotHaveNullEmailException;
import es.grise.upm.profundizacion.exceptions.NullUserException;

public class SubscriptionServiceTest {
	private SubscriptionService sserv;
	
	@BeforeEach
	public void testSetUp() {
		// Creamos servicio
		sserv = new SubscriptionService(Delivery.LOCAL);
		
		// Comprobamos que hay 0 subscriptores
		assertEquals(0, sserv.getSubscribers().size());
	}
	
	/**
	 * Test para comprobar si "addSubscriber" lanza correctamente la
	 * excepcion "NullUserException"
	 */
	@Test
	public void testNullUserException() {
		// Comprobamos que al intentar añadir null se lanza la excepcion
		assertThrows(NullUserException.class, () -> {
			sserv.addSubscriber(null);
		});
	}
	
	/**
	 * Test para comprobar si "addSubscriber" lanza correctamente la
	 * excepcion "ExistingUserException" 
	 * @throws NullUserException
	 * @throws ExistingUserException
	 * @throws LocalUserDoesNotHaveNullEmailException
	 */
	@Test
	public void testExistingUserException() throws NullUserException, ExistingUserException, LocalUserDoesNotHaveNullEmailException {
		// Hacemos mock de usuario
		User userMock = mock(User.class);
		
		// Establecemos Delivery de tipo DO_NOT_DELIVER
		when(userMock.getDelivery()).thenReturn(Delivery.DO_NOT_DELIVER);
		
		// Añadimos el usuario
		sserv.addSubscriber(userMock);
		
		// Comprobamos que al intentar añadir el usuario una segunda vez, se lanza la excepcion
		assertThrows(ExistingUserException.class, () -> {
			sserv.addSubscriber(userMock);
		});
	}
	
	/**
	 * Test para comprobar si "addSubscriber" lanza correctamente la
	 * excepcion "LocalUserDoesNotHaveNullEmailException" 
	 * @throws NullUserException
	 * @throws ExistingUserException
	 * @throws LocalUserDoesNotHaveNullEmailException
	 */
	@Test
	public void testLocalUserNoNullEmailException() throws NullUserException, ExistingUserException, LocalUserDoesNotHaveNullEmailException {
		// Hacemos mock de usuario
		User userMock = mock(User.class);
		
		// Establecemos que responda que su Delivery es LOCAL
		when(userMock.getDelivery()).thenReturn(Delivery.LOCAL);
		
		// Establecemos que responda que email existe
		when(userMock.getEmail()).thenReturn("test@test.com");
		
		// Comprobamos que al intentar añadir el usuario, se lanza la excepcion
		assertThrows(LocalUserDoesNotHaveNullEmailException.class, () -> {
			sserv.addSubscriber(userMock);
		});
	}
	
	/**
	 * Test para comprobar el funcionamiento de "addSubscriber"
	 * bajo condiciones normales
	 * @throws NullUserException
	 * @throws ExistingUserException
	 * @throws LocalUserDoesNotHaveNullEmailException
	 */
	@Test
	public void testAddSubscribers() throws NullUserException, ExistingUserException, LocalUserDoesNotHaveNullEmailException {
		// Hacemos mock de usuario
		User userMock = mock(User.class);
		
		// Establecemos que responda que su Delivery es LOCAL
		when(userMock.getDelivery()).thenReturn(Delivery.LOCAL);
		
		// Añadimos el usuario
		sserv.addSubscriber(userMock);
		
		// Hacemos mock de un segundo usuario
		User user2Mock = mock(User.class);
		
		// Establecemos que responda que su Delivery es LOCAL
		when(user2Mock.getDelivery()).thenReturn(Delivery.DO_NOT_DELIVER);
		
		// Establecemos que responda que email existe
		when(user2Mock.getEmail()).thenReturn("test@test.com");
		
		// Añadimos el usuario
		sserv.addSubscriber(user2Mock);
		
		// Comprobamos que se han añadido correctamente los 2 usuarios
		assertEquals(2, sserv.getSubscribers().size());
	}
}
