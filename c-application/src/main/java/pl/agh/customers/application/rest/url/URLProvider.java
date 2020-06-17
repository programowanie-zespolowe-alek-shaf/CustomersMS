package pl.agh.customers.application.rest.url;

import pl.agh.customers.application.rest.MicroService;

public interface URLProvider {

    String getBaseURL(MicroService microService);
}
