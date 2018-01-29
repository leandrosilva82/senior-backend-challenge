package digital.leandro.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import digital.leandro.dto.UFCityCount;
import digital.leandro.model.City;
import digital.leandro.repository.CityRepository;

@Service
public class CityService {

	@Autowired
	CityRepository cityRepository;

	public boolean loadCitiesToDB() throws IOException, URISyntaxException {

		ClassLoader classloader = Thread.currentThread().getContextClassLoader();

		try (Stream<String> stream = Files.lines(Paths.get(classloader.getResource("cidades.csv").toURI()))) {
			City city = new City();
			stream.skip(1).forEach(el -> {
				String[] fields = el.split(",");

				city.setIbgeid(Long.parseLong(fields[0]));
				city.setUf(fields[1]);
				city.setName(fields[2]);
				city.setCapital(fields[3].toLowerCase().equals("true") ? true : false);
				city.setLon(Double.parseDouble(fields[4]));
				city.setLat(Double.parseDouble(fields[5]));
				city.setNo_accents(fields[6]);
				city.setAlternative_names(fields[7]);
				city.setMicroregion(fields[8]);
				city.setMesoregion(fields[9]);

				cityRepository.save(city);
			});
		}

		return true;
	}

	public List<UFCityCount> findUFWithMoreFewerCities() {
		List<UFCityCount> ufs = new ArrayList<>();

		Object[] max = cityRepository.findUFMoreCities();
		if (max != null && max.length > 0) {
			Object[] ufMax = (Object[]) max[0];
			ufs.add(new UFCityCount(ufMax[0].toString(), Integer.parseInt(ufMax[1].toString()), ufMax[2].toString()));
		}

		Object[] min = cityRepository.findUFFewerCities();
		if (min != null && min.length > 0) {
			Object[] ufMin = (Object[]) min[0];
			ufs.add(new UFCityCount(ufMin[0].toString(), Integer.parseInt(ufMin[1].toString()), ufMin[2].toString()));
		}

		return ufs;
	}

	public List<UFCityCount> countCitiesPerUF() {
		List<UFCityCount> ufs = new ArrayList<>();

		Object[] max = cityRepository.countCitiesPerUF();
		if (max != null && max.length > 0) {
			for (int i = 0; i < max.length; i++) {
				Object[] ufMax = (Object[]) max[i];
				ufs.add(new UFCityCount(ufMax[0].toString(), Integer.parseInt(ufMax[1].toString()),
						ufMax[2].toString()));
			}
		}

		return ufs;
	}

	public void insertCity(Map<String, Object> cityJSON) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		City city = mapper.readValue(mapper.writeValueAsString(cityJSON), City.class);
		cityRepository.save(city);
	}

	public static Specification<City> byColumnNameAndValue(String columnName, String value) {
		return new Specification<City>() {
			@Override
			public Predicate toPredicate(Root<City> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.equal(root.<String>get(columnName), value);
			}
		};
	}

	public List<City> findCityByColumn(String randomColumnName, String valueToSearchFor) {
		CitySpecification cs = new CitySpecification(randomColumnName.replace("_", ""), valueToSearchFor);

		return cityRepository.findAll(cs);
	}

	public Double biggerDistanceBetweenCities() {
		List<City> cities = cityRepository.findAll();
		Double tmp, biggerDistance = 0D;
		City cityA, cityB;
		for(int i = 0; i < cities.size(); i++) {
			cityA = cities.get(i);
			for(int j = 0; j < cities.size(); j++) {
				cityB = cities.get(j);
				tmp = distance(cityA.getLat(), cityB.getLat(), cityA.getLon(), cityB.getLon(), 0D, 0D);
				biggerDistance = tmp > biggerDistance ? tmp : biggerDistance;
			}
		}

		return biggerDistance / 1000;
	}

	private double distance(double lat1, double lat2, double lon1, double lon2, double el1, double el2) {

		final int R = 6371;

		Double latDistance = deg2rad(lat2 - lat1);
		Double lonDistance = deg2rad(lon2 - lon1);
		Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(deg2rad(lat1))
				* Math.cos(deg2rad(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = R * c * 1000;

		double height = el1 - el2;
		distance = Math.pow(distance, 2) + Math.pow(height, 2);
		return Math.sqrt(distance);
	}

	private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

}
