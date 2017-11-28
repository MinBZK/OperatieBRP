/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Protocolleringsniveau;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.ObjectElement;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieService;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import org.springframework.stereotype.Component;

/**
 * Afnemerindicaties worden nooit getoond in berichten, behalve voor de dienst GeefDetailsPersoon.
 * Indien afnemerindicate verwijderd wordt, dan wordt de gehele onderliggende autorisatiestructuur
 * verwijderd. Voor geef details persoon, worden enkel de afnemerindicaties behouden die
 * geldig zijn op het gegeven peilmoment.
 * <p>
 * Onderstaande snippet beschrijft een PL met een afnemerindicatie.
 * <pre><code>
 * &nbsp;&nbsp;[o] Persoon.Afnemerindicatie id=27
 * &nbsp;&nbsp;&nbsp;[g] Persoon.Afnemerindicatie.Identiteit
 * &nbsp;&nbsp;&nbsp;&nbsp;[r] id=28
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Afnemerindicatie.AfnemerCode, '199901'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Afnemerindicatie.LeveringsautorisatieIdentificatie, '11'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Afnemerindicatie.Persoon, '90149'
 * &nbsp;&nbsp;&nbsp;[g] Persoon.Afnemerindicatie.Standaard
 * &nbsp;&nbsp;&nbsp;&nbsp;[r] id=28
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Afnemerindicatie.DienstInhoud, '51'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Afnemerindicatie.TijdstipRegistratie, 'Sun Aug 07 20:50:28 CEST 2015'
 * </code></pre>
 *
 * <b>Voorbeeld 1</b> Als de dienst ongelijk is aan GeefDetailsPersoon, zal in autorisatieboom de autorisatie verwijderd worden
 * voor object <tt>[o] Persoon.Afnemerindicatie id=27</tt>. De gehele structuur valt daarmee weg.
 * <p>
 * <b>Voorbeeld 2</b> Voor GeefDetailsPersoon zijn er twee nuances, of de records in <tt>Persoon.Afnemerindicatie.Standaard</tt>
 * niet meer geldig op het gegeven peilmoment, OF autorisatie ontbreekt. In dat geval wordt de afnemerindicatie verwijderd.
 *
 * Als de groep <tt>Persoon.Afnemerindicatie.Standaard</tt>
 * <pre><code>
 * &nbsp;&nbsp;[o] Persoon.Afnemerindicatie id=27
 * &nbsp;&nbsp;&nbsp;[g] Persoon.Afnemerindicatie.Identiteit
 * &nbsp;&nbsp;&nbsp;&nbsp;[r] id=28
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Afnemerindicatie.AfnemerCode, '199901'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Afnemerindicatie.LeveringsautorisatieIdentificatie, '11'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Afnemerindicatie.Persoon, '90149'
 * </code></pre>
 */
@Component
@Bedrijfsregel(Regel.R2195)
final class VerwijderAfnemerindicatieServiceImpl implements MaakBerichtStap {

    private static final ObjectElement PERSOON_AFNEMERINDICATIE = ElementHelper.getObjectElement(Element.PERSOON_AFNEMERINDICATIE);

    @Inject
    private LeveringsautorisatieService leveringsautorisatieService;

    private VerwijderAfnemerindicatieServiceImpl() {
    }

    @Override
    public void execute(final Berichtgegevens berichtgegevens) {
        final SoortDienst soortDienst = berichtgegevens.getAutorisatiebundel().getSoortDienst();
        if (SoortDienst.GEEF_DETAILS_PERSOON == soortDienst) {
            filterAfnemerindicaties(berichtgegevens);
        } else {
            //verwijder alle afnemerindicatie objecten voor de andere diensten
            final Set<MetaObject> geautoriseerdeObjecten = berichtgegevens.getGeautoriseerdeObjecten(PERSOON_AFNEMERINDICATIE);
            for (final MetaObject metaObject : geautoriseerdeObjecten) {
                berichtgegevens.verwijderAutorisatie(metaObject);
            }
        }
    }

    /**
     * afnemerindicatie wordt getoond in de volgende gevallen: - afnemerindicatie is geldig op peilmoment (dwz er bestaan nog records in de
     * afnemerindicatie standaardgroep) - afnemerindicatie heeft een bestaande leveringsautorisatie met protocolleringniveau niet geheim.
     */
    private void filterAfnemerindicaties(final Berichtgegevens berichtgegevens) {
        final Set<MetaObject> geautoriseerdeObjecten = berichtgegevens.getGeautoriseerdeObjecten(PERSOON_AFNEMERINDICATIE);
        for (MetaObject metaObject : geautoriseerdeObjecten) {
            final MetaGroep standaard = metaObject.getGroep(Element.PERSOON_AFNEMERINDICATIE_STANDAARD);

            //Records uit de standaardgroep zijn potentieel niet geautoriseerd (geen attributen zijn aanwezig)
            //maar is wel geldig op een gegeven peilmoment (dwz, zit in kandidaatrecords)
            //In dit geval moeten we het standaardrecord expliciet autoriseren. De meeste gegevens zitten
            //namelijk in het records van de identiteit groep. Zonder standaardgroep wordt de identiteit groep niet
            //getoond (rendering)
            boolean geldigOpPeilmoment = false;
            for (MetaRecord record : standaard.getRecords()) {
                if (berichtgegevens.getKandidaatRecords().contains(record)) {
                    geldigOpPeilmoment = true;
                    berichtgegevens.autoriseer(record);
                    break;
                }
            }
            boolean afnemerindicatieLeveren = false;
            if (geldigOpPeilmoment) {
                final MetaGroep identiteit = metaObject.getGroep(Element.PERSOON_AFNEMERINDICATIE_IDENTITEIT);
                final MetaRecord identiteitRecord = identiteit.getRecords().iterator().next();
                final MetaAttribuut attribuut = identiteitRecord.getAttribuut(Element.PERSOON_AFNEMERINDICATIE_LEVERINGSAUTORISATIEIDENTIFICATIE);
                final Leveringsautorisatie leveringsautorisatie = leveringsautorisatieService.geefLeveringautorisatie(attribuut.getWaarde());
                afnemerindicatieLeveren = leveringsautorisatie != null && Protocolleringsniveau.GEHEIM != leveringsautorisatie.getProtocolleringsniveau();
            }

            if (!afnemerindicatieLeveren) {
                berichtgegevens.verwijderAutorisatie(metaObject);
            }
        }
    }
}
