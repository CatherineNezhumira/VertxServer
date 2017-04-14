import context.AppConfiguration;
import io.vertx.core.Vertx;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import verticle.ContactsVerticle;

public class VertxApp {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new ContactsVerticle(context));
    }
}
