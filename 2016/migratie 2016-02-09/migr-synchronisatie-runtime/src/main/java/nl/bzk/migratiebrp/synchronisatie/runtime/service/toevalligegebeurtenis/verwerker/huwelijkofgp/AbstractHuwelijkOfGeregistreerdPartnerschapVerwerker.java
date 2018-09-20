/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.huwelijkofgp;

import java.math.BigInteger;

import nl.bzk.migratiebrp.bericht.model.brp.generated.BuitenlandsePlaats;
import nl.bzk.migratiebrp.bericht.model.brp.generated.DatumMetOnzekerheid;
import nl.bzk.migratiebrp.bericht.model.brp.generated.GemeenteCode;
import nl.bzk.migratiebrp.bericht.model.brp.generated.GroepRelatieRelatie;
import nl.bzk.migratiebrp.bericht.model.brp.generated.LandGebiedCode;
import nl.bzk.migratiebrp.bericht.model.brp.generated.MigratievoorzieningRegistreerHuwelijkGeregistreerdPartnerschapBijhouding;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeBerichtBijhouding;
import nl.bzk.migratiebrp.bericht.model.brp.generated.RedenEindeRelatieCode;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Relatie;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.AbstractToevalligeGebeurtenisVerwerker;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

import org.apache.commons.lang.StringUtils;

/**
 * Overkoepelende klasse voor het verwerken van toevallige gebeurtenissen m.b.t. huwelijk/geregistreerd partnerschap.
 */
public abstract class AbstractHuwelijkOfGeregistreerdPartnerschapVerwerker extends AbstractToevalligeGebeurtenisVerwerker {

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Hook-methode voor specifieke implementatie van de generatie van de bericht inhoud voor huwelijk/geregistreerd
     * partnerschap bijhoudingen.
     *
     * @param opdracht
     *            De tot nog toe opgestelde opdracht waarin de inhoud komt.
     * @param verzoek
     *            Het verzoek waarop de inhoud van het bericht wordt gebaseerd.
     * @param rootPersoon
     *            De persoon van waaruit de bijhouding plaatsvindt.
     */
    protected abstract void maakBrpOpdrachtInhoud(
        final MigratievoorzieningRegistreerHuwelijkGeregistreerdPartnerschapBijhouding opdracht,
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek,
        final Persoon rootPersoon);

    @Override
    protected final ObjecttypeBerichtBijhouding maakBrpToevalligeGebeurtenisOpdracht(
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek,
        final Persoon rootPersoon)
    {
        final MigratievoorzieningRegistreerHuwelijkGeregistreerdPartnerschapBijhouding opdracht =
                new MigratievoorzieningRegistreerHuwelijkGeregistreerdPartnerschapBijhouding();

        // Zet de bericht stuurgegevens op de opdracht.
        LOG.debug("Zetten stuurgegevens opdracht.");
        opdracht.setStuurgegevens(OBJECT_FACTORY.createObjecttypeBerichtStuurgegevens(maakBerichtStuurgegevens(verzoek)));

        // Zet de bericht parameters op de opdracht.
        LOG.debug("Zetten parameters opdracht.");
        opdracht.setParameters(OBJECT_FACTORY.createObjecttypeBerichtParameters(maakBerichtParameters(rootPersoon)));

        // Zet de inhoud van de opdracht.
        LOG.debug("Zetten inhoud opdracht.");
        maakBrpOpdrachtInhoud(opdracht, verzoek, rootPersoon);

        return opdracht;
    }

