package co.edu.uniquindio.apis.messaging;


import io.smallrye.reactive.messaging.MutinyEmitter;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

import io.nats.client.Connection;

import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;


@ApplicationScoped
public class NatsService {
    private Connection connection;

    @Inject
    @Channel("sms")
    MutinyEmitter<String> smsEmitter;
    public void publishSms(String json) {
        smsEmitter.send(json)
                .subscribe().with(
                        success -> System.out.println("✅ Email publicado"),
                        failure -> System.err.println("❌ Error al publicar email: " + failure.getMessage())
                );
    }

    @Inject
    @Channel("email")
    MutinyEmitter<String> mailEmitter;

    public void publishMail(String json) {
        mailEmitter.send(json)
                .subscribe().with(
                        success -> System.out.println("✅ Email publicado"),
                        failure -> System.err.println("❌ Error al publicar email: " + failure.getMessage())
                );
    }
}


