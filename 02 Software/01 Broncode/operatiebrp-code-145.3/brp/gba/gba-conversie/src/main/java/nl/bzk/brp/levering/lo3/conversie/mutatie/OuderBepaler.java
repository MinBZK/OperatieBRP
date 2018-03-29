/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AanduidingOuder;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.gba.dataaccess.Lo3AanduidingOuderRepository;
import nl.bzk.brp.levering.lo3.conversie.excepties.OnduidelijkeOudersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Bepaalt welke GBA ouder een BRP ouder is.
 */
@Component
public class OuderBepaler {

    @Autowired
    private Lo3AanduidingOuderRepository lo3AanduidingOuderRepository;

    /**
     * Bepaal de ouder 1 en ouder 2 voor de ouderbetrokkenheden.
     * @param ouderBetrokkenheden alle ouderbetrokkenheden
     * @return lijst ouderbetrokkenheden inclusief de ouder 1 en 2 aanduiding
     * @throws OnduidelijkeOudersException Indien ouder niet goed bepaald kan worden
     */
    public final List<OuderIdentificatie> bepaalOuders(final Collection<MetaObject> ouderBetrokkenheden) throws OnduidelijkeOudersException {
        final List<OuderIdentificatie> resultaat = new ArrayList<>();

        for (final MetaObject ouderBetrokkenheid : ouderBetrokkenheden) {
            final OuderIdentificatie ouderIdentificatie = new OuderIdentificatie(ouderBetrokkenheid);
            ouderIdentificatie.setOuderNummer(lo3AanduidingOuderRepository.getOuderIdentificatie(ouderBetrokkenheid.getObjectsleutel()));
            resultaat.add(ouderIdentificatie);
        }

        Collections.sort(resultaat, new Comparator<OuderIdentificatie>() {
            @Override
            public final int compare(final OuderIdentificatie arg0, final OuderIdentificatie arg1) {
                final Long voorkomen0 = arg0.getMetaObject().getObjectsleutel();
                final Long voorkomen1 = arg1.getMetaObject().getObjectsleutel();
                return voorkomen0.compareTo(voorkomen1);
            }
        });
        if (nietAlleOudernummersGevuld(resultaat)) {
            aanvullenOudernummers(resultaat);
        }

        return resultaat;
    }

    private boolean nietAlleOudernummersGevuld(final List<OuderIdentificatie> ouderIdentificaties) {
        boolean result = false;
        for (final OuderIdentificatie ouderIdentificatie : ouderIdentificaties) {
            if (ouderIdentificatie.getOuderNummer() == null) {
                result = true;
                break;
            }
        }
        return result;
    }

    private void aanvullenOudernummers(final List<OuderIdentificatie> ouderIdentificaties) throws OnduidelijkeOudersException {
        if (ouderIdentificaties.size() > 2) {
            throw new OnduidelijkeOudersException("Teveel ouders gevonden");
        }
        OuderIdentificatie ouder1 = null;
        OuderIdentificatie ouder2 = null;
        for (final OuderIdentificatie ouderIndentificatie : ouderIdentificaties) {
            if (ouder1 == null) {
                ouder1 = ouderIndentificatie;
            } else if (ouder2 == null) {
                ouder2 = ouderIndentificatie;
            }
        }

        plaatsOuderNummer(ouder1, ouder2);
    }

    private void plaatsOuderNummer(final OuderIdentificatie ouder1, final OuderIdentificatie ouder2) {
        if (ouder1 != null && ouder2 == null) {
            // blijkbaar 1 ouder en die is leeg, is namelijk eerder al gecontrolleerd
            ouder1.setOuderNummer(AanduidingOuder.OUDER_1);
            lo3AanduidingOuderRepository.setAanduidingOuderBijOuderBetrokkenheid(ouder1.getMetaObject().getObjectsleutel(), ouder1.getOuderNummer());
        }
        if (ouder1 != null && ouder2 != null) {
            if (ouder1.getOuderNummer() == null && ouder2.getOuderNummer() == null) {
                ouder1.setOuderNummer(AanduidingOuder.OUDER_1);
                ouder2.setOuderNummer(AanduidingOuder.OUDER_2);
            } else {
                bepaalNummerBijEnkeleLegeOuder(ouder1, ouder2);
            }
            lo3AanduidingOuderRepository.setAanduidingOuderBijOuderBetrokkenheid(ouder1.getMetaObject().getObjectsleutel(), ouder1.getOuderNummer());
            lo3AanduidingOuderRepository.setAanduidingOuderBijOuderBetrokkenheid(ouder2.getMetaObject().getObjectsleutel(), ouder2.getOuderNummer());
        }
    }

    private void bepaalNummerBijEnkeleLegeOuder(final OuderIdentificatie ouder1, final OuderIdentificatie ouder2) {
        if (ouder1.getOuderNummer() == null) {
            if (AanduidingOuder.OUDER_1 == ouder2.getOuderNummer()) {
                ouder1.setOuderNummer(AanduidingOuder.OUDER_2);
            } else {
                ouder1.setOuderNummer(AanduidingOuder.OUDER_1);
            }
        } else if (ouder2.getOuderNummer() == null) {
            if (AanduidingOuder.OUDER_1 == ouder1.getOuderNummer()) {
                ouder2.setOuderNummer(AanduidingOuder.OUDER_2);
            } else {
                ouder2.setOuderNummer(AanduidingOuder.OUDER_1);
            }
        }
    }

    /**
     * Ouder identificatie.
     */
    public static final class OuderIdentificatie {
        private final MetaObject metaObject;
        private AanduidingOuder ouderNummer;

        /**
         * Constructor.
         * @param metaObject ouder
         */
        public OuderIdentificatie(final MetaObject metaObject) {
            super();
            this.metaObject = metaObject;
        }

        /**
         * Geef de ouder.
         * @return ouder
         */
        public MetaObject getMetaObject() {
            return metaObject;
        }

        /**
         * Geef het ouder nummer.
         * @return ouder nummer
         */
        public AanduidingOuder getOuderNummer() {
            return ouderNummer;
        }

        /**
         * Zet het ouder nummer.
         * @param ouderNummer ouder nummer
         */
        public void setOuderNummer(final AanduidingOuder ouderNummer) {
            this.ouderNummer = ouderNummer;
        }

    }
}
