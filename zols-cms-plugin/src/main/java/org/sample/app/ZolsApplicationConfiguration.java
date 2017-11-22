/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sample.app;

import java.util.Locale;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import static org.elasticsearch.common.settings.Settings.settingsBuilder;
import static org.elasticsearch.node.NodeBuilder.nodeBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.zols.datastore.DataStore;
import org.zols.datastore.elasticsearch.ElasticSearchDataStorePersistence;

@Configuration
public class ZolsApplicationConfiguration extends WebMvcConfigurerAdapter {
    
    @Value("${spring.application.name}")
    private String indexName;
        
    @Bean
    public DataStore dataStore() {
        Settings settings = settingsBuilder().put("index.number_of_shards", 1)
                .put("path.home", "/")
                .put("path.data", "data")
                .put("index.number_of_replicas", 0).build();
        Client client = nodeBuilder().settings(settings).build().start().client();
        if(client == null) {
            return new DataStore(new ElasticSearchDataStorePersistence(indexName));
        }
        return new DataStore(new ElasticSearchDataStorePersistence(indexName,client));
    }

   

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.ENGLISH);
        return slr;
    }
}