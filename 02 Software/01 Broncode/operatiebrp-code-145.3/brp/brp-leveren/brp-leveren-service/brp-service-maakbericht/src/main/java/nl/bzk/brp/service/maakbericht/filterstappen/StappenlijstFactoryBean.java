/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Beschrijft de stappen en de volgorde van stappen voor de module maakbericht.
 * <ol>
 * <li>Bepaal set kandidaat records (superset) welke in het bericht mogen komen.<br>
 * Van alle records op de PL wordt een subset van records bepaald welke in het bericht
 * opgenomen kunnen worden. Deze set aan records wordt kandidaatrecords genoemd. De overige
 * records komen definitief niet in het bericht.
 * <br>Zie: {@link KandidaatRecordBepaling}</li>
 * <li>Bouw autorisatieboom (volgorde stappen niet relevant)<br>
 * De berichtstructuur wordt vervolgens opgebouwd adhv de autorisatie. Als een gegeven
 * attribuut geautoriseerd is in de autorisatietabel dan wordt
 * eerst gekeken of het attribuut onderdeel is van een kandidaatrecord. Is dit niet
 * het geval dan kan dit attribuut niet geautoriseerd worden. Is
 * dat wel het geval dan krijgt de berichtstructuur een autorisatie voor het
 * {@code MetaAttribuut}, maar ook voor het bovenliggende {@code MetaRecord},
 * het bovenliggende {@code MetaObject} en indien aanwezig ook voor de
 * daarbovenliggende {@code MetaObject}en. Uiteindelijk vormt zich zodoende een boomstructuur met modelelementen
 * die de berichtstructuur benadert.
 * <br>Zie: {@link AutoriseerAttributenObvAttribuutAutorisatieServiceImpl},
 * {@link AutoriseerHistorieEnVerantwoordingAttribuutServiceImpl},
 * {@link AutoriseerObjectenWaarvoorOnderliggendeAutorisatieBestaatServiceImpl})
 * {@link AutoriseerAttributenObvAboPartijServiceImpl}
 * </li>
 * <li>Corrigeer autorisatieboom (volgorde niet relevant)
 * <br>Als de berichtstructuur gemaakt is moeten er een mogelijk nog een aantal
 * correcties plaatsvinden. Dit zijn dienstspecifieke correcties of correcties
 * die niet obv de autorisatie voorkomen had kunnen worden. In dat geval worden
 * objecten en onderliggende structuren verwijderd uit de boomstructuur.
 * <br>Zie: {@link VerwijderPreRelatieGegevensServiceImpl},
 * {@link VerwijderGerelateerdeOuderServiceImpl },
 * {@link BerichtRestrictieStap},
 * {@link VerwijderMigratieGroepServiceImpl},
 * {@link GeefDetailsPersoonCorrigeerVoorScoping}, en
 * {@link VerwijderAfnemerindicatieServiceImpl}
 * {@link GeefDetailsPersoonOpschonenObjecten}
 * <li>Correct leveren van onderzoeken
 * <br>
 * De stap draagt zorg voor het correct leveren van onderzoeksgegevens.
 * Onderzoeken worden geleverd voor alle diensten, voor volledig en
 * mutatieberichten gelden echter specifieke uitzonderingen.
 * <br> Zie {@link AutoriseerOnderzoekServiceImpl})
 * </li>
 * <li>Correct leveren van identificerende gegeven in mutatieberichten.
 * <br>
 * De berichtstructuur is voor volledigberichten nu zo goed als af, echter voor mutatieberichten
 * staat er nog teveel. Mutatieberichten tonen enkel de gegevens die in de mutatie geraakt zijn
 * (toegevoegd, aangepast of vervallen) EN identificerende gegevens van de hoofdpersoon indien deze
 * niet gemuteerd zijn EN alle identificerende gegevens van gerelateerde personen indien daar iets
 * van gemuteerd is.
 * {@link MutatieleveringBehoudIdentificerendeGegevensServiceImpl}
 * </li>
 * <li>
 * Correct leveren verantwoordingsinformatie
 * <br>Naast persoonsgegevens bevat het bericht ook verantwoordingsinformatie (administratieve handelingen, acties, bronnen etc.).
 * Enkel de verantwoordingsinformatie die relevant mag getoond worden.
 * <br>Zie {@link VerantwoordingAutorisatieServiceImpl}
 * </li>
 * <li>Correct leveren van Persoon objectsleutels.
 * <br>Technische ids van Persoon objecten moeten versleuteld opgenomen worden in bericht. De
 * {@code ObjectsleutelVersleutelaarServiceImpl} bereidt deze stap voor en genereert de sleutels.
 * {@link ObjectsleutelVersleutelaarServiceImpl}
 * </li>
 * <li>
 * Lege berichten niet leveren.
 * <br> Na alle bepalingen kan het zijn dat het mutatiebericht geen relevant informatie meer bevat.
 * In dat geval wordt het beschouwd als "leeg" en mag het niet meer geleverd worden.
 * <br>Zie: {@link LeegBerichtBepalerServiceImpl}
 * </li>
 * </ol>
 */
