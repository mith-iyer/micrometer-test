package com.miyer.testmicrometer.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.miyer.testmicrometer.repository.PersonRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@Transactional
@Path("/")
@RequiredArgsConstructor
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @GET
    @Path("/persons")
    @Produces({"application/json"})
    public Response listPerson() {
        log.info("List all persons");
        return Response.status(200)
            .entity(personRepository.findAll())
            .build();
    }

    @GET
    @Path("/persons/{id}")
    @Produces({"application/json"})
    public Response getPerson(
        @PathParam("id")
            Long id
    ) {
        log.info("Listing a person with id: {}", id);
        return Response.status(200)
            .entity(personRepository.findById(id))
            .build();
    }

}
