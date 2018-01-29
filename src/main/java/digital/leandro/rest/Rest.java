package digital.leandro.rest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import digital.leandro.dto.UFCityCount;
import digital.leandro.model.City;
import digital.leandro.repository.CityRepository;
import digital.leandro.service.CityService;

@RestController
@RequestMapping("/api")
public class Rest {

	@Autowired
	CityService cityService;

	@Autowired
	CityRepository cityRepository;

	@RequestMapping(value = "/load", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<String> load() throws JsonGenerationException, JsonMappingException, IOException {
		try {
			cityService.loadCitiesToDB();
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = "/capital", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<City>> allCapital() throws JsonGenerationException, JsonMappingException, IOException {

		List<City> cities = cityRepository.findByCapitalOrderByName(true);
		return new ResponseEntity<List<City>>(cities, HttpStatus.OK);
	}

	@RequestMapping(value = "/ufcitycount", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<UFCityCount>> ufCityCount()
			throws JsonGenerationException, JsonMappingException, IOException {
		List<UFCityCount> uf = cityService.findUFWithMoreFewerCities();
		return new ResponseEntity<List<UFCityCount>>(uf, HttpStatus.OK);
	}

	@RequestMapping(value = "/ufcitycountall", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<UFCityCount>> ufCityCountAll()
			throws JsonGenerationException, JsonMappingException, IOException {
		List<UFCityCount> uf = cityService.countCitiesPerUF();
		return new ResponseEntity<List<UFCityCount>>(uf, HttpStatus.OK);
	}

	@RequestMapping(value = "/citybyibge/{ibgeId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<City>> cityByIbge(@PathVariable long ibgeId)
			throws JsonGenerationException, JsonMappingException, IOException {
		List<City> cities = cityRepository.findByIbgeid(ibgeId);
		return new ResponseEntity<List<City>>(cities, HttpStatus.OK);
	}

	@RequestMapping(value = "/citybyuf/{uf}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<String>> cityByUf(@PathVariable String uf)
			throws JsonGenerationException, JsonMappingException, IOException {
		List<String> cities = cityRepository.findCityByUf(uf);
		return new ResponseEntity<List<String>>(cities, HttpStatus.OK);
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public void insertCity(@RequestBody Map<String, Object> city)
			throws JsonParseException, JsonMappingException, IOException {
		cityService.insertCity(city);
	}

	@RequestMapping(value = "/delete/{ibgeId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<City>> deleteCity(@PathVariable long ibgeId)
			throws JsonParseException, JsonMappingException, IOException {
		List<City> cities = cityRepository.deleteByIbgeid(ibgeId);
		return new ResponseEntity<List<City>>(cities, HttpStatus.OK);
	}

	@RequestMapping(value = "/findColumn/column/{column}/search/{search}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<City>> cityByIbge(@PathVariable String column, @PathVariable String search)
			throws JsonGenerationException, JsonMappingException, IOException {
		List<City> cities = cityService.findCityByColumn(column, search);
		return new ResponseEntity<List<City>>(cities, HttpStatus.OK);
	}


	@RequestMapping(value = "/countCities", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Long> countCities() {
		Long cities = cityRepository.countByIbgeid();
		return new ResponseEntity<Long>(cities, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/biggerDistance", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Double> biggerDistance() {
		Double cities = cityService.biggerDistanceBetweenCities();
		return new ResponseEntity<Double>(cities, HttpStatus.OK);
	}

}
