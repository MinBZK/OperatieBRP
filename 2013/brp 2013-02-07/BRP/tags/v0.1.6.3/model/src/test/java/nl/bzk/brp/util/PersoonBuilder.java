/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import java.util.ArrayList;

import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.GeslachtsnaamComponent;
import nl.bzk.brp.model.attribuuttype.Volgnummer;
import nl.bzk.brp.model.attribuuttype.Voornaam;
import nl.bzk.brp.model.attribuuttype.Voorvoegsel;
import nl.bzk.brp.model.groep.bericht.PersoonBijhoudingsGemeenteGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonBijhoudingsVerantwoordelijkheidGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonGeslachtsAanduidingGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonGeslachtsnaamCompStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatieNummersGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonVoornaamStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonGeslachtsnaamComponentBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonVoornaamBericht;
import nl.bzk.brp.model.objecttype.operationeel.statisch.GeslachtsAanduiding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortPersoon;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Verantwoordelijke;


/** Utility class voor het construeren van Persoon instanties. */
public final class PersoonBuilder {

    /** Empty en private constructor daar utility classes niet geinstantieerd dienen te worden. */
    private PersoonBuilder() {
    }

    public static PersoonBericht bouwRefererendPersoon(final String bsn) {
        final PersoonBericht persoon = new PersoonBericht();

        // Groep identificatie nummers
        persoon.setIdentificatieNummers(new PersoonIdentificatieNummersGroepBericht());
        persoon.getIdentificatieNummers().setBurgerServiceNummer(new Burgerservicenummer(bsn));

        return persoon;
    }

    /**
     * Bouwt en retourneert een nieuwe Persoon instantie met de opgegeven eigenschappen en enkele standaard
     * waardes voor enkele velden.
     *
     * @param bsn het burgerservicenummer
     * @param datumGeboorte de geboorte datum
     * @param gemeenteGeboorte de geboorte gemeente
     * @param voornaam de voornaam
     * @param voorvoegsel het voorvoegsel
     * @param geslachtsnaam de geslachtsnaam
     * @return een nieuw persoon
     */
    public static PersoonBericht bouwPersoon(final String bsn, final GeslachtsAanduiding geslachtsAanduiding,
        final Integer datumGeboorte, final Partij gemeenteGeboorte, final String voornaam, final String voorvoegsel,
        final String geslachtsnaam)
    {
        final PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(SoortPersoon.INGESCHREVENE);

        // Groep identificatie nummers
        persoon.setIdentificatieNummers(new PersoonIdentificatieNummersGroepBericht());
        persoon.getIdentificatieNummers().setBurgerServiceNummer(new Burgerservicenummer(bsn));

        // Groep geslachts aanduiding
        persoon.setGeslachtsAanduiding(new PersoonGeslachtsAanduidingGroepBericht());
        persoon.getGeslachtsAanduiding().setGeslachtsAanduiding(geslachtsAanduiding);

        // Groep geboorte
        persoon.setGeboorte(new PersoonGeboorteGroepBericht());
        persoon.getGeboorte().setDatumGeboorte(new Datum(datumGeboorte));
        persoon.getGeboorte().setLandGeboorte(StatischeObjecttypeBuilder.LAND_NEDERLAND);
        persoon.getGeboorte().setGemeenteGeboorte(gemeenteGeboorte);

        // Objecttype persoonvoornaam
        persoon.setPersoonVoornaam(new ArrayList<PersoonVoornaamBericht>());
        persoon.getPersoonVoornaam().add(bouwPersoonVoornaam(voornaam));

        // Objecttype geslachtsnaam component
        persoon.setGeslachtsnaamcomponenten(new ArrayList<PersoonGeslachtsnaamComponentBericht>());
        persoon.getGeslachtsnaamcomponenten().add(bouwGeslachtsnaamComponent(voorvoegsel, geslachtsnaam));

        // Bijhoudings gemeente groep
        persoon.setBijhoudenGemeente(new PersoonBijhoudingsGemeenteGroepBericht());
        persoon.getBijhoudenGemeente().setBijhoudingsGemeente(gemeenteGeboorte);
        persoon.getBijhoudenGemeente().setDatumInschrijvingInGemeente(new Datum(datumGeboorte));

        // Bijhoudings verantwoordelijk groep
        persoon.setBijhoudingVerantwoordelijke(new PersoonBijhoudingsVerantwoordelijkheidGroepBericht());
        persoon.getBijhoudingVerantwoordelijke().setVerantwoordelijke(Verantwoordelijke.COLLEGE);

        return persoon;
    }

    /**
     * Bouwt en retourneert een nieuwe {@link PersoonVoornaamBericht} met opgegeven voornaam.
     *
     * @param voornaam de voornaam
     * @return een nieuwe voornaam
     */
    public static PersoonVoornaamBericht bouwPersoonVoornaam(final String voornaam) {
        final PersoonVoornaamBericht persoonVoornaam = new PersoonVoornaamBericht();
        persoonVoornaam.setGegevens(new PersoonVoornaamStandaardGroepBericht());
        persoonVoornaam.getGegevens().setVoornaam(new Voornaam(voornaam));
        return persoonVoornaam;
    }

    /**
     * Bouwt en retourneert een nieuwe {@link PersoonGeslachtsnaamComponentBericht} met opgegeven voorvoegsel en
     * geslachtsnaam.
     *
     * @param voorvoegsel het voorvoegsel
     * @param geslachtsnaam de geslachtsnaam
     * @return een nieuw geslachtsnaamcomponent
     */
    public static PersoonGeslachtsnaamComponentBericht bouwGeslachtsnaamComponent(final String voorvoegsel,
        final String geslachtsnaam)
    {
        final PersoonGeslachtsnaamComponentBericht persoonGeslachtsnaamComponent =
            new PersoonGeslachtsnaamComponentBericht();
        persoonGeslachtsnaamComponent.setVolgnummer(new Volgnummer(1));
        persoonGeslachtsnaamComponent.setPersoonGeslachtsnaamCompStandaardGroep(
            new PersoonGeslachtsnaamCompStandaardGroepBericht());
        if (voorvoegsel != null) {
            persoonGeslachtsnaamComponent.getGegevens().setVoorvoegsel(new Voorvoegsel(voorvoegsel));
        }
        persoonGeslachtsnaamComponent.getGegevens().setGeslachtsnaamComponent(
            new GeslachtsnaamComponent(geslachtsnaam));
        return persoonGeslachtsnaamComponent;
    }

}
