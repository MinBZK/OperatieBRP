/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie;

import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * Security configuratie.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguratie extends WebSecurityConfigurerAdapter {

    private static final String ROLE_USER = "USER";
    private static final String ANT_ALL_MATCHER = "/**";
    private static final String USER_SEARCH_BASE_DN = "cn={0},ou=Users,ou=Data,o=BPR,dc=bprbzk,dc=nl";
    private static final String GROUP_SEARCH_BASE_DN = "ou=Groups,ou=Data,o=BPR,dc=bprbzk,dc=nl";
    private static final String GROUP_SEARCH_FILTER = "(member={0})";
    private static final String PROVIDER_URL = "ldap://lacita01.modernodam.nl:10389/";

    private static final String GEGEVENS_BEHEERDER = "gegevens";
    private static final String FUNCTIONEEL_BEHEERDER = "functioneel";
    private static final String ADMINISTRATOR = "admin";
    private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";

    @Inject
    private Environment environment;

    @Override
    @SuppressWarnings({"PMD.SignatureDeclareThrowsException", "PMD.AvoidDuplicateLiterals", "checkstyle:designforextension"})
    public void configure(final AuthenticationManagerBuilder auth) throws Exception {

        if (Boolean.parseBoolean(environment.getProperty("useInMemoryAuthentication", "false"))) {
            final InMemoryUserDetailsManagerConfigurer<?> userConfigurer = auth.inMemoryAuthentication();
            userConfigurer.withUser(GEGEVENS_BEHEERDER).password(GEGEVENS_BEHEERDER).roles(ROLE_USER, SecurityConstants.ROL_GEGEVENS_BEHEER);
            userConfigurer.withUser(FUNCTIONEEL_BEHEERDER).password(FUNCTIONEEL_BEHEERDER).roles(ROLE_USER, SecurityConstants.ROL_FUNCTIONEEL_BEHEER);
            userConfigurer.withUser(ADMINISTRATOR)
                .password(ADMINISTRATOR)
                .roles(
                    ROLE_USER,
                    SecurityConstants.ROL_AUDITOR,
                    SecurityConstants.ROL_BEHEERDER_STAMTABELLEN,
                    SecurityConstants.ROL_FUNCTIONEEL_BEHEER,
                    SecurityConstants.ROL_GEGEVENS_BEHEER,
                    SecurityConstants.ROL_SUPER_FUNCTIONEEL_BEHEER,
                    SecurityConstants.ROL_SUPER_GEGEVENS_BEHEER,
                    SecurityConstants.ROL_TECHNISCH_BEHEER);
        } else {
            auth.ldapAuthentication()
            .contextSource()
            .url(PROVIDER_URL)
            .and()
            .userDnPatterns(USER_SEARCH_BASE_DN)
            .groupSearchFilter(GROUP_SEARCH_FILTER)
                .groupSearchBase(GROUP_SEARCH_BASE_DN);
        }
    }

    @Bean
    @Override
    @SuppressWarnings({"PMD.SignatureDeclareThrowsException", "checkstyle:designforextension"})
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @Override
    @SuppressWarnings({"PMD.SignatureDeclareThrowsException", "checkstyle:designforextension"})
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }

    @Override
    @SuppressWarnings({"PMD.SignatureDeclareThrowsException", "checkstyle:designforextension", "checkstyle:methodlength"})
    protected void configure(final HttpSecurity http) throws Exception {

        http.authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS, ANT_ALL_MATCHER)
            .permitAll()
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, ControllerConstants.LOGOUT)
            .permitAll()
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, ControllerConstants.VERSIE_URI, ControllerConstants.CONFIGURATIE_URI)
            .permitAll()
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, ControllerConstants.USER_URI + ANT_ALL_MATCHER)
            .authenticated()

            // Inzien
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, ANT_ALL_MATCHER)
            .authenticated()
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.PUT, ANT_ALL_MATCHER)
            .denyAll()
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.DELETE, ANT_ALL_MATCHER)
            .denyAll()
            .and()
            .authorizeRequests()
        .antMatchers(
            HttpMethod.GET,
            ControllerConstants.ACTIE_URI + ANT_ALL_MATCHER,
            ControllerConstants.ADMINISTRATIEVE_HANDELING_URI + ANT_ALL_MATCHER,
            ControllerConstants.BERICHT_URI + ANT_ALL_MATCHER,
            ControllerConstants.GEDEBLOKKEERDE_MELDING_URI,
            ControllerConstants.PERSOON_URI + ANT_ALL_MATCHER,
            ControllerConstants.PERSOON_VOLLEDIG_URI + ANT_ALL_MATCHER)
            .hasRole(SecurityConstants.ROL_GEGEVENS_BEHEER)

            // dubbele rollen
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, ControllerConstants.LEVERINGSAUTORISATIE_URI + ANT_ALL_MATCHER, ControllerConstants.PARTIJ_URI + ANT_ALL_MATCHER)
            .hasAnyRole(SecurityConstants.ROL_GEGEVENS_BEHEER, SecurityConstants.ROL_FUNCTIONEEL_BEHEER)

            // Abonnementen
            .and()
            .authorizeRequests()
                    .antMatchers(
                        HttpMethod.GET,
                        ControllerConstants.DIENST_URI + ANT_ALL_MATCHER,
                        ControllerConstants.TOEGANGABONNEMENT_URI + ANT_ALL_MATCHER,
                        ControllerConstants.AFLEVERWIJZE_URI + ANT_ALL_MATCHER,
                        ControllerConstants.LO3_FILTER_RUBRIEK + ANT_ALL_MATCHER)
            .hasRole(SecurityConstants.ROL_FUNCTIONEEL_BEHEER)

            .and()
            .authorizeRequests()
                        .antMatchers(
                            HttpMethod.POST,
                            ControllerConstants.PARTIJ_URI + ANT_ALL_MATCHER,
                            ControllerConstants.LEVERINGSAUTORISATIE_URI + ANT_ALL_MATCHER,
                            ControllerConstants.DIENST_URI + ANT_ALL_MATCHER,
                            ControllerConstants.TOEGANGABONNEMENT_URI + ANT_ALL_MATCHER,
                            ControllerConstants.AFLEVERWIJZE_URI + ANT_ALL_MATCHER,
                            ControllerConstants.LO3_FILTER_RUBRIEK + ANT_ALL_MATCHER)
            .hasRole(SecurityConstants.ROL_FUNCTIONEEL_BEHEER)

            // Stamgegevens
            .and()
            .authorizeRequests()
                            .antMatchers(
                                HttpMethod.GET,
                                ControllerConstants.AANDUIDING_MEDIUM_URI + ANT_ALL_MATCHER,
                                ControllerConstants.CATALOGUS_OPTIE_URI + ANT_ALL_MATCHER,
                                ControllerConstants.CATEGORIE_DIENST_URI + ANT_ALL_MATCHER,
                                ControllerConstants.EFFECT_AFNEMERINDICATIES_URI + ANT_ALL_MATCHER,
                                ControllerConstants.FUNCTIE_URI + ANT_ALL_MATCHER,
                                ControllerConstants.KANAAL_URI + ANT_ALL_MATCHER,
                                ControllerConstants.PROTOCOLLERINGSNIVEAU_URI + ANT_ALL_MATCHER,
                                ControllerConstants.SOORT_AUTORISATIEBESLUIT_URI + ANT_ALL_MATCHER,
                                ControllerConstants.SOORT_BEVOEGDHEID_URI + ANT_ALL_MATCHER,
                                ControllerConstants.TOESTAND_URI + ANT_ALL_MATCHER,
                                ControllerConstants.BIJHOUDINGSRESULTAAT_URI + ANT_ALL_MATCHER,
                                ControllerConstants.BIJHOUDINGSSITUATIE_URI + ANT_ALL_MATCHER,
                                ControllerConstants.HISTORIEVORM_URI + ANT_ALL_MATCHER,
                                ControllerConstants.RICHTING_URI + ANT_ALL_MATCHER,
                                ControllerConstants.SOORT_BERICHT_URI + ANT_ALL_MATCHER,
                                ControllerConstants.SOORT_MELDING_URI + ANT_ALL_MATCHER,
                                ControllerConstants.SOORT_SYNCHRONISATIE_URI + ANT_ALL_MATCHER,
                                ControllerConstants.VERWERKINGSRESULTAAT_URI + ANT_ALL_MATCHER,
                                ControllerConstants.VERWERKINGSWIJZE_URI + ANT_ALL_MATCHER,
                                ControllerConstants.REGELSITUATIE_URI + ANT_ALL_MATCHER,
                                ControllerConstants.REGEL_SOORT_BERICHT_URI + ANT_ALL_MATCHER,
                                ControllerConstants.REGELEFFECT_URI + ANT_ALL_MATCHER,
                                ControllerConstants.SOORT_REGEL_URI + ANT_ALL_MATCHER,
                                ControllerConstants.CONVERSIE_AANDUIDING_INHOUDING_VERMISSING_REISDOCUMENT_URI + ANT_ALL_MATCHER,
                                ControllerConstants.CONVERSIE_AANGIFTE_ADRESHOUDING_URI + ANT_ALL_MATCHER,
                                ControllerConstants.CONVERSIE_ADELLIJKE_TITEL_PREDIKAAT_URI + ANT_ALL_MATCHER,
                                ControllerConstants.CONVERSIE_REDEN_BEEINDIGEN_NATIONALITEIT_URI + ANT_ALL_MATCHER,
                                ControllerConstants.CONVERSIE_REDEN_ONTBINDING_HUWELIJK_GEREGISTREERD_PARTNERSCHAP_URI + ANT_ALL_MATCHER,
                                ControllerConstants.CONVERSIE_REDEN_BEEINDIGEN_NATIONALITEIT_URI + ANT_ALL_MATCHER,
                                ControllerConstants.CONVERSIE_REDEN_OPSCHORTING_URI + ANT_ALL_MATCHER,
                                ControllerConstants.CONVERSIE_RNI_DEELNEMER_URI + ANT_ALL_MATCHER,
                                ControllerConstants.CONVERSIE_SOORT_NL_REISDOCUMENT_URI + ANT_ALL_MATCHER,
                                ControllerConstants.CONVERSIE_VOORVOEGSEL_URI + ANT_ALL_MATCHER,
                                ControllerConstants.CONVERSIE_LO3_RUBRIEK_URI + ANT_ALL_MATCHER,
                                ControllerConstants.AUTORISATIETABEL_URI + ANT_ALL_MATCHER,
                                ControllerConstants.AANDUIDING_INHOUDING_VERMISSING_REISDOCUMENT_URI + ANT_ALL_MATCHER,
                                ControllerConstants.AANDUIDING_VERBLIJFSRECHT_URI + ANT_ALL_MATCHER,
                                ControllerConstants.AANGEVER_URI + ANT_ALL_MATCHER,
                                ControllerConstants.ADELLIJKE_TITEL_URI + ANT_ALL_MATCHER,
                                ControllerConstants.AUTORITEITTYPE_VAN_AFGIFTE_REISDOCUMENT_URI + ANT_ALL_MATCHER,
                                ControllerConstants.BIJHOUDINGSAARD_URI + ANT_ALL_MATCHER,
                                ControllerConstants.BURGERZAKEN_MODULE_URI + ANT_ALL_MATCHER,
                                ControllerConstants.CATEGORIE_ADMINISTRATIEVE_HANDELING_URI + ANT_ALL_MATCHER,
                                ControllerConstants.CATEGORIE_PERSONEN_URI + ANT_ALL_MATCHER,
                                ControllerConstants.ELEMENT_URI + ANT_ALL_MATCHER,
                                ControllerConstants.FUNCTIE_ADRES_URI + ANT_ALL_MATCHER,
                                ControllerConstants.GEMEENTE_URI + ANT_ALL_MATCHER,
                                ControllerConstants.GESLACHTSAANDUIDING_URI + ANT_ALL_MATCHER,
                                ControllerConstants.LAND_GEBIED_URI + ANT_ALL_MATCHER,
                                ControllerConstants.NAAMGEBRUIK_URI + ANT_ALL_MATCHER,
                                ControllerConstants.NADERE_BIJHOUDINGSAARD_URI + ANT_ALL_MATCHER,
                                ControllerConstants.NATIONALITEIT_URI + ANT_ALL_MATCHER,
                                ControllerConstants.PLAATS_URI + ANT_ALL_MATCHER,
                                ControllerConstants.PREDICAAT_URI + ANT_ALL_MATCHER,
                                ControllerConstants.RECHTSGROND_URI + ANT_ALL_MATCHER,
                                ControllerConstants.REDEN_EINDE_RELATIE_URI + ANT_ALL_MATCHER,
                                ControllerConstants.REDEN_OPSCHORTING_URI + ANT_ALL_MATCHER,
                                ControllerConstants.REDEN_VERKRIJGING_NL_NATIONALITEIT_URI + ANT_ALL_MATCHER,
                                ControllerConstants.REDEN_VERLIES_NL_NATIONALITEIT_URI + ANT_ALL_MATCHER,
                                ControllerConstants.REDEN_WIJZIGING_VERBLIJF_URI + ANT_ALL_MATCHER,
                                ControllerConstants.REGEL_URI + ANT_ALL_MATCHER,
                                ControllerConstants.ROL_URI + ANT_ALL_MATCHER,
                                ControllerConstants.SOORT_ACTIE_URI + ANT_ALL_MATCHER,
                                ControllerConstants.SOORT_ADMINISTRATIEVE_HANDELING_URI + ANT_ALL_MATCHER,
                                ControllerConstants.SOORT_BETROKKENHEID_URI + ANT_ALL_MATCHER,
                                ControllerConstants.SOORT_DOCUMENT_URI + ANT_ALL_MATCHER,
                                ControllerConstants.SOORT_ELEMENT_URI + ANT_ALL_MATCHER,
                                ControllerConstants.SOORT_ELEMENT_AUTORISATIE_URI + ANT_ALL_MATCHER,
                                ControllerConstants.SOORT_INDICATIE_URI + ANT_ALL_MATCHER,
                                ControllerConstants.SOORT_MIGRATIE_URI + ANT_ALL_MATCHER,
                                ControllerConstants.SOORT_NEDERLANDS_REISDOCUMENT_URI + ANT_ALL_MATCHER,
                                ControllerConstants.SOORT_PARTIJ_URI + ANT_ALL_MATCHER,
                                ControllerConstants.SOORT_PARTIJ_ONDERZOEK_URI + ANT_ALL_MATCHER,
                                ControllerConstants.SOORT_PERSOON_URI + ANT_ALL_MATCHER,
                                ControllerConstants.SOORT_PERSOON_ONDERZOEK_URI + ANT_ALL_MATCHER,
                                ControllerConstants.SOORT_RECHTSGROND_URI + ANT_ALL_MATCHER,
                                ControllerConstants.SOORT_RELATIE_URI + ANT_ALL_MATCHER,
                                ControllerConstants.STATUS_ONDERZOEK_URI + ANT_ALL_MATCHER,
                                ControllerConstants.STATUS_TERUGMELDING_URI + ANT_ALL_MATCHER,
                                ControllerConstants.VOORVOEGSEL_URI + ANT_ALL_MATCHER,
                                ControllerConstants.REDEN_BLOKKERING_URI + ANT_ALL_MATCHER,
                                ControllerConstants.LO3_BERICHTEN_BRON_URI + ANT_ALL_MATCHER,
                                ControllerConstants.LO3_CATEGORIE_MELDING_URI + ANT_ALL_MATCHER,
                                ControllerConstants.LO3_SEVERITY_URI + ANT_ALL_MATCHER,
                                ControllerConstants.LO3_SOORT_AANDUIDING_OUDER_URI + ANT_ALL_MATCHER,
                                ControllerConstants.LO3_SOORT_MELDING_URI + ANT_ALL_MATCHER)
            .hasAnyRole(SecurityConstants.ROL_FUNCTIONEEL_BEHEER, SecurityConstants.ROL_GEGEVENS_BEHEER)

            .and()
            .authorizeRequests()
                                .antMatchers(
                                    HttpMethod.POST,
                                    ControllerConstants.REGELSITUATIE_URI + ANT_ALL_MATCHER,
                                    ControllerConstants.CONVERSIE_AANDUIDING_INHOUDING_VERMISSING_REISDOCUMENT_URI + ANT_ALL_MATCHER,
                                    ControllerConstants.CONVERSIE_AANGIFTE_ADRESHOUDING_URI + ANT_ALL_MATCHER,
                                    ControllerConstants.CONVERSIE_ADELLIJKE_TITEL_PREDIKAAT_URI + ANT_ALL_MATCHER,
                                    ControllerConstants.CONVERSIE_REDEN_BEEINDIGEN_NATIONALITEIT_URI + ANT_ALL_MATCHER,
                                    ControllerConstants.CONVERSIE_REDEN_ONTBINDING_HUWELIJK_GEREGISTREERD_PARTNERSCHAP_URI + ANT_ALL_MATCHER,
                                    ControllerConstants.CONVERSIE_REDEN_OPNAME_NATIONALITEIT_URI + ANT_ALL_MATCHER,
                                    ControllerConstants.CONVERSIE_REDEN_OPSCHORTING_URI + ANT_ALL_MATCHER,
                                    ControllerConstants.CONVERSIE_RNI_DEELNEMER_URI + ANT_ALL_MATCHER,
                                    ControllerConstants.CONVERSIE_SOORT_NL_REISDOCUMENT_URI + ANT_ALL_MATCHER,
                                    ControllerConstants.CONVERSIE_VOORVOEGSEL_URI + ANT_ALL_MATCHER,
                                    ControllerConstants.CONVERSIE_LO3_RUBRIEK_URI + ANT_ALL_MATCHER,
                                    ControllerConstants.AUTORISATIETABEL_URI + ANT_ALL_MATCHER,
                                    ControllerConstants.AANDUIDING_INHOUDING_VERMISSING_REISDOCUMENT_URI + ANT_ALL_MATCHER,
                                    ControllerConstants.AANDUIDING_VERBLIJFSRECHT_URI + ANT_ALL_MATCHER,
                                    ControllerConstants.AANGEVER_URI + ANT_ALL_MATCHER,
                                    ControllerConstants.ADELLIJKE_TITEL_URI + ANT_ALL_MATCHER,
                                    ControllerConstants.AUTORITEITTYPE_VAN_AFGIFTE_REISDOCUMENT_URI + ANT_ALL_MATCHER,
                                    ControllerConstants.GEMEENTE_URI + ANT_ALL_MATCHER,
                                    ControllerConstants.LAND_GEBIED_URI + ANT_ALL_MATCHER,
                                    ControllerConstants.NATIONALITEIT_URI + ANT_ALL_MATCHER,
                                    ControllerConstants.PLAATS_URI + ANT_ALL_MATCHER,
                                    ControllerConstants.PREDICAAT_URI + ANT_ALL_MATCHER,
                                    ControllerConstants.RECHTSGROND_URI + ANT_ALL_MATCHER,
                                    ControllerConstants.REDEN_EINDE_RELATIE_URI + ANT_ALL_MATCHER,
                                    ControllerConstants.REDEN_VERKRIJGING_NL_NATIONALITEIT_URI + ANT_ALL_MATCHER,
                                    ControllerConstants.REDEN_VERLIES_NL_NATIONALITEIT_URI + ANT_ALL_MATCHER,
                                    ControllerConstants.REDEN_WIJZIGING_VERBLIJF_URI + ANT_ALL_MATCHER,
                                    ControllerConstants.SOORT_NEDERLANDS_REISDOCUMENT_URI + ANT_ALL_MATCHER,
                                    ControllerConstants.SOORT_DOCUMENT_URI + ANT_ALL_MATCHER,
                                    ControllerConstants.VOORVOEGSEL_URI + ANT_ALL_MATCHER)
            .hasRole(SecurityConstants.ROL_FUNCTIONEEL_BEHEER)

            .and()
            .httpBasic()
            .authenticationEntryPoint(new UnauthorisedEntryPoint())

            .and()
            .logout()
            .logoutSuccessHandler(new LogoutSuccessHandler() {

                @Override
                public void onLogoutSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication)
                    throws IOException, ServletException
                {
                    if (response.getHeader(ACCESS_CONTROL_ALLOW_ORIGIN) == null) {
                        response.addHeader(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
                    }
                }
            })

            // TODO
            .and()
            .csrf()
            .disable();
    }

    @Override
    @SuppressWarnings({"PMD.SignatureDeclareThrowsException", "checkstyle:designforextension"})
    public void configure(final WebSecurity web) throws Exception {
        // Spring Security should completely ignore URLs starting with /resources/
        web.ignoring().antMatchers("/ea/**", "/conf/**", "/css/**", "/fields/**", "/fonts/**", "/img/**", "/js/**", "/views/**", "/", "/index.html");
    }

    /**
     * Basis voor autorisatie is unauthorized.
     */
    public static final class UnauthorisedEntryPoint implements AuthenticationEntryPoint {

        @Override
        public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException exception)
                throws IOException, ServletException
        {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }
}
