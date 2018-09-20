/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig;

import static nl.bzk.brp.model.hisvolledig.FormeleHistoriePredikaat.bekendOp;
import static nl.bzk.brp.model.hisvolledig.MaterieleHistoriePredikaat.geldigOp;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.kern.PersoonAdresHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVoornaamHisVolledig;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonAanschrijvingGroep;
import nl.bzk.brp.model.logisch.kern.PersoonAdres;
import nl.bzk.brp.model.logisch.kern.PersoonAfgeleidAdministratiefGroep;
import nl.bzk.brp.model.logisch.kern.PersoonBijhoudingsaardGroep;
import nl.bzk.brp.model.logisch.kern.PersoonBijhoudingsgemeenteGroep;
import nl.bzk.brp.model.logisch.kern.PersoonBijzondereVerblijfsrechtelijkePositieGroep;
import nl.bzk.brp.model.logisch.kern.PersoonEUVerkiezingenGroep;
import nl.bzk.brp.model.logisch.kern.PersoonGeboorteGroep;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsaanduidingGroep;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.logisch.kern.PersoonIdentificatienummersGroep;
import nl.bzk.brp.model.logisch.kern.PersoonImmigratieGroep;
import nl.bzk.brp.model.logisch.kern.PersoonIndicatie;
import nl.bzk.brp.model.logisch.kern.PersoonInschrijvingGroep;
import nl.bzk.brp.model.logisch.kern.PersoonNationaliteit;
import nl.bzk.brp.model.logisch.kern.PersoonOpschortingGroep;
import nl.bzk.brp.model.logisch.kern.PersoonOverlijdenGroep;
import nl.bzk.brp.model.logisch.kern.PersoonPersoonskaartGroep;
import nl.bzk.brp.model.logisch.kern.PersoonReisdocument;
import nl.bzk.brp.model.logisch.kern.PersoonSamengesteldeNaamGroep;
import nl.bzk.brp.model.logisch.kern.PersoonUitsluitingNLKiesrechtGroep;
import nl.bzk.brp.model.logisch.kern.PersoonVerblijfstitelGroep;
import nl.bzk.brp.model.logisch.kern.PersoonVoornaam;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorteModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsaanduidingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIdentificatienummersModel;
import nl.bzk.brp.util.DatumUtil;
import org.apache.commons.collections.CollectionUtils;

/**
 * Proxy voor {@link PersoonHisVolledig} die de informatie terug geeft die op een
 * peildatum bekend is van de persoon.
 */
public class PersoonHisVolledigView implements Persoon {

    private final PersoonHisVolledig persoon;
    private final FormeleHistoriePredikaat formeelPredikaat;
    private final MaterieleHistoriePredikaat materieelPredikaat;

    private final DatumTijd formeelPeilmoment;
    private final Datum materieelPeilmoment;

    /**
     * Proxy een {@link PersoonHisVolledig} instantie met de formeelPeilmoment en materieelPeilmoment vandaag.
     *
     * @param persoon de persoonVolledig instantie die de informatie bevat
     * @see #PersoonHisVolledigView(PersoonHisVolledig, nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd, nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum)
     */
    public PersoonHisVolledigView(final PersoonHisVolledig persoon) {
        this(persoon, new DatumTijd(new Date()));
    }

    /**
     * Proxy een {@link PersoonHisVolledig} instantie met de formeelPeilmoment en materieelPeilmoment gesteld op <code>datum</code>.
     *
     * @param persoon           de persoonVolledig instantie die de informatie bevat
     * @param formeelPeilmoment de datumTijd waarde voor formeelPeilmoment en materieelPeilmoment
     * @see #PersoonHisVolledigView(PersoonHisVolledig, nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd, nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum)
     */
    public PersoonHisVolledigView(final PersoonHisVolledig persoon, final DatumTijd formeelPeilmoment) {
        this(persoon, formeelPeilmoment, DatumUtil.dateToDatum(formeelPeilmoment.getWaarde()));
    }

    /**
     * Proxy een {@link PersoonHisVolledig} instantie met de gegeven formeelPeilmoment en materieelPeilmoment.
     * De formeelPeilmoment is het moment waarop de doorsnede door de historie van een Persoon wordt gemaakt.
     * De materieelPeilmoment is de datum waarnaar we kijken vanaf de formeelPeilmoment.
     *
     * @param persoon             de persoonVolledig instantie die de informatie bevat
     * @param formeelPeilmoment   de formeelPeilmoment waarop informatie wordt opgehaald
     * @param materieelPeilmoment de datum waarnaar wordt gekeken
     */
    public PersoonHisVolledigView(final PersoonHisVolledig persoon, final DatumTijd formeelPeilmoment,
                                  final Datum materieelPeilmoment)
    {
        this.persoon = persoon;
        this.formeelPredikaat = bekendOp(formeelPeilmoment);
        this.materieelPredikaat = geldigOp(formeelPeilmoment, materieelPeilmoment);

        this.formeelPeilmoment = formeelPeilmoment;
        this.materieelPeilmoment = materieelPeilmoment;
    }

