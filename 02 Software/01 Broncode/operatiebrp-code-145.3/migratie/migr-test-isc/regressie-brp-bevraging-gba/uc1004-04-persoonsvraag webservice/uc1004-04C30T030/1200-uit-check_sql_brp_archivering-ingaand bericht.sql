select count(*) as aantal from ber.ber where dienst = $$dienst$$ and levsautorisatie = $$autorisatie$$ and zendendepartij = $$partij$$ and richting = 1;
