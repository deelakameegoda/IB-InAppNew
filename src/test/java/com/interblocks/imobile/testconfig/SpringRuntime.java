package com.interblocks.imobile.testconfig;

import com.interblocks.imobile.api.inapp.oauth.InappOauth2Util;
import com.interblocks.imobile.api.inapp.oauth.InappTokenCipher;
import com.interblocks.imobile.api.inapp.service.card.CardManagementApiServiceImpl;
import com.interblocks.imobile.api.inapp.service.registration.RegistrationApiServiceImpl;
import com.interblocks.imobile.api.inapp.service.user.UserManagementApiServiceImpl;
import com.interblocks.imobile.subcomponents.inapp.InAppRegistrationServiceImpl;
import com.interblocks.imobile.testmocks.services.MockInAppCardServiceImpl;
import com.interblocks.imobile.testmocks.services.MockInAppRegistrationServiceImpl;
import com.interblocks.imobile.testmocks.services.MockInAppUserServiceImpl;
import com.interblocks.iwallet.adaptor.broker.BrokerClient;
import com.interblocks.iwallet.adaptor.broker.BrokerCommunicator;
import com.interblocks.iwallet.adaptor.broker.components.JAXBParser;
import com.interblocks.iwallet.adaptor.broker.config.ServiceBrokerClasses;
import com.interblocks.iwallet.adaptor.communicators.card.CardServiceCommunicator;
import com.interblocks.iwallet.adaptor.rest.admin.IAdminRestClientInappImpl;
import com.interblocks.iwallet.config.hazelcast.HazelcastConfig;
import com.interblocks.iwallet.isodetails.builder.ISODetailsMapper;
import com.interblocks.iwallet.oauth.Oauth2Util;
import com.interblocks.iwallet.oauth.ResourceOwnerPasswordCredentialsGrant;
import com.interblocks.iwallet.oauth.TokenCipher;
import com.interblocks.iwallet.subcomponents.cache.CacheServiceImpl;
import com.interblocks.iwallet.subcomponents.fundtransfers.util.CardExpiry;
import com.interblocks.webtools.broker.model.card.BalanceInquiryResponse;
import com.interblocks.webtools.broker.model.core.DefaultRequest;
import com.interblocks.webtools.broker.model.core.DefaultResponse;
import com.interblocks.webtools.broker.model.transaction.TrxMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.web.client.RestTemplate;

@Slf4j
@SpringBootTest()
@PropertySource(value = {"classpath:testapplication.properties","classpath:application-test.properties"})
@ComponentScan({
        "com.hazelcast.core",
        "com.interblocks.iwallet.subcomponents.cache",
        "com.hazelcast.core.HazelcastInstance",
        "com.interblocks.iwallet.config.hazelcast"
})
@EnableJpaRepositories({
        "com.interblocks.iwallet.repository"
})
@EntityScan({
        "com.interblocks.iwallet.model"
})
public class SpringRuntime extends AbstractTransactionalTestNGSpringContextTests {

    @Configuration
    static class ContextConfiguration {

        @Bean
        @Primary
        public CardManagementApiServiceImpl cardManagementApiServiceTest() {
            return new CardManagementApiServiceImpl(new MockInAppCardServiceImpl());
        }

        @Bean
        @Primary
        public RegistrationApiServiceImpl registrationApiServiceTest() {
            return new RegistrationApiServiceImpl(new MockInAppRegistrationServiceImpl());
        }

        @Bean
        @Primary
        public UserManagementApiServiceImpl userManagementApiServiceTest() {
            return new UserManagementApiServiceImpl(new MockInAppUserServiceImpl());
        }

        @Bean
        public IAdminRestClientInappImpl iAdminRestClientInapp() {
            return new IAdminRestClientInappImpl();
        }

        @Bean
        public CardServiceCommunicator cardServiceCommunicator() {
            return new CardServiceCommunicator();
        }

        @Bean
        public InAppRegistrationServiceImpl inAppRegistrationService() {
            return new InAppRegistrationServiceImpl();
        }

        @Bean
        public CacheServiceImpl cacheService() {
            return new CacheServiceImpl();
        }

        @Bean
        public HazelcastConfig hazelcastConfig() {
            return new HazelcastConfig();
        }

        @Bean
        public ISODetailsMapper isoDetailsMapper() {
            return new ISODetailsMapper();
        }

        @Bean
        public ResourceOwnerPasswordCredentialsGrant resourceOwnerPasswordCredentialsGrant() {
            return new ResourceOwnerPasswordCredentialsGrant();
        }

        @Bean
        public Oauth2Util oauth2Util() {
            return new Oauth2Util();
        }

        @Bean
        public TokenCipher tokenCipher() {
            return new TokenCipher();
        }

        @Bean
        public InappOauth2Util inappOauth2Util() {
            return new InappOauth2Util();
        }

        @Bean
        public InappTokenCipher inappTokenCipher() {
            return new InappTokenCipher();
        }

        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }

        @Bean
        public BrokerCommunicator brokerCommunicator() {
            return new BrokerCommunicator();
        }

        @Bean
        public BrokerClient brokerClient() {
            return new BrokerClient();
        }

        @Bean
        public CardExpiry cardExpiry() {
            return new CardExpiry();
        }

        @Bean
        public JAXBParser jaxbParser() {
            ServiceBrokerClasses serviceBrokerClasses = new ServiceBrokerClasses();
            Class<?>[] classes = new Class[]{
                    DefaultRequest.class,
                    DefaultResponse.class,
                    BalanceInquiryResponse.class,
                    TrxMsg.class
            };
            serviceBrokerClasses.setClasses(classes);
            return new JAXBParser(serviceBrokerClasses);
        }
    }
}

