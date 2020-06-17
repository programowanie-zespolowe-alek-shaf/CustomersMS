package pl.agh.customers.application.rest.url;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pl.agh.customers.application.rest.MicroService;

@Component
@Profile("localhost")
@RequiredArgsConstructor
public class EurekaURLProvider implements URLProvider {

    private final EurekaClient eurekaClient;

    @Override
    public String getBaseURL(MicroService microService) {
        return getBaseURL(microService.getServiceId());
    }

    private String getBaseURL(String serviceId) {
        Application application = eurekaClient.getApplication(serviceId);
        InstanceInfo instanceInfo = application.getInstances().get(0);
        return "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort();
    }
}
