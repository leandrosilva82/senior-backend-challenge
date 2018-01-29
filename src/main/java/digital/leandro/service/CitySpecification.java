package digital.leandro.service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import digital.leandro.model.City;

public class CitySpecification implements Specification<City>{
	private String randomColumnName; 
    private String valueToSearchFor;

    public CitySpecification(String randomColumnName, String valueToSearchFor) {
        this.randomColumnName = randomColumnName;
        this.valueToSearchFor = valueToSearchFor;
    }

    @Override
    public Predicate toPredicate(Root<City> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.and(builder.equal(root.<String>get(this.randomColumnName), this.valueToSearchFor));
    }
}
