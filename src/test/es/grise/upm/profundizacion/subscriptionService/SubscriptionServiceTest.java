package es.grise.upm.profundizacion.subscriptionService;
import es.grise.upm.profundizacion.exceptions.ExistingUserException;
import es.grise.upm.profundizacion.exceptions.LocalUserDoesNotHaveNullEmailException;
import es.grise.upm.profundizacion.exceptions.NullUserException;
import es.grise.upm.profundizacion.subscriptionService.*;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SubscriptionServiceTest {
    Delivery deliverL = Delivery.LOCAL;
    Delivery deliverD = Delivery.DO_NOT_DELIVER;
    SubscriptionService subscription = new SubscriptionService(deliverD);
    User usuarioNull = null;
    User usuario = new User();
    User usuarioSinEmail = new User();
    @Test
    public void testAñadirSubscriptorCorrecto() throws NullUserException, LocalUserDoesNotHaveNullEmailException, ExistingUserException {
        usuario.setEmail("email@gmail.com");
        usuario.setDelivery(deliverD);
        subscription.addSubscriber(usuario);
        assertEquals(usuario, subscription.getSubscribers().iterator().next(), "usuario incorrecto");
    }
    @Test
    public void testUsuarioRepetido() throws NullUserException, LocalUserDoesNotHaveNullEmailException, ExistingUserException {
        usuario.setEmail("email@gmail.com");
        usuario.setDelivery(deliverD);
        subscription.addSubscriber(usuario);
        assertThrows(ExistingUserException.class, () -> subscription.addSubscriber(usuario),"el usuario no estaba introducido anteriormente");
    }

    @Test
    public void testUsuarioNulo() throws NullUserException, LocalUserDoesNotHaveNullEmailException, ExistingUserException {
        assertThrows(NullUserException.class, () -> subscription.addSubscriber(usuarioNull), "el usuario no es nulo");
    }
    @Test
    public void testUsuarioLocalConEmail() throws NullUserException, LocalUserDoesNotHaveNullEmailException, ExistingUserException {
        usuarioSinEmail.setDelivery(deliverL);
        usuarioSinEmail.setEmail("emailNuevo@gmail.com");
        assertThrows(LocalUserDoesNotHaveNullEmailException.class, () -> subscription.addSubscriber(usuarioSinEmail),"el usuario tiene email o no es local");
    }
    @Test
    public void testUsuarioLocalEmailNull() throws NullUserException, LocalUserDoesNotHaveNullEmailException, ExistingUserException {
        usuario.setEmail(null);
        usuario.setDelivery(deliverL);
        subscription.addSubscriber(usuario);
        assertEquals(usuario, subscription.getSubscribers().iterator().next(), "el usuario tiene email o no es local");
    }
}
