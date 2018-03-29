/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import com.google.common.collect.Sets;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroep;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerantwoordingCategorie;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.ModelIndex;
import nl.bzk.brp.service.cache.GeldigeAttributenElementenCache;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import org.springframework.stereotype.Component;


/**
 * Autoriseert attributen met autorisatie "Via groepsautorisatie". Deze attributen zijn nooit direct te autoriseren, maar worden geleverd als de
 * groepsautorisatie aanwezig is. Afhankelijk van indicaties op formele historie, materiele historie en verantwoording worden attributen geautoriseerd voor
 * levering. B.v. tijdstip verval van een groep wordt alleen geleverd als de partij geautoriseerd is om formele historie te zien voor de groep.
 * <p>
 * Onderstaand een aantal voorbeelden, met als uitgangspunt een metamodel van een adresstructuur met een actueel, gewijzigd en vervallen adres:
 * <pre><code>&nbsp;[o] Persoon.Adres id=90133
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
 * <b>Voorbeeld 1</b>, met kandidaatrecords <tt>[r] id=90232, [r] id=90233, [r] id=90234</tt> met autorisatie Persoon.Adres.Standaard indicatieFormeel=J,
 * indicatieMaterieel=N en indicatieVerantwoording=N levert de volgende autorisatieboom:
 * <pre><code>&nbsp;[o] Persoon.Adres id=90133
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[g] Persoon.Adres.Standaard
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[r] id=90232
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.TijdstipRegistratie, 'Fri Dec 31 21:00:55 CET 2010'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.TijdstipVerval, 'Thu Dec 31 21:00:57 CET 2015'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[r] id=90233
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.TijdstipRegistratie, 'Thu Dec 31 21:00:57 CET 2015'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[r] id=90234
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.TijdstipRegistratie, 'Fri Dec 31 21:00:55 CET 2010'
 * </code></pre>
 * <p>
 * <b>Voorbeeld 2</b>, met kandidaatrecords <tt>[r] id=90232, [r] id=90233, [r] id=90234</tt> met autorisatie Persoon.Adres.Standaard indicatieFormeel=N,
 * indicatieMaterieel=J en indicatieVerantwoording=N levert de volgende autorisatieboom:
 * <pre><code>&nbsp;[o] Persoon.Adres id=90133
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[g] Persoon.Adres.Standaard
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[r] id=90234
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.DatumEindeGeldigheid, '20151231'
 * </code></pre>
 * <p>
 * LET OP: ondanks dat DatumAanvangGeldigheid een materieel attribuut is wordt deze hier niet geautoriseerd. DatumAanvangGeldigheid is autoriseerbaar als
 * normaal attribuut in {@link AutoriseerAttributenObvAttribuutAutorisatieServiceImpl}
 * <p>
 * <br><b>Voorbeeld 3</b>, met kandidaatrecords <tt>[r] id=90232, [r] id=90233, [r] id=90234</tt> met autorisatie Persoon.Adres.Standaard
 * indicatieFormeel=N, indicatieMaterieel=N en indicatieVerantwoording=J levert de volgende autorisatieboom:
 * <pre><code>&nbsp;[o] Persoon.Adres id=90133
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[g] Persoon.Adres.Standaard
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[r] id=90232
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.ActieInhoud, '90777'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.ActieVerval, '90778'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[r] id=90233
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.ActieInhoud, '90778'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[r] id=90234
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.ActieAanpassingGeldigheid, '90778'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.ActieInhoud, '90777'
 * </code></pre>
 */
@Component
//Beeindigde groep alleen leveren bij autorisatie op materiele historie
@Bedrijfsregel(Regel.R1349)
//Leveren van DatumEindeGeldigheid mag alleen bij autorisatie op materiele historie
@Bedrijfsregel(Regel.R1546)
//Leveren van DatumEindeGeldigheid mag alleen bij autorisatie op materiele historie
@Bedrijfsregel(Regel.R1547)
//Leveren van Datum/tijd registratie en Datum/tijd verval mag alleen bij autorisatie voor formele historie
@Bedrijfsregel(Regel.R1548)
//Zoekdiensten leveren alleen wettelijk verplichte verantwoordingsgegevens
@Bedrijfsregel(Regel.R1549)
//Alleen attributen leveren waarvoor autorisatie bestaat
@Bedrijfsregel(Regel.R1974)
//Zoekdiensten leveren alleen wettelijk verplichte verantwoordingsgegevens
@Bedrijfsregel(Regel.R2263)
//Afhandeling parameter verantwoording
@Bedrijfsregel(Regel.R2274)
final class AutoriseerHistorieEnVerantwoordingAttribuutServiceImpl implements MaakBerichtStap {

