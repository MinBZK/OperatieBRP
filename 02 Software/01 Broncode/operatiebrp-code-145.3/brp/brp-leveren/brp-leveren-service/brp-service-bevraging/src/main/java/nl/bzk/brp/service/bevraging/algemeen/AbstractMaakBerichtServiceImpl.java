/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.algemeen;

import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.algemeen.autorisatie.AutorisatieException;
import nl.bzk.brp.service.algemeen.autorisatie.AutorisatieParams;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieValidatieService;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;

/**
 * Abstracte basis voor het maken van antwoordberichten binnen bevraging.
 * @param <V> type bevraging verzoek
 * @param <R> type bevraging resultaat
 */

@Bedrijfsregel(Regel.R2120)
@Bedrijfsregel(Regel.R2245)
public abstract class AbstractMaakBerichtServiceImpl<V extends BevragingVerzoek, R extends BevragingResultaat>
        implements Bevraging.MaakBerichtService<V, R> {

    private LeveringsautorisatieValidatieService leveringsautorisatieService;
    private PartijService partijService;

    /**
     * @param leveringsautorisatieService de leveringsautorisatie validatieservice
     * @param partijService de partij service
     */
    public AbstractMaakBerichtServiceImpl(final LeveringsautorisatieValidatieService leveringsautorisatieService, final PartijService partijService) {
        this.leveringsautorisatieService = leveringsautorisatieService;
        this.partijService = partijService;
    }

    /**
     * Voert stappen uit voor het maken van een GeefDetailsPersoon leverbericht.
     * @param bevragingVerzoek bevragingsverzoek
     * @return het geef details persoon resultaat
     */
    @Override
    public final R voerStappenUit(final V bevragingVerzoek) {
        final R resultaat = maakResultaatObject();
        try {
            // generieke autorisatie voor bevraging.
            // zet eerst de brp partij, anders leeg indien fout.
            resultaat.setBrpPartij(partijService.geefBrpPartij());
            resultaat.setAutorisatiebundel(controleerAutorisatie(bevragingVerzoek));

            voerDienstSpecifiekeStappenUit(bevragingVerzoek, resultaat);
        } catch (final AutorisatieException e) {
            final String str = String.format("Autorisatiefout opgetreden (%s) - %s", getDienstSpecifiekeLoggingString(), e.getMessage());
            getLogger().debug(str, e);
            getLogger().info(str);
            resultaat.getMeldingen().add(new Melding(Regel.R2343));
        } catch (final StapMeldingException e) {
            final String str = String.format("Functionele fout bij bevraging (%s) - %s", getDienstSpecifiekeLoggingString(), e.getMessage());
            getLogger().debug(str, e);
            getLogger().info(str);
            resultaat.getMeldingen().addAll(e.getMeldingen());
        } catch (final StapException e) {
            getLogger().error("Fout opgetreden bij het verwerken van de stappen {}", e.getMessage(), e);
            resultaat.getMeldingen().add(e.maakFoutMelding());
        }
        return resultaat;
    }

    /**
     * @return resultaat
     */
    protected abstract R maakResultaatObject();

    /**
     * Voer de specifieke stappen uit voor de geimplementeerde dienst.
     * @param verzoek het verzoek
     * @param resultaat het resultaat
     * @throws StapException stappen fout
     */
    protected abstract void voerDienstSpecifiekeStappenUit(final V verzoek, final R resultaat) throws StapException;

    /**
     * @return logger
     */
    protected abstract Logger getLogger();

    /**
     * get logging string.
     * @return logging string
     */
    protected abstract String getDienstSpecifiekeLoggingString();

    /**
     * @param bevragingVerzoek bevragingVerzoek
     * @return autorisatiebundel
     * @throws AutorisatieException autorisatie fout
     */
    private Autorisatiebundel controleerAutorisatie(final V bevragingVerzoek) throws AutorisatieException {
        final AutorisatieParams autorisatieParams = AutorisatieParams.maakBuilder()
                .metZendendePartijCode(bevragingVerzoek.getStuurgegevens().getZendendePartijCode())
                .metLeveringautorisatieId(Integer.parseInt(bevragingVerzoek.getParameters().getLeveringsAutorisatieId()))
                .metOIN(bevragingVerzoek.getOin())
                .metSoortDienst(bevragingVerzoek.getSoortDienst())
                .metDienstId(Integer.valueOf(bevragingVerzoek.getParameters().getDienstIdentificatie()))
                .metRol(bevragingVerzoek.getParameters().getRolNaam())
                .metVerzoekViaKoppelvlak(bevragingVerzoek.isBrpKoppelvlakVerzoek())
                .build();
        return leveringsautorisatieService.controleerAutorisatie(autorisatieParams);
    }
}
