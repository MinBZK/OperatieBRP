/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import java.util.Collection;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortElementAutorisatie;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.leveringmodel.ChildFirstModelVisitor;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaModel;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import org.springframework.stereotype.Component;

/**
 * Bij scoping mag het bericht alleen groepen bevatten die verplichte attributen of attributen uit de scope bevatten.
 * <p>
 * Verwijdert objecten / groepen attributen niet in scope.
 * <p>
 * <b>Opschonen attributen</b>,
 * <br><tt>Persoon.Adres.Huisnummer</tt> niet in scope:
 * <pre><code>&nbsp;[o] Persoon.Adres id=90133
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[g] Persoon.Adres.Standaard
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[r] id=90233
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.ActieInhoud, '90778'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.DatumAanvangGeldigheid, '20151231'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.Huisnummer, '11'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.TijdstipRegistratie, 'Thu Dec 31 21:00:57 CET 2015'
 * </code></pre>
 * <p>
 * resulteert in:
 * <pre><code>&nbsp;[o] Persoon.Adres id=90133
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[g] Persoon.Adres.Standaard
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[r] id=90233
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.ActieInhoud, '90778'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.DatumAanvangGeldigheid, '20151231'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.TijdstipRegistratie, 'Thu Dec 31 21:00:57 CET 2015'
 * </code></pre>
 * <p>
 * <b>Opschonen groep</b>,
 * <br><tt>Persoon.Bijhouding.PartijCode</tt> niet in scope, geen verplicht attribuut
 * <pre><code>&nbsp;&nbsp;[g] Persoon.Bijhouding
 * &nbsp;&nbsp;&nbsp;[r] id=90171
 * &nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Bijhouding.ActieInhoud, '90340'
 * &nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Bijhouding.DatumAanvangGeldigheid, '19980622'
 * &nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Bijhouding.IndicatieOnverwerktDocumentAanwezig, 'false'
 * &nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Bijhouding.PartijCode, '62601'
 * &nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Bijhouding.TijdstipRegistratie, 'Wed Mar 16 02:00:00 CET 2011'
 * </code></pre>
 * <p>
 * resulteert in:
 * <br> het verwijderen van de groep, er blijft op het record niets over
 * wat in scope staat, of een verplicht attribuut is.
 * <p>
 * <b>Opschonen groep niet mogelijk ivm verplicht attribuut</b>,
 * <pre><code>&nbsp;&nbsp;[g] Persoon.Bijhouding
 * &nbsp;&nbsp;&nbsp;[r] id=90171
 * &nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Bijhouding.ActieInhoud, '90340'
 * &nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Bijhouding.BijhoudingsaardCode, 'I'
 * &nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Bijhouding.DatumAanvangGeldigheid, '19980622'
 * &nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Bijhouding.IndicatieOnverwerktDocumentAanwezig, 'false'
 * &nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Bijhouding.PartijCode, '62601'
 * &nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Bijhouding.TijdstipRegistratie, 'Wed Mar 16 02:00:00 CET 2011'</code></pre>
 * <p>
 * resulteert in:
 * De groep blijft staan, <tt>Persoon.Bijhouding.NadereBijhoudingsaardCode</tt> is een verplicht attribuut, de opschoning
 * wordt dan niet toegepast.
 */
@Component
@Bedrijfsregel(Regel.R2218)
@Bedrijfsregel(Regel.R2217)
final class GeefDetailsPersoonCorrigeerVoorScoping implements MaakBerichtStap {