    /**
     * Maak de relatiegroep van een huwelijk of geregistreerd partnerschap aan voor omzetting of ontbinding.
     *
     * @param isSluiting
     *            De relatiegroep heeft betrekking op een sluiting.
     * @param datumVerzoek
     *            De datum aanvang/einde vanuit het verzoek.
     * @param plaatsVerzoek
     *            De plaats aanvang/einde vanuit het verzoek.
     * @param landVerzoek
     *            Het land aanvang/einde vanuit het verzoek.
     * @param redenEinde
     *            De reden einde vanuit het verzoek.
     * @param huidigeRelatie
     *            De relatie zoals deze in BRP staat.
     * @return De relatiegroep.
     */
    protected final GroepRelatieRelatie maakHuwelijkOfGeregistreerdPartnerschapRelatie(
        final boolean isSluiting,
        final BigInteger datumVerzoek,
        final String plaatsVerzoek,
        final String landVerzoek,
        final String redenEinde,
        final Relatie huidigeRelatie)
    {
        final GroepRelatieRelatie relatie = new GroepRelatieRelatie();
        relatie.setCommunicatieID(COMMUNICATIE_TYPE_IDENTIFICATIE + getIdentificatieIdVolgnummerEnVerhoogMetEen());

        if (huidigeRelatie != null) {
            relatie.setObjectSleutel(String.valueOf(huidigeRelatie.getId()));
        }

        relatie.setObjecttype(OBJECT_TYPE_RELATIE);

        final BrpDatum brpDatum = getConverteerder().converteerDatum(new Lo3Datum(datumVerzoek.intValue()));
        final DatumMetOnzekerheid datum = new DatumMetOnzekerheid();
        datum.setValue(String.valueOf(brpDatum.getWaarde()));
        if (isSluiting) {
            relatie.setDatumAanvang(OBJECT_FACTORY.createGroepRelatieRelatieDatumAanvang(datum));
        } else {
            relatie.setDatumEinde(OBJECT_FACTORY.createGroepRelatieRelatieDatumEinde(datum));
        }

        if (plaatsVerzoek.length() == LENGTE_GEMEENTE_CODE && StringUtils.isNumeric(plaatsVerzoek)) {
            // Binnenlands plaats.

            final GemeenteCode gemeenteCode = new GemeenteCode();
            gemeenteCode.setValue(getConverteerder().converteerLo3GemeenteCode(new Lo3GemeenteCode(plaatsVerzoek)).getFormattedStringCode());
            if (isSluiting) {
                relatie.setGemeenteAanvangCode(OBJECT_FACTORY.createGroepRelatieRelatieGemeenteAanvangCode(gemeenteCode));
            } else {
                relatie.setGemeenteEindeCode(OBJECT_FACTORY.createGroepRelatieRelatieGemeenteEindeCode(gemeenteCode));
            }
        } else {
            // Buitenlandse plaats.

            final BuitenlandsePlaats buitenlandsePlaats = new BuitenlandsePlaats();
            buitenlandsePlaats.setValue(plaatsVerzoek);
            if (isSluiting) {
                relatie.setBuitenlandsePlaatsAanvang(OBJECT_FACTORY.createGroepRelatieRelatieBuitenlandsePlaatsAanvang(buitenlandsePlaats));
            } else {
                relatie.setBuitenlandsePlaatsEinde(OBJECT_FACTORY.createGroepRelatieRelatieBuitenlandsePlaatsEinde(buitenlandsePlaats));
            }
        }

        final LandGebiedCode landGebiedCode = new LandGebiedCode();
        landGebiedCode.setValue(String.valueOf(getConverteerder().converteerLo3LandCode(new Lo3LandCode(landVerzoek)).getWaarde()));

        if (isSluiting) {
            relatie.setLandGebiedAanvangCode(OBJECT_FACTORY.createGroepRelatieRelatieLandGebiedAanvangCode(landGebiedCode));

        } else {
            relatie.setLandGebiedEindeCode(OBJECT_FACTORY.createGroepRelatieRelatieLandGebiedEindeCode(landGebiedCode));
        }

        if (redenEinde != null) {
            final RedenEindeRelatieCode redenEindeCode = new RedenEindeRelatieCode();
            final BrpRedenEindeRelatieCode brpEindeRelatieCode =
                    getConverteerder().converteerLo3RedenOntbindingHuwelijkOfGpCode(new Lo3RedenOntbindingHuwelijkOfGpCode(redenEinde));
            redenEindeCode.setValue(String.valueOf(brpEindeRelatieCode.getWaarde()));
            relatie.setRedenEindeCode(OBJECT_FACTORY.createObjecttypeRedenEindeRelatieCode(redenEindeCode));
        }

        return relatie;
    }

}
