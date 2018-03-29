/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import java.util.HashSet;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.Actiebron;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import org.springframework.stereotype.Component;

/**
 * Indien de bij de ActieInhoud, ActieAanpassingGeldigheid en/of ActieVerval behorende Administratieve handeling een Actie \ Bron heeft waarbij
 * Rechtsgrond.Omschrijving gevuld is, dan moeten de velden ActieInhoud, ActieAanpassingGeldigheid en ActieVerval in de groep/het bericht aanwezig blijven.
 * Hierdoor wordt gegarandeerd dat de bijbehorende Administratieve handeling (incl. Partij en Actie \ Bron) wordt geleverd.
 * <p>
 * Als het actie attribuut getoond moet worden, dan kan dat alleen op records die niet weggefilterd zijn.
 * <p>
 * Onderstaand een metamodel van een adresstructuur met een actueel, gewijzigd en vervallen adres:
 * <pre><code>&nbsp;[o] Persoon.Adres id=90133
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[g] Persoon.Adres.Identiteit
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[r] id=90133
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[g] Persoon.Adres.Standaard
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[r] id=90232
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.ActieInhoud, '90777'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.ActieVerval, '90778'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.DatumAanvangGeldigheid, '20101231'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.Huisnummer, '16'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.TijdstipRegistratie, 'Fri Dec 31 21:00:55 CET 2010'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.TijdstipVerval, 'Thu Dec 31 21:00:57 CET 2015'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[r] id=90233
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.ActieInhoud, '90778'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.DatumAanvangGeldigheid, '20151231'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.GemeenteCode, '344'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.Huisnummer, '11'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.TijdstipRegistratie, 'Thu Dec 31 21:00:57 CET 2015'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[r] id=90234
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.ActieAanpassingGeldigheid, '90778'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.ActieInhoud, '90777'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.DatumAanvangGeldigheid, '20101231'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.DatumEindeGeldigheid, '20151231'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.Huisnummer, '16'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.TijdstipRegistratie, 'Fri Dec 31 21:00:55 CET 2010'
 * </code></pre>
 * <p>
 * Voorbeeld: Als record <tt>[r] id=90233</tt> tot de kandidaatrecords behoort en actie 90778 behoort tot een ABO admninistratievehandeling, dan resulteert
 * dit in de volgende structuur:
 * <pre><code>&nbsp;[o] Persoon.Adres id=90133
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[g] Persoon.Adres.Standaard
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[r] id=90233
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.ActieInhoud, '90778'
 * </code></pre>
 * <p>
 * De andere records behoren niet tot de kandidaatrecords en worden nooit getoond.
 */
@Component
@Bedrijfsregel(Regel.R1545)
@Bedrijfsregel(Regel.R1549)
final class AutoriseerAttributenObvAboPartijServiceImpl implements MaakBerichtStap {

    @Override
    public void execute(final Berichtgegevens berichtgegevens) {
        final Set<Actie> aboActies = bepaalAboActies(berichtgegevens);
        final Set<MetaRecord> geautoriseerdeRecords = berichtgegevens.getGeautoriseerdeRecords();
        for (final MetaRecord record : geautoriseerdeRecords) {
            for (final MetaAttribuut attribuut : record.getAttributen().values()) {
                if (attribuut.getAttribuutElement().isVerantwoording()
                        && aboActies.contains(attribuut.<Actie>getWaarde())) {
                    berichtgegevens.autoriseer(attribuut);
                }
            }
        }
    }

    private boolean isAboActie(final Actie actie) {
        for (final Actiebron actieBron : actie.getBronnen()) {
            if (actieBron.getRechtsgrondomschrijving() != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Bepaalt de ABO acties van alle administratievehandelingen.
     */
    private Set<Actie> bepaalAboActies(final Berichtgegevens berichtgegevens) {
        final Set<Actie> aboActies = new HashSet<>();
        final Set<AdministratieveHandeling> aboHandelingen = bepaalAboHandelingen(berichtgegevens);
        for (AdministratieveHandeling aboHandeling : aboHandelingen) {
            aboActies.addAll(aboHandeling.getActies());
        }
        return aboActies;
    }

    /**
     * Bepaalt de ABO administratievehandelingen.
     */
    private Set<AdministratieveHandeling> bepaalAboHandelingen(final Berichtgegevens berichtgegevens) {
        final Set<AdministratieveHandeling> handelingen = new HashSet<>();
        for (final AdministratieveHandeling admhnd : berichtgegevens.getPersoonslijst().getAdministratieveHandelingen()) {
            for (final Actie actie : admhnd.getActies()) {
                if (isAboActie(actie)) {
                    handelingen.add(admhnd);
                }
            }
        }
        return handelingen;
    }

}