    @Override
    public SoortPersoon getSoort() {
        return persoon.getSoort();
    }

    @Override
    public PersoonGeslachtsaanduidingGroep getGeslachtsaanduiding() {
        return (HisPersoonGeslachtsaanduidingModel) CollectionUtils
                .find(persoon.getHisPersoonGeslachtsaanduidingLijst(), materieelPredikaat);
    }

    @Override
    public Collection<? extends PersoonAdres> getAdressen() {
        Set<PersoonAdres> result = new HashSet<PersoonAdres>(persoon.getAdressen().size());
        for (PersoonAdresHisVolledig adresVolledig : persoon.getAdressen()) {
            result.add(new PersoonAdresHisVolledigView(this, adresVolledig, formeelPeilmoment, materieelPeilmoment));
        }

        return result;
    }

    @Override
    public Collection<? extends PersoonVoornaam> getVoornamen() {
        Set<PersoonVoornaam> result = new HashSet<PersoonVoornaam>(persoon.getVoornamen().size());
        for (PersoonVoornaamHisVolledig voornaamVolledig : persoon.getVoornamen()) {
            result.add(
                    new PersoonVoornaamHisVolledigView(this, voornaamVolledig, formeelPeilmoment, materieelPeilmoment));
        }

        return result;
    }

    @Override
    public PersoonIdentificatienummersGroep getIdentificatienummers() {
        return (HisPersoonIdentificatienummersModel) CollectionUtils
                .find(persoon.getHisPersoonIdentificatienummersLijst(), materieelPredikaat);
    }

    @Override
    public PersoonGeboorteGroep getGeboorte() {
        return (HisPersoonGeboorteModel) CollectionUtils.find(persoon.getHisPersoonGeboorteLijst(), formeelPredikaat);
    }

    @Override
    public PersoonAfgeleidAdministratiefGroep getAfgeleidAdministratief() {
        throw new UnsupportedOperationException("Nog niet geimplementeerd");
    }

    @Override
    public PersoonSamengesteldeNaamGroep getSamengesteldeNaam() {
        throw new UnsupportedOperationException("Nog niet geimplementeerd");
    }

    @Override
    public PersoonInschrijvingGroep getInschrijving() {
        throw new UnsupportedOperationException("Nog niet geimplementeerd");
    }

    @Override
    public PersoonBijhoudingsaardGroep getBijhoudingsaard() {
        throw new UnsupportedOperationException("Nog niet geimplementeerd");
    }

    @Override
    public PersoonBijhoudingsgemeenteGroep getBijhoudingsgemeente() {
        throw new UnsupportedOperationException("Nog niet geimplementeerd");
    }

    @Override
    public PersoonOpschortingGroep getOpschorting() {
        throw new UnsupportedOperationException("Nog niet geimplementeerd");
    }

    @Override
    public PersoonOverlijdenGroep getOverlijden() {
        throw new UnsupportedOperationException("Nog niet geimplementeerd");
    }

    @Override
    public PersoonAanschrijvingGroep getAanschrijving() {
        throw new UnsupportedOperationException("Nog niet geimplementeerd");
    }

    @Override
    public PersoonImmigratieGroep getImmigratie() {
        throw new UnsupportedOperationException("Nog niet geimplementeerd");
    }

    @Override
    public PersoonVerblijfstitelGroep getVerblijfstitel() {
        throw new UnsupportedOperationException("Nog niet geimplementeerd");
    }

    @Override
    public PersoonBijzondereVerblijfsrechtelijkePositieGroep getBijzondereVerblijfsrechtelijkePositie() {
        throw new UnsupportedOperationException("Nog niet geimplementeerd");
    }

    @Override
    public PersoonUitsluitingNLKiesrechtGroep getUitsluitingNLKiesrecht() {
        throw new UnsupportedOperationException("Nog niet geimplementeerd");
    }

    @Override
    public PersoonEUVerkiezingenGroep getEUVerkiezingen() {
        throw new UnsupportedOperationException("Nog niet geimplementeerd");
    }

    @Override
    public PersoonPersoonskaartGroep getPersoonskaart() {
        throw new UnsupportedOperationException("Nog niet geimplementeerd");
    }

    @Override
    public Collection<? extends PersoonGeslachtsnaamcomponent> getGeslachtsnaamcomponenten() {
        throw new UnsupportedOperationException("Nog niet geimplementeerd");
    }

    @Override
    public Collection<? extends PersoonNationaliteit> getNationaliteiten() {
        throw new UnsupportedOperationException("Nog niet geimplementeerd");
    }

    @Override
    public Collection<? extends PersoonIndicatie> getIndicaties() {
        throw new UnsupportedOperationException("Nog niet geimplementeerd");
    }

    @Override
    public Collection<? extends PersoonReisdocument> getReisdocumenten() {
        throw new UnsupportedOperationException("Nog niet geimplementeerd");
    }

    @Override
    public Collection<? extends Betrokkenheid> getBetrokkenheden() {
        throw new UnsupportedOperationException("Nog niet geimplementeerd");
    }
}
