package co.edu.uniquindio.apis.services.health;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.resource.spi.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

@Readiness
@ApplicationScoped
public class ProgramHealthCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {


        return null;
    }
}
