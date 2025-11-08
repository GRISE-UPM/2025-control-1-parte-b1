package es.grise.upm.profundizacion.subscriptionService;

public class User {
	String email = null;
	Delivery delivery = null;

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
    }
    public void setEmail(String email){
        this.setEmail(email);
    }
	public String getEmail() { return email;};
	public Delivery getDelivery() {return delivery;};
	
}