@Configuration
class StappenlijstFactoryBean implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * @return de stappenlijst
     */
    @Bean
    Stappenlijst maakStappen() {
        return new Stappenlijst(
                //STAP1,
                //bepaal set kandidaat records (superset) welke in het bericht mogen komen.
                applicationContext.getBean(KandidaatRecordBepaling.class),

                //STAP2
                //bouw autorisatieboom (volgorde niet relevant)
                applicationContext.getBean(AutoriseerAttributenObvAttribuutAutorisatieServiceImpl.class),
                applicationContext.getBean(AutoriseerHistorieEnVerantwoordingAttribuutServiceImpl.class),
                applicationContext.getBean(AutoriseerObjectenWaarvoorOnderliggendeAutorisatieBestaatServiceImpl.class),
                applicationContext.getBean(AutoriseerAttributenObvAboPartijServiceImpl.class),

                //STAP3
                //corrigeer autorisatieboom (volgorde niet relevant)
                applicationContext.getBean(OpschonenTavIndMutLevServiceImpl.class),
                applicationContext.getBean(VerwijderPreRelatieGegevensServiceImpl.class),
                applicationContext.getBean(VerwijderGerelateerdeOuderServiceImpl.class),
                applicationContext.getBean(BerichtRestrictieStap.class),
                applicationContext.getBean(VerwijderMigratieGroepServiceImpl.class),
                applicationContext.getBean(GeefDetailsPersoonCorrigeerVoorScoping.class),
                applicationContext.getBean(VerwijderAfnemerindicatieServiceImpl.class),
                applicationContext.getBean(GeefDetailsPersoonOpschonenObjecten.class),

                //STAP4
                //markeer onderzoekgegevens
                applicationContext.getBean(AutoriseerOnderzoekServiceImpl.class),

                //STAP5
                //behoud actuele indentificerende gegevens, indien niet in delta
                //maar wel geautoriseerd.
                applicationContext.getBean(MutatieleveringBehoudIdentificerendeGegevensServiceImpl.class),

                //STAP6
                //zorg dat verantwoording correct geleverd wordt
                applicationContext.getBean(VerantwoordingAutorisatieServiceImpl.class),
                applicationContext.getBean(AutoriseerGedeblokkeerdeMeldingenServiceImpl.class),

                //STAP7
                //bepaalt objectsleutels voor personen
                applicationContext.getBean(ObjectsleutelVersleutelaarServiceImpl.class),

                //STAP8
                //bepaalt of het mutatiebericht als "leeg" beschouwd kan worden.
                //in dat geval wordt het niet geleverd.
                applicationContext.getBean(LeegBerichtBepalerServiceImpl.class)
        );
    }
}
