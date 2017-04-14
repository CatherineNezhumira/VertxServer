package verticle;

import entity.Contact;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.core.json.Json;
import io.vertx.ext.web.handler.BodyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import service.ContactService;

public class ContactsVerticle extends AbstractVerticle {
    @Autowired
    private final ContactService service;

    public ContactsVerticle(final ApplicationContext context) {
        service = (ContactService) context.getBean("contactService");
    }


    @Override
    public void start(Future<Void> fut) {
//        router.route("/").handler(routingContext -> {
//            HttpServerResponse response = routingContext.response();
//            response
//                    .putHeader("content-type", "text/html")
//                    .end("<h1>Hello from my first Vert.x 3 application</h1>");
//        });
//
//        // Create the HTTP server and pass the "accept" method to the request handler.
//        vertx
//                .createHttpServer()
//                .requestHandler(router::accept)
//                .listen(
//                        // Retrieve the port from the configuration,
//                        // default to 8080.
//                        config().getInteger("http.port", 8080),
//                        result -> {
//                            if (result.succeeded()) {
//                                fut.complete();
//                            } else {
//                                fut.fail(result.cause());
//                            }
//                        }
//                );
//    }

        Router router = Router.router(vertx);
        router.route("/api/contacts*").handler(BodyHandler.create());
        router.get("/api/contacts").handler(this::getAll);
        router.post("/api/contacts").handler(this::addNewContact);
        router.delete("/api/contacts/:id").handler(this::deleteContact);
        router.options("/api/contacts*").handler(this::getOptions);
        router.put("/api/contacts").handler(this::updateContact);

        vertx
                .createHttpServer()
                .requestHandler(router::accept)
                .listen(8050, "localhost", result -> {
                    if (result.succeeded()) {
                        fut.complete();
                    } else {
                        fut.fail(result.cause());
                    }
                });
    }
    
    private void getAll(RoutingContext routingContext) {
        System.out.println("GET ALL " + Json.encodePrettily(service.getAllContacts()));
        routingContext.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json; charset=utf-8")
                .putHeader("Access-Control-Allow-Origin", "*")
                .putHeader("Access-Control-Allow-Methods", "GET, OPTIONS")
                .putHeader("Access-Control-Allow-Headers", "Content-Type, Authorization")
                .end(Json.encodePrettily(service.getAllContacts()));
    }

    private void addNewContact(RoutingContext routingContext) {
        final Contact contact = Json.decodeValue(routingContext.getBodyAsString(), Contact.class);
        int newContactId = service.addContact(contact);
        routingContext.response()
                .setStatusCode(201)
                .putHeader("content-type", "application/json; charset=utf-8")
                .putHeader("Access-Control-Allow-Origin", "*")
                .putHeader("Access-Control-Allow-Methods", "POST, OPTIONS")
                .putHeader("Access-Control-Allow-Headers", "Content-Type, Authorization")
                .end(Json.encodePrettily(newContactId));
    }

    private void deleteContact(RoutingContext routingContext) {
        service.deleteContact(Integer.parseInt(routingContext.request().getParam("id")));
        routingContext.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json; charset=utf-8")
                .putHeader("Access-Control-Allow-Origin", "*")
                .putHeader("Access-Control-Allow-Methods", "DELETE, OPTIONS")
                .putHeader("Access-Control-Allow-Headers", "Content-Type, Authorization")
                .end(Json.encodePrettily(routingContext.get("id")));
    }

    private void updateContact(RoutingContext routingContext) {
        final Contact contact = Json.decodeValue(routingContext.getBodyAsString(), Contact.class);
        service.updateContact(contact);
        routingContext.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json; charset=utf-8")
                .putHeader("Access-Control-Allow-Origin", "*")
                .putHeader("Access-Control-Allow-Methods", "PUT, OPTIONS")
                .putHeader("Access-Control-Allow-Headers", "Content-Type, Authorization")
                .end(Json.encodePrettily(contact));
    }

    private void getOptions(RoutingContext routingContext) {
        routingContext.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json; charset=utf-8")
                .putHeader("Access-Control-Allow-Origin", "*")
                .putHeader("Access-Control-Allow-Methods", "DELETE, PUT, OPTIONS")
                .putHeader("Access-Control-Allow-Headers", "Content-Type, Authorization")
                .end();
    }

}
