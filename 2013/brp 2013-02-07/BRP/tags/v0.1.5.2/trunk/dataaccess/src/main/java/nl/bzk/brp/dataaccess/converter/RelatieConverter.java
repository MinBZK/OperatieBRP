/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.converter;

import java.util.HashSet;
import java.util.Set;

import nl.bzk.brp.model.gedeeld.SoortBetrokkenheid;
import nl.bzk.brp.model.gedeeld.SoortRelatie;
import nl.bzk.brp.model.logisch.Betrokkenheid;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.Relatie;
import nl.bzk.brp.model.operationeel.StatusHistorie;
import nl.bzk.brp.model.operationeel.kern.PersistentBetrokkenheid;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoon;
import nl.bzk.brp.model.operationeel.kern.PersistentRelatie;

/**
 * Helper Utility class voor het converteren tussen {@link nl.bzk.brp.model.logisch.Relatie} (logisch model) en
 * {@link nl.bzk.brp.model.operationeel.kern.PersistentRelatie} (operationeel model). Hierbij worden ook de
 * betrokkenheden geconverteerd tussen de twee modellen.
 */
public final class RelatieConverter {

    /** default constructor. */
    private RelatieConverter() {
    }

    /**
     * Bouwt een {@link nl.bzk.brp.model.operationeel.kern.PersistentRelatie} instantie (operationeel model) op basis
     * van de opgegeven logische {@link nl.bzk.brp.model.logisch.Relatie}.
     *
     * @param relatie de logische relatie instantie waarvoor een operationele instantie moet worden opgebouwd.
     * @param datumAanvang de datum van aanvang van de relatie.
     * @return de gebouwde relatie.
     */
    public static PersistentRelatie bouwPersistentRelatie(final Relatie relatie, final Integer datumAanvang) {
        PersistentRelatie persistentRelatie = new PersistentRelatie();
        persistentRelatie.setSoortRelatie(relatie.getSoortRelatie());
        persistentRelatie.setStatusHistorie(StatusHistorie.A);

        // Aanvang en einde alleen op relatie zetten als relatie een partner relatie is
        if (SoortRelatie.GEREGISTREERD_PARTNERSCHAP == relatie.getSoortRelatie() || SoortRelatie.HUWELIJK == relatie
            .getSoortRelatie())
        {
            // Indien geen datum in relatie, dan algemene datum aanvang uit bericht gebruiken
            if (relatie.getDatumAanvang() == null) {
                persistentRelatie.setDatumAanvang(datumAanvang);
            } else {
                persistentRelatie.setDatumAanvang(relatie.getDatumAanvang());
            }
            persistentRelatie.setDatumEinde(relatie.getDatumEinde());

            persistentRelatie.setGemeenteAanvang(relatie.getGemeenteAanvang());
            persistentRelatie.setGemeenteEinde(relatie.getGemeenteEinde());
            persistentRelatie.setPlaatsAanvang(relatie.getPlaatsAanvang());
            persistentRelatie.setPlaatsEinde(relatie.getPlaatsEinde());
            persistentRelatie.setLandAanvang(relatie.getLandAanvang());
            persistentRelatie.setLandEinde(relatie.getLandEinde());
            persistentRelatie.setOmschrijvingLocatieAanvang(relatie.getOmschrijvingLocatieAanvang());
            persistentRelatie.setOmschrijvingLocatieEinde(relatie.getOmschrijvingLocatieEinde());
            persistentRelatie.setRedenBeeindigingRelatie(relatie.getRedenBeeindigingRelatie());

            persistentRelatie.setBuitenlandsePlaatsAanvang(relatie.getBuitenlandsePlaatsAanvang());
            persistentRelatie.setBuitenlandseRegioAanvang(relatie.getBuitenlandseRegioAanvang());
            persistentRelatie.setBuitenlandsePlaatsEinde(relatie.getBuitenlandsePlaatsEinde());
            persistentRelatie.setBuitenlandseRegioEinde(relatie.getBuitenlandseRegioEinde());
        }

        // Betrokkenheden toevoegen
        if (relatie.getBetrokkenheden() != null) {
            Set<PersistentBetrokkenheid> betrokkenheden = new HashSet<PersistentBetrokkenheid>();
            for (Betrokkenheid betrokkenheid : relatie.getBetrokkenheden()) {
                betrokkenheden.add(bouwPersistentBetrokkenheid(persistentRelatie, betrokkenheid, datumAanvang));
            }
        }
        return persistentRelatie;
    }

