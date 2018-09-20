/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import javax.inject.Inject;
import nl.bzk.brp.gba.dataaccess.VerConvRepository;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.basis.FormeelHistorisch;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.basis.NadereAanduidingVervalToepasbaar;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;

/**
 * Basis mapper voor BRP met formele historie naar Conversie model.
 *
 * @param <B>
 *            BRP Basis object type
 * @param <H>
 *            BRP formele historie type
 * @param <G>
 *            Conversie model groep inhoud type
 */
public abstract class AbstractFormeelMapper<B, H extends FormeelHistorisch & ModelIdentificeerbaar<?>, G extends BrpGroepInhoud>
        extends AbstractMapper<B, H, G>
{

    private final ElementEnum elementEnumTijdstipRegistratie;
    private final ElementEnum elementEnumTijdstipVerval;

    @Inject
    private VerConvRepository verConvRepository;

    /**
     * Constructor.
     *
     * @param elementEnumTijdstipRegistratie
     *            DB Object voor tijdstip registratie
     * @param elementEnumTijdstipVerval
     *            DB Object voor tijdstip verval
     */
    protected AbstractFormeelMapper(final ElementEnum elementEnumTijdstipRegistratie, final ElementEnum elementEnumTijdstipVerval) {
        this.elementEnumTijdstipRegistratie = elementEnumTijdstipRegistratie;
        this.elementEnumTijdstipVerval = elementEnumTijdstipVerval;
    }

    @Override
    public final BrpGroep<G> mapGroep(final H historie, final OnderzoekMapper onderzoekMapper, final ActieHisVolledigLocator actieHisVolledigLocator) {
        final G inhoud = mapInhoud(historie, onderzoekMapper);
        if (inhoud == null) {
            return null;
        } else {
            final Integer stapelNummer = getStapelNummer(historie);

            final BrpHistorie brpHistorie =
                    BrpMapperUtil.mapHistorie(
                        historie.getFormeleHistorie(),
                        getActieInhoud(historie),
                        null,
                        getActieVerval(historie),
                        historie instanceof NadereAanduidingVervalToepasbaar ? ((NadereAanduidingVervalToepasbaar) historie).getNadereAanduidingVerval()
                                                                             : null,
                        onderzoekMapper,
                        historie.getID(),
                        null,
                        null,
                        elementEnumTijdstipRegistratie,
                        elementEnumTijdstipVerval);

            final BrpActie brpActieInhoud =
                    BrpMapperUtil.mapActieInhoud(historie, onderzoekMapper, stapelNummer, verConvRepository, actieHisVolledigLocator);
            final BrpActie brpActieVerval =
                    BrpMapperUtil.mapActieVerval(historie, onderzoekMapper, stapelNummer, verConvRepository, actieHisVolledigLocator);

            return new BrpGroep<G>(inhoud, brpHistorie, brpActieInhoud, brpActieVerval, null);
        }
    }
}