    @Override
    public void execute(final Berichtgegevens berichtgegevens) {
        if (berichtgegevens.getAutorisatiebundel().getSoortDienst() == SoortDienst.GEEF_DETAILS_PERSOON
                && !berichtgegevens.getParameters().getGevraagdeElementen().isEmpty()) {
            //corrigeer leveren van attributen niet in scope
            final Set<MetaAttribuut> geautoriseerdeAttributen = berichtgegevens.getGeautoriseerdeAttributen();
            for (MetaAttribuut metaAttribuut : geautoriseerdeAttributen) {
                if (!attribuutVoldoetAanScopingVoorwaarden(berichtgegevens, metaAttribuut)) {
                    berichtgegevens.verwijderAutorisatie(metaAttribuut);
                }
            }

            //corrigeer leveren van groepen
            final Set<MetaGroep> geautoriseerdeGroepen = berichtgegevens.getGeautoriseerdeGroepen();
            for (MetaGroep groep : geautoriseerdeGroepen) {
                if (!groepVoldoetAanScopingVoorwaarden(berichtgegevens, groep)) {
                    berichtgegevens.verwijderAutorisatie(groep);
                }
            }

            //corrigeer leveren van objecten
            if (berichtgegevens.getMaakBerichtPersoon().getHistorieFilterInformatie() == null) {
                berichtgegevens.getPersoonslijst().getMetaObject().accept(new ChildFirstModelVisitor() {
                    @Override
                    protected void doVisit(final MetaObject object) {
                        //object moet verwijderd worden als het geen onderliggende groepen of objecten bevat
                        final Collection<MetaModel> onderliggendeAutorisaties = berichtgegevens.getOnderliggendeAutorisaties(object);
                        if (onderliggendeAutorisaties.isEmpty()) {
                            berichtgegevens.verwijderAutorisatie(object);
                        }
                    }
                });
            }
        }
    }

    //elke groep moet minimaal één record bevatten met één verplicht attribuut OF minimaal 1 attribuut uit de scope
    private boolean groepVoldoetAanScopingVoorwaarden(final Berichtgegevens berichtgegevens, final MetaGroep groep) {
        final Collection<MetaModel> records = berichtgegevens.getOnderliggendeAutorisaties(groep);
        for (MetaModel record : records) {
            if (recordVoldoetAanScopingVoorwaarde(berichtgegevens, record)) {
                return true;
            }
        }
        return false;
    }

    //elk record moet minimaal één verplicht attribuut bevatten OF minimaal 1 attribuut uit de scope
    private boolean recordVoldoetAanScopingVoorwaarde(final Berichtgegevens berichtgegevens, final MetaModel record) {
        final Collection<MetaModel> attributen = berichtgegevens.getOnderliggendeAutorisaties(record);
        for (MetaModel metaModel : attributen) {
            final MetaAttribuut attribuut = (MetaAttribuut) metaModel;
            if (attribuut.getAttribuutElement().getAutorisatie() == SoortElementAutorisatie.VERPLICHT
                    || berichtgegevens.getParameters().getGevraagdeElementen().contains(attribuut.getAttribuutElement())
                    ) {
                return true;
            }
        }
        return false;
    }

    //een attribuut moet in scope zijn en juiste soort autorisatie (niet optioneel,aanbevolen of bijhoudingsgegeven) hebben
    private boolean attribuutVoldoetAanScopingVoorwaarden(final Berichtgegevens berichtgegevens, final MetaAttribuut metaAttribuut) {
        final Set<AttribuutElement> gevraagdeElementen = berichtgegevens.getParameters().getGevraagdeElementen();
        final boolean isScopeAttribuut = gevraagdeElementen.contains(metaAttribuut.getAttribuutElement());
        if (!isScopeAttribuut) {
            final boolean isOptioneel = metaAttribuut.getAttribuutElement().getAutorisatie() == SoortElementAutorisatie.OPTIONEEL;
            final boolean isAanbevolen = metaAttribuut.getAttribuutElement().getAutorisatie() == SoortElementAutorisatie.AANBEVOLEN;
            final boolean isBijhoudersgegeven = metaAttribuut.getAttribuutElement().getAutorisatie() == SoortElementAutorisatie.BIJHOUDINGSGEGEVENS;
            if (isOptioneel || isAanbevolen || isBijhoudersgegeven) {
                return false;
            }
        }
        return true;
    }
}