    /**
     * Bouwt een {@link PersistentBetrokkenheid} instantie (operationeel model) op basis van de opgegeven logische
     * {@link Relatie} en {@link Betrokkenheid}.
     *
     * @param relatie de relatie (operationeel model) waartoe de betrokkenheid hoort.
     * @param betrokkenheid de logische betrokkenheid instantie waarvoor de operationele instantie moet worden
     * opgebouwd.
     * @param datumAanvang de datum van aanvang van de betrokkenheid.
     * @return de gebouwde betrokkenheid.
     */
    private static PersistentBetrokkenheid bouwPersistentBetrokkenheid(final PersistentRelatie relatie,
        final Betrokkenheid betrokkenheid, final Integer datumAanvang)
    {
        PersistentBetrokkenheid persistentBetrokkenheid = new PersistentBetrokkenheid();
        persistentBetrokkenheid.setRelatie(relatie);
        persistentBetrokkenheid.setSoortBetrokkenheid(betrokkenheid.getSoortBetrokkenheid());

        if (SoortBetrokkenheid.OUDER == betrokkenheid.getSoortBetrokkenheid()) {
            // Indien geen datum in betrokkenheid, dan algemene datum aanvang uit bericht gebruiken
            if (betrokkenheid.getDatumAanvangOuderschap() == null) {
                persistentBetrokkenheid.setDatumAanvangOuderschap(datumAanvang);
            } else {
                persistentBetrokkenheid.setDatumAanvangOuderschap(betrokkenheid.getDatumAanvangOuderschap());
            }
            persistentBetrokkenheid.setIndOuder(betrokkenheid.isIndOuder());
            persistentBetrokkenheid.setOuderStatusHistorie(StatusHistorie.A);
            persistentBetrokkenheid.setIndOuderHeeftGezag(betrokkenheid.isIndOuderHeeftGezag());
            persistentBetrokkenheid.setOuderlijkGezagStatusHistorie(StatusHistorie.A);
        } else {
            persistentBetrokkenheid.setOuderStatusHistorie(StatusHistorie.X);
            persistentBetrokkenheid.setOuderlijkGezagStatusHistorie(StatusHistorie.X);
        }

        persistentBetrokkenheid.setBetrokkene(bouwIdentificerendPersistentPersoon(betrokkenheid.getBetrokkene()));

        if (relatie.getBetrokkenheden() == null) {
            relatie.setBetrokkenheden(new HashSet<PersistentBetrokkenheid>());
        }
        relatie.getBetrokkenheden().add(persistentBetrokkenheid);
        return persistentBetrokkenheid;
    }

    /**
     * Bouwt en retourneert een 'identificerend' {@link nl.bzk.brp.model.operationeel.kern.PersistentPersoon} instantie
     * (operationeel model) op basis van de opgegeven logische {@link nl.bzk.brp.model.logisch.Persoon}. Het gaat hier
     * puur om een persoon met de technische sleutel voor referentie vanuit de overige operationeel model instanties.
     *
     * @param persoon de logisch model Persoon waarvoor de operationeel model instantie moet worden opgebouwd.
     * @return een persistent persoon (operationeel) instantie met alleen de primaire sleutel.
     */
    private static PersistentPersoon bouwIdentificerendPersistentPersoon(final Persoon persoon) {
        PersistentPersoon persistentPersoon = new PersistentPersoon();
        persistentPersoon.setId(persoon.getId());
        return persistentPersoon;
    }

}
