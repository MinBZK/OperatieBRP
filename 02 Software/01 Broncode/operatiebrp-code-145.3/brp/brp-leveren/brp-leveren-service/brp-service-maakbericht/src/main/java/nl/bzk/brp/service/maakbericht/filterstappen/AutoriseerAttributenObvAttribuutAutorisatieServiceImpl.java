/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.ModelIndex;
import nl.bzk.brp.service.cache.LeveringsAutorisatieCache;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import org.springframework.stereotype.Component;


/**
 * Autoriseert de attributen die aangewezen zijn in de het autorisatieschema.
 * <p>
 * <p>
 * Onderstaand een metamodel van een adresstructuur met een actueel, gewijzigd en vervallen adres:
 * <pre><code>&nbsp;[o] Persoon.Adres id=90133
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[g] Persoon.Adres.Standaard
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[r] id=90233
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.ActieInhoud, '90778'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.DatumAanvangGeldigheid, '20151231'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.Huisnummer, '11'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.TijdstipRegistratie, 'Thu Dec 31 21:00:57 CET 2015'
 * </code></pre>
 * <p>
 * Met de autorisatie op <tt>Persoon.Adres.Huisnummer</tt> en kandidaatrecords <tt>[r] id=90233</tt> wordt de onderstaande autorisatieboom gemaakt.
 * <pre><code>&nbsp;[o] Persoon.Adres id=90133
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[g] Persoon.Adres.Standaard
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[r] id=90233
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.Huisnummer, '11'
 * </code></pre>
 */
@Component
@Bedrijfsregel(Regel.R1974)
@Bedrijfsregel(Regel.R2217)
final class AutoriseerAttributenObvAttribuutAutorisatieServiceImpl implements MaakBerichtStap {

    @Inject
    private LeveringsAutorisatieCache leveringsAutorisatieCache;

    private AutoriseerAttributenObvAttribuutAutorisatieServiceImpl() {
    }

    @Bedrijfsregel(Regel.R1974)
    @Override
    public void execute(final Berichtgegevens berichtgegevens) {
        final Autorisatiebundel autorisatiebundel = berichtgegevens.getAutorisatiebundel();
        final Dienst dienst = autorisatiebundel.getDienst();
        final List<AttribuutElement> geldigeAttribuutElementen = leveringsAutorisatieCache.geefGeldigeElementen(autorisatiebundel
                .getToegangLeveringsautorisatie(), dienst);
        final ModelIndex modelIndex = berichtgegevens.getPersoonslijst().getModelIndex();
        for (final AttribuutElement attr : geldigeAttribuutElementen) {
            final Iterable<MetaAttribuut> plAttributen = modelIndex.geefAttributen(attr);
            for (final MetaAttribuut plAttribuut : plAttributen) {
                if (berichtgegevens.getKandidaatRecords().contains(plAttribuut.getParentRecord())) {
                    berichtgegevens.autoriseer(plAttribuut);
                }
            }
        }
    }
}