    @Inject
    private GeldigeAttributenElementenCache geldigeAttributenElementenCache;

    private AutoriseerHistorieEnVerantwoordingAttribuutServiceImpl() {
    }

    @Override
    public void execute(final Berichtgegevens berichtgegevens) {
        final Dienst dienst = berichtgegevens.getAutorisatiebundel().getDienst();
        autoriseerAttributenViaGroepen(berichtgegevens, dienst);
    }

    private void autoriseerAttributenViaGroepen(final Berichtgegevens berichtgegevens, final Dienst dienst) {
        final ModelIndex modelIndex = berichtgegevens.getPersoonslijst().getModelIndex();
        for (final DienstbundelGroep dienstbundelGroep : dienst.getDienstbundel().getDienstbundelGroepSet()) {
            final GroepElement groepElement = ElementHelper.getGroepElement(dienstbundelGroep.getGroep().getId());
            final Set<MetaGroep> metaGroepSet = modelIndex.geefGroepenVanElement(groepElement);
            autoriseerAttributenViaGroepen(berichtgegevens, dienstbundelGroep, groepElement, metaGroepSet);
        }
    }

    private void autoriseerAttributenViaGroepen(final Berichtgegevens berichtgegevens, final DienstbundelGroep dienstbundelGroep,
                                                final GroepElement groepElement, final Set<MetaGroep> metaGroepSet) {
        final boolean verantwoordingLeveren = berichtgegevens.getParameters().isVerantwoordingLeveren();
        for (final MetaGroep groep : metaGroepSet) {
            for (final MetaRecord metaRecord : groep.getRecords()) {
                if (!berichtgegevens.getKandidaatRecords().contains(metaRecord)) {
                    continue;
                }
                final Set<String> teAutoriserenElementen = Sets.newHashSet();
                bepaalTeLeverenFormeleElementen(dienstbundelGroep, metaRecord, teAutoriserenElementen);
                bepaalTeLeverenMaterieleElementen(dienstbundelGroep, metaRecord, teAutoriserenElementen);
                bepaalTeLeverenVerantwoordingElementen(dienstbundelGroep, metaRecord, teAutoriserenElementen, verantwoordingLeveren);
                autoriseerAttribuut(berichtgegevens, groepElement, metaRecord, teAutoriserenElementen);
            }
        }
    }

    private void autoriseerAttribuut(final Berichtgegevens berichtgegevens, final GroepElement groepElement, final MetaRecord metaRecord,
                                     final Set<String> teAutoriserenElementen) {
        for (final String teAutoriserenElement : teAutoriserenElementen) {
            final AttribuutElement attribuutElement = groepElement.getAttribuutMetElementNaam(teAutoriserenElement);
            final MetaAttribuut attribuut = metaRecord.getAttribuut(attribuutElement);
            if (attribuut != null && geldigeAttributenElementenCache.geldigVoorGroepAutorisatie(attribuutElement)) {
                berichtgegevens.autoriseer(attribuut);
            }
        }
    }

