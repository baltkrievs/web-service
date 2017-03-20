package by.darashchonak.mentoring.service.app;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

public class ServiceApplication extends ResourceConfig {

    public ServiceApplication() {
        packages("by.darashchonak.mentoring.service");
        packages("org.glassfish.jersey.examples.multipart").register(MultiPartFeature.class);
        register(org.glassfish.jersey.server.filter.UriConnegFilter.class);
        property(ServerProperties.METAINF_SERVICES_LOOKUP_DISABLE, true);
    }

}
