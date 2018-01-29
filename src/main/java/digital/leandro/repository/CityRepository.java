package digital.leandro.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import digital.leandro.model.City;

public interface CityRepository extends CrudRepository<City, Long>, JpaRepository<City, Long>, JpaSpecificationExecutor<City> {
	List<City> findByCapitalOrderByName(boolean capital);
	List<City> findByIbgeid(Long ibgeId);
	
	@Transactional
	List<City> deleteByIbgeid(Long ibgeId);
	
	@Query(value = "SELECT C.UF, (SELECT COUNT(*) FROM CITY Y WHERE C.UF = Y.uf) cityCount, 'MAIOR' AS STATUS "
			+ "FROM CITY c WHERE C.UF <> 'DF'	"
			+ "GROUP BY C.UF "
			+ "ORDER BY cityCount DESC "
			+ "LIMIT 1", nativeQuery = true)	
	Object[] findUFMoreCities();
	
	@Query(value = "SELECT C.UF, (SELECT COUNT(*) FROM CITY Y WHERE C.UF = Y.uf) cityCount, 'MENOR' AS STATUS "
			+ "FROM CITY c WHERE C.UF <> 'DF'	"
			+ "GROUP BY C.UF "
			+ "ORDER BY cityCount ASC "
			+ "LIMIT 1", nativeQuery = true)	
	Object[] findUFFewerCities();
	
	@Query(value = "SELECT C.UF, (SELECT COUNT(*) FROM CITY Y WHERE C.UF = Y.uf) cityCount, '' AS STATUS "
			+ "FROM CITY c "
			+ "GROUP BY C.UF "
			+ "ORDER BY cityCount ASC", nativeQuery = true)	
	Object[] countCitiesPerUF();

	@Query(value = "SELECT NAME FROM CITY WHERE UF = ?1 ORDER BY NAME ASC", nativeQuery = true)	
	List<String> findCityByUf(String uf);
	
	@Query(value = "SELECT COUNT(*) FROM CITY", nativeQuery = true)	
	Long countByIbgeid();	
}
