/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
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
import javax.persistence.EntityManagerFactory;
import nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
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
@ComponentScan(basePackages = {"nl.bzk.brp.beheer.webapp.controllers" })
public class WebConfiguratie extends WebMvcConfigurerAdapter {

    private static final int CACHE_PERIOD = 31556926;
    private static final Integer MAX_PAGE_SIZE = Integer.valueOf(10000);

    @Autowired
    private EntityManagerFactory[] entityManagerFactories;

    private final GlobalValidator globalValidator = new GlobalValidator();

    @Override
    @SuppressWarnings({"checkstyle:designforextension", "PMD.AvoidDuplicateLiterals"})
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new CorsInterceptor());

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
    @SuppressWarnings("checkstyle:designforextension")
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
    @SuppressWarnings("checkstyle:designforextension")
    public HateoasSortHandlerMethodArgumentResolver sortResolver() {
        return new HateoasSortHandlerMethodArgumentResolver();
    }

    /**
     * @return ViewResolver
     */
    @Bean
    @SuppressWarnings("checkstyle:designforextension")
    public UrlBasedViewResolver setupViewResolver() {
        final UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setPrefix("/");
        resolver.setSuffix(".html");
        resolver.setViewClass(JstlView.class);
        return resolver;
    }

    @Override
    @SuppressWarnings("checkstyle:designforextension")
    public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    @SuppressWarnings("checkstyle:designforextension")
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/META-INF/resources/webjars/").setCachePeriod(CACHE_PERIOD);
        registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(CACHE_PERIOD);
        registry.addResourceHandler("/fonts/**").addResourceLocations("/fonts/").setCachePeriod(CACHE_PERIOD);
        registry.addResourceHandler("/img/**").addResourceLocations("/img/").setCachePeriod(CACHE_PERIOD);
        registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(0);
        registry.addResourceHandler("/views/**").addResourceLocations("/views/").setCachePeriod(CACHE_PERIOD);
    }

    @Override
    @SuppressWarnings("checkstyle:designforextension")
    public void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {
        converters.add(createJacksonHttpConverter());

        super.configureMessageConverters(converters);
    }

    private HttpMessageConverter<Object> createJacksonHttpConverter() {
        final MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();
        jacksonConverter.setPrettyPrint(true);
        jacksonConverter.setObjectMapper(new BrpJsonObjectMapper());

        return jacksonConverter;
    }

    /**
     * @return Foutmeldingen resource bundle
     */
    @Bean(name = "foutmeldingen")
    @SuppressWarnings("checkstyle:designforextension")
    public ResourceBundle getFoutmeldingenResourceBundle() {
        try (InputStream is = WebConfiguratie.class.getResourceAsStream("/foutmeldingen.properties")) {
            return new PropertyResourceBundle(is);
        } catch (final IOException e) {
            throw new IllegalArgumentException("Kan foutmeldingen niet laden", e);
        }
    }

    @Override
    @SuppressWarnings("checkstyle:designforextension")
    public void addFormatters(final FormatterRegistry registry) {
        registry.addConverter(new Converter<String, Timestamp>() {

            @Override
            public final Timestamp convert(final String source) {
                Timestamp timestamp;
                try {
                    timestamp = new Timestamp(new SimpleDateFormat("yyyyMMddHHmm").parse(source).getTime());
                } catch (final ParseException e) {
                    timestamp = null;
                }
                return timestamp;
            }
        });
    }

    @Override
    @SuppressWarnings("checkstyle:designforextension")
    public Validator getValidator() {
        return globalValidator;
    }

    /**
     * Registreer alle specifieke validators (om te injecteren in globale validator).
     *
     * @param validators alle validators
     */
    @Autowired
    @SuppressWarnings("checkstyle:designforextension")
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
         *
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
