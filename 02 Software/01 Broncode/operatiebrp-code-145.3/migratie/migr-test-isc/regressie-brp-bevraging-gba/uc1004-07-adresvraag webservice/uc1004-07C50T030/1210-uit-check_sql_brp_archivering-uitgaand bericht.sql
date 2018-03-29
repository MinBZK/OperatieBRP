select count(*) as aantal from ber.ber where levsautorisatie = $$autorisatie$$ and ontvangendepartij = $$partij$$ and richting = 2;
