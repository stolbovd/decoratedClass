package ru.inkontext.rest;

/**
 * Created by stolbovd on 06.08.14.
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.inkontext.domain.projections.PersonCityProjection;
import ru.inkontext.persistence.PersonRepository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Slf4j
@RestController
@RequestMapping(value = "/rest/persons")
public class PersonRestController {

	@Autowired
	ProjectionFactory projectionFactory;

	@Autowired
	PersonRepository personRepository;

	@RequestMapping(value = "search/adressCity", method = GET)
	public List<PersonCityProjection> findPersonsOrderByAdress() throws Exception {
		List<PersonCityProjection> projections = new ArrayList<>();
		personRepository.findAllOrderByAdress().forEach(entity ->
				projections.add(projectionFactory.createProjection(PersonCityProjection.class, entity)));
		return projections;
	}

	@RequestMapping(value = "{id}/adressCity", method = GET)
	public PersonCityProjection findPersonById(@PathVariable("id") Long id) throws Exception {
		return projectionFactory.createProjection(PersonCityProjection.class, personRepository.findById(id));
	}
}
