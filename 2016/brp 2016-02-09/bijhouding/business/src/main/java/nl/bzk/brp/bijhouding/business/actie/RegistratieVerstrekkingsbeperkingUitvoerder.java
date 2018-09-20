/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.verstrekkingsbeperkingspecifiek.SpecifiekeVerstrekkingsbeperkingGroepVerwerker;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.verstrekkingsbeperkingvolledig.VolledigeVerstrekkingsbeperkingGroepVerwerker;
import nl.bzk.brp.bijhouding.business.util.All;
import nl.bzk.brp.model.bericht.kern.PersoonVerstrekkingsbeperkingBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonVerstrekkingsbeperkingHisVolledigImpl;

/**
 * Uitvoerder voor indicatie verstrekkingsberking en partiele verstrekkingsbeperkingen.
 */
public final class RegistratieVerstrekkingsbeperkingUitvoerder extends AbstractRegistratieIndicatieUitvoerder {

    @Override
    protected void verzamelVerwerkingsregels() {
        // Volledige verstrekkingsbeperking
        if (getBerichtRootObject().getIndicaties() != null) {
            final PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl indicatieHisVolledig;

            if (getPersoonHisVolledig().getIndicatieVolledigeVerstrekkingsbeperking() != null) {
                indicatieHisVolledig = getPersoonHisVolledig().getIndicatieVolledigeVerstrekkingsbeperking();
            } else {
                indicatieHisVolledig =
                        new PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl(getPersoonHisVolledig());
                getPersoonHisVolledig().setIndicatieVolledigeVerstrekkingsbeperking(indicatieHisVolledig);
            }

            this.voegVerwerkingsregelToe(new VolledigeVerstrekkingsbeperkingGroepVerwerker(getPersoonIndicatieBericht(),
                    indicatieHisVolledig, getActieModel()));
        }

        // Specifieke verstrekkingsbeperking
        if (getBerichtRootObject().getVerstrekkingsbeperkingen() != null) {
            for (final PersoonVerstrekkingsbeperkingBericht persoonVerstrekkingsbeperkingBericht : getBerichtRootObject().getVerstrekkingsbeperkingen()) {
                this.voegVerwerkingsregelToe(new SpecifiekeVerstrekkingsbeperkingGroepVerwerker(persoonVerstrekkingsbeperkingBericht,
                        bepaalPersoonVerstrekkingsbeperkingHisVolledigImpl(persoonVerstrekkingsbeperkingBericht),
                        getActieModel()));
            }
        }
    }

    /**
     * Bepaal de his volledig impl: indien specifieke verstr.bep. met zelfde id gegevens al bestaat,
     * gebruik die, anders een nieuwe.
     *
     * @param persoonVerstrekkingsbeperkingBericht het bericht
     * @return de juiste his volledig impl
     */
    private PersoonVerstrekkingsbeperkingHisVolledigImpl bepaalPersoonVerstrekkingsbeperkingHisVolledigImpl(
            final PersoonVerstrekkingsbeperkingBericht persoonVerstrekkingsbeperkingBericht)
    {
        final PersoonHisVolledigImpl persoonHisVolledig = getModelRootObject();

        PersoonVerstrekkingsbeperkingHisVolledigImpl persoonVerstrekkingsbeperkingHisVolledig = null;

        // Zoek of er al een persoon verstrekkingsbeperking bestaat met dezelfde id gegevens.
        for (final PersoonVerstrekkingsbeperkingHisVolledigImpl verstrbep : persoonHisVolledig.getVerstrekkingsbeperkingen()) {
            if (heeftGelijkeIdentiteit(persoonVerstrekkingsbeperkingBericht, verstrbep)) {
                persoonVerstrekkingsbeperkingHisVolledig = verstrbep;
            }
        }

        // Als er geen bestaande his volledig impl gevonden is, maak een nieuwe aan.
        if (persoonVerstrekkingsbeperkingHisVolledig == null) {
            persoonVerstrekkingsbeperkingHisVolledig = new PersoonVerstrekkingsbeperkingHisVolledigImpl(
                    persoonHisVolledig, persoonVerstrekkingsbeperkingBericht.getPartij(),
                    persoonVerstrekkingsbeperkingBericht.getOmschrijvingDerde(),
                    persoonVerstrekkingsbeperkingBericht.getGemeenteVerordening());

            persoonHisVolledig.getVerstrekkingsbeperkingen().add(persoonVerstrekkingsbeperkingHisVolledig);
        }

        return persoonVerstrekkingsbeperkingHisVolledig;
    }

    private boolean heeftGelijkeIdentiteit(final PersoonVerstrekkingsbeperkingBericht persoonVerstrekkingsbeperkingBericht,
                                           final PersoonVerstrekkingsbeperkingHisVolledigImpl verstrbep)
    {
        // Identiteit is gelijk als voor alle 3 identificerende attributen geldt:
        // beiden null of beide dezelfde (niet-null) waarde (code in geval van stamgegevens).
        final boolean gelijkePartij = All.isNull(verstrbep.getPartij(), persoonVerstrekkingsbeperkingBericht.getPartij())
                || All.isNotNull(verstrbep.getPartij(), persoonVerstrekkingsbeperkingBericht.getPartij())
                && verstrbep.getPartij().getWaarde().getCode().getWaarde().equals(
                persoonVerstrekkingsbeperkingBericht.getPartij().getWaarde().getCode().getWaarde());

        final boolean gelijkeOmschrijvingDerde = All.isNull(verstrbep.getOmschrijvingDerde(), persoonVerstrekkingsbeperkingBericht.getOmschrijvingDerde())
                || All.isNotNull(verstrbep.getOmschrijvingDerde(), persoonVerstrekkingsbeperkingBericht.getOmschrijvingDerde())
                && verstrbep.getOmschrijvingDerde().getWaarde().equals(
                persoonVerstrekkingsbeperkingBericht.getOmschrijvingDerde().getWaarde());

        final boolean gelijkeGemeenteVerordening = All.isNull(verstrbep.getGemeenteVerordening(), persoonVerstrekkingsbeperkingBericht.getGemeenteVerordening())
                || All.isNotNull(verstrbep.getGemeenteVerordening(), persoonVerstrekkingsbeperkingBericht.getGemeenteVerordening())
                && verstrbep.getGemeenteVerordening().getWaarde().getCode().getWaarde().equals(
                persoonVerstrekkingsbeperkingBericht.getGemeenteVerordening()
                        .getWaarde().getCode().getWaarde());

        return gelijkePartij && gelijkeOmschrijvingDerde && gelijkeGemeenteVerordening;
    }

}