    /**
     * Indien een dienstbundelgroep de indicatie JA heeft voor formele historie, dan worden de attributen "Datum/tijd registratie" en  "Datum/tijd verval"
     * geautoriseerd voor levering.
     */
    private void bepaalTeLeverenFormeleElementen(final DienstbundelGroep dienstbundelGroep, final MetaRecord metaRecord,
                                                 final Set<String> teAutoriserenElementen) {
        if (dienstbundelGroep.getIndicatieFormeleHistorie()) {
            if (metaRecord.getActieInhoud() != null) {
                teAutoriserenElementen.add(AttribuutElement.DATUM_TIJD_REGISTRATIE);
            }
            if (metaRecord.getActieVerval() != null) {
                teAutoriserenElementen.add(AttribuutElement.DATUM_TIJD_VERVAL);
            }
            //autoriseer voor dienstverantwoording op attr niveau
            if (metaRecord.getParentGroep().getGroepElement().getVerantwoordingCategorie() == VerantwoordingCategorie.D) {
                final AttribuutElement attribuutElementTsReg = metaRecord.getParentGroep().getGroepElement()
                        .getAttribuutMetElementNaam(AttribuutElement.DATUM_TIJD_REGISTRATIE);
                final MetaAttribuut attribuutTsReg = metaRecord.getAttribuut(attribuutElementTsReg);
                if (attribuutTsReg != null) {
                    teAutoriserenElementen.add(AttribuutElement.DATUM_TIJD_REGISTRATIE);
                }
                final AttribuutElement attribuutElementTsVerval = metaRecord.getParentGroep().getGroepElement()
                        .getAttribuutMetElementNaam(AttribuutElement.DATUM_TIJD_VERVAL);
                final MetaAttribuut attribuutTsVerval = metaRecord.getAttribuut(attribuutElementTsVerval);
                if (attribuutTsVerval != null) {
                    teAutoriserenElementen.add(AttribuutElement.DATUM_TIJD_VERVAL);
                }
            }
        }
    }

    /**
     * Indien een dienstbundelgroep de indicatie JA heeft voor materiele historie wordt het attribuut "Datum einde geldigheid" geautoriseerd voor levering.
     * LET OP: Het andere materiele attribuut "Datum aanvang geldigheid" wordt hier NIET geautoriseerd. Dit gebeurt net als alle andere 'normale'
     * attributen in {@link AutoriseerAttributenObvAttribuutAutorisatieServiceImpl}.
     */
    private void bepaalTeLeverenMaterieleElementen(final DienstbundelGroep dienstbundelGroep, final MetaRecord metaRecord,
                                                   final Set<String> teAutoriserenElementen) {
        if (dienstbundelGroep.getIndicatieMaterieleHistorie() && metaRecord.getDatumEindeGeldigheid() != null) {
            teAutoriserenElementen.add(AttribuutElement.DATUM_EINDE_GELDIGHEID);
        }
    }

    /**
     * Indien een dienstbundelgroep de indicatie JA heeft voor verantwoording worden de attributen "BRP Actie inhoud", "BRP Actie verval" en "BRP Actie
     * Aanpassing Geldigheid" geautoriseerd voor levering. Echter, indien de parameter Verantwoording in verzoek waarde 'GEEN' heeft, dan worden geen
     * actieverwijzingen opgenomen in het bericht (R2274).
     */
    private void bepaalTeLeverenVerantwoordingElementen(final DienstbundelGroep dienstbundelGroep, final MetaRecord metaRecord,
                                                        final Set<String> teAutoriserenElementen, final boolean verantwoordingLeveren) {
        if (dienstbundelGroep.getIndicatieVerantwoording() && verantwoordingLeveren) {
            if (metaRecord.getActieInhoud() != null) {
                teAutoriserenElementen.add(AttribuutElement.BRP_ACTIE_INHOUD);
            }
            if (metaRecord.getActieVerval() != null) {
                teAutoriserenElementen.add(AttribuutElement.BRP_ACTIE_VERVAL);
            }
            if (metaRecord.getActieAanpassingGeldigheid() != null) {
                teAutoriserenElementen.add(AttribuutElement.BRP_ACTIE_AANPASSING_GELDIGHEID);
            }
        }
        if (metaRecord.getActieVerval() != null) {
            // Nadere aanduiding verval en bijhouding beëindigd? worden (indien gevuld) bij de levering
            // van een vervallen voorkomen altijd meegeleverd.
            if (metaRecord.getNadereAanduidingVerval() != null) {
                teAutoriserenElementen.add(AttribuutElement.NADERE_AANDUIDING_VERVAL);
            }
            final String elementNaam = ElementHelper.getAttribuutElement(Element.PERSOON_NATIONALITEIT_INDICATIEBIJHOUDINGBEEINDIGD).getElementNaam();
            if (metaRecord.getParentGroep().getGroepElement().bevatAttribuut(elementNaam)) {
                teAutoriserenElementen.add(elementNaam);
            }
        }
    }
}
