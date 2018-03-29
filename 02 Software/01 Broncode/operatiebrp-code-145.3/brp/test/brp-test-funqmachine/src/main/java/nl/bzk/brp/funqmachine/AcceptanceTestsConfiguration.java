/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine;

import java.util.HashMap;
import java.util.Map;

import nl.bzk.brp.funqmachine.spring.ScenarioScope;
import nl.bzk.brp.funqmachine.spring.StoryScope;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Spring configuratie klasse.
 */
@Configuration
@ComponentScan
@ImportResource("classpath:config/funqmachine-beans.xml")
// Deze class niet final en geen private constructor maken ondanks checkstyle melding.
public class AcceptanceTestsConfiguration {

    private static ScenarioScope scenarioScope = new ScenarioScope();
    private static StoryScope storyScope = new StoryScope();

    /**
     * @return geeft de bean voor de {@link ScenarioScope} terug.
     */
    @Bean
    public static ScenarioScope scenarioScope() {
        return scenarioScope;
    }

    /**
     * @return geeft de bean voor de {@link StoryScope} terug.
     */
    @Bean
    public static StoryScope storyScope() {
        return storyScope;
    }

    /**
     * @return geeft de bean voor de {@link CustomScopeConfigurer} terug.
     */
    @Bean
    public static CustomScopeConfigurer registerCustomScopes() {
        final CustomScopeConfigurer configurer = new CustomScopeConfigurer();

        final Map<String, Object> jbehaveScopes = new HashMap<>();

        jbehaveScopes.put("scenario", scenarioScope());
        jbehaveScopes.put("story", storyScope());

        configurer.setScopes(jbehaveScopes);

        return configurer;
    }
}
