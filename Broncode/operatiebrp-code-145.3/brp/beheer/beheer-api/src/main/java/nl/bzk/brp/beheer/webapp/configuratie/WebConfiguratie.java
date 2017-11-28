/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.HateoasSortHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

/**
 * Spring WebMVC configuratie.
 */
@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport
@ComponentScan(basePackages = {"nl.bzk.brp.beheer.webapp.controllers", "nl.bzk.brp.beheer.webapp.service"})
@Import(value = JsonConfiguratie.class)
@ImportResource(value = "classpath:webservice-client.xml")

public class WebConfiguratie extends WebMvcConfigurerAdapter {

    private static final int CACHE_PERIOD = 31_556_926;
    private static final Integer MAX_PAGE_SIZE = 10_000;
    private static final long MAX_AGE = 3600L;

    private final EntityManagerFactory[] entityManagerFactories;
    private final BrpJsonObjectMapper brpJsonObjectMapper;

    private final GlobalValidator globalValidator = new GlobalValidator();

    /**
     * Constructor
     * @param entityManagerFactories entity manager factories
     * @param brpJsonObjectMapper mapper voor json naar java en terug
     */
    @Inject
    public WebConfiguratie(final EntityManagerFactory[] entityManagerFactories, final BrpJsonObjectMapper brpJsonObjectMapper) {
        this.entityManagerFactories = entityManagerFactories;
        this.brpJsonObjectMapper = brpJsonObjectMapper;
    }

    @Override
    public final void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS")
                .allowedHeaders(
                        "Host",
                        "User-Agent",
                        "X-Requested-With",
                        "Accept",
                        "Accept-Language",
                        "Accept-Encoding",
                        "Authorization",
                        "Referer",
                        "Connection",
                        "Content-Type")
                .exposedHeaders("header1", "header2")
                .maxAge(MAX_AGE)
                .allowCredentials(true);
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {

        for (final EntityManagerFactory entityManagerFactory : entityManagerFactories) {
            final OpenEntityManagerInViewInterceptor openEntityManagerInViewInterceptor = new OpenEntityManagerInViewInterceptor();
            openEntityManagerInViewInterceptor.setEntityManagerFactory(entityManagerFactory);
            registry.addWebRequestInterceptor(openEntityManagerInViewInterceptor);
        }

        super.addInterceptors(registry);
    }

    /**
     * @return PageableHandlerResolver
     */
    @Bean
    public HateoasPageableHandlerMethodArgumentResolver pageableResolver() {
        final HateoasPageableHandlerMethodArgumentResolver hateoasPageableHandlerMethodArgumentResolver =
                new HateoasPageableHandlerMethodArgumentResolver(sortResolver());
        hateoasPageableHandlerMethodArgumentResolver.setMaxPageSize(MAX_PAGE_SIZE);
        return hateoasPageableHandlerMethodArgumentResolver;
    }

    /**
     * @return SortResolver
     */
    @Bean
    public HateoasSortHandlerMethodArgumentResolver sortResolver() {
        return new HateoasSortHandlerMethodArgumentResolver();
    }

    /**
     * @return ViewResolver
     */
    @Bean
    public UrlBasedViewResolver setupViewResolver() {
        final UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setPrefix("/");
        resolver.setSuffix(".html");
        resolver.setViewClass(JstlView.class);
        return resolver;
    }

    @Override
    public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/META-INF/resources/webjars/").setCachePeriod(CACHE_PERIOD);
        registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(CACHE_PERIOD);
        registry.addResourceHandler("/fonts/**").addResourceLocations("/fonts/").setCachePeriod(CACHE_PERIOD);
        registry.addResourceHandler("/img/**").addResourceLocations("/img/").setCachePeriod(CACHE_PERIOD);
        registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(0);
        registry.addResourceHandler("/views/**").addResourceLocations("/views/").setCachePeriod(CACHE_PERIOD);
    }

    @Override
    public void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {
        converters.add(createJacksonHttpConverter());

        super.configureMessageConverters(converters);
    }

    private HttpMessageConverter<Object> createJacksonHttpConverter() {
        final MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter(brpJsonObjectMapper);
        jacksonConverter.setPrettyPrint(true);

        return jacksonConverter;
    }

    /**
     * @return Foutmeldingen resource bundle
     */
    @Bean(name = "foutmeldingen")
    public ResourceBundle getFoutmeldingenResourceBundle() {
        try (InputStream is = WebConfiguratie.class.getResourceAsStream("/foutmeldingen.properties")) {
            return new PropertyResourceBundle(is);
        } catch (final IOException e) {
            throw new IllegalArgumentException("Kan foutmeldingen niet laden", e);
        }
    }

    private interface StringToTimestampConverter extends Converter<String, Timestamp> {
    }

    @Override
    public void addFormatters(final FormatterRegistry registry) {
        StringToTimestampConverter converter = source -> {
            Timestamp timestamp;
            try {
                timestamp = new Timestamp(new SimpleDateFormat("yyyyMMddHHmm").parse(source).getTime());
            } catch (final ParseException e) {
                timestamp = null;
            }
            return timestamp;
        };
        registry.addConverter(converter);
    }

    @Override
    public Validator getValidator() {
        return globalValidator;
    }

    /**
     * Registreer alle specifieke validators (om te injecteren in globale validator).
     * @param validators alle validators
     */
    @Inject
    public void setValidators(final Validator[] validators) {
        globalValidator.setValidators(validators);
    }

    /**
     * Globale validator om validators te verzamelen en ze niet apart aan controllers te hoeven binden.
     */
    public static final class GlobalValidator implements Validator {
        private final List<Validator> validators = new ArrayList<>();

        /**
         * Registreer de specifieke validators.
         * @param validators validators
         */
        public void setValidators(final Validator[] validators) {
            for (final Validator validator : validators) {
                if (!(validator instanceof GlobalValidator)) {
                    this.validators.add(validator);
                }
            }
        }

        @Override
        public boolean supports(final Class<?> clazz) {
            for (final Validator validator : validators) {
                if (validator.supports(clazz)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public void validate(final Object target, final Errors errors) {
            for (final Validator validator : validators) {
                if (validator.supports(target.getClass())) {
                    validator.validate(target, errors);
                }
            }
        }

    }
}
