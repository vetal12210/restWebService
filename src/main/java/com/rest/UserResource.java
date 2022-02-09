package com.rest;

import com.entity.User;
import com.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@CrossOriginResourceSharing(allowAllOrigins = true)
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@Path("/users")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Log4j2
public class UserResource {

    public UserResource() {
    }

    @Autowired
    private UserService userService;

    /**
     * Generated json response for output all users.
     *
     * @return json array with users from DB
     */

    @GET
    public List<User> getUsers() {
        log.info("Find all users");
        return userService.findAll();
    }

    /**
     * Return user in json format
     *
     * @param id users id from DB
     * @return the user
     */

    @GET
    @Path(value = "/{id}")
    public Response getUserById(@PathParam("id") String id) {
        log.info("Find user by id {}", id);
        User foundUser;
        try {
            foundUser = userService.findById(Long.valueOf(id));
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Id cannot be not Long").build();
        }
        if (foundUser == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("User not found by this id: " + id).build();
        }

        return Response.status(Response.Status.OK).entity(foundUser).build();
    }


    /**
     * Return user in json format
     *
     * @param login users' login from DB
     * @return the user
     */

    @GET
    @Path("/by-login/{login}")
    public Response getUserByLogin(@PathParam("login") String login) {
        log.info("Find user by login {}", login);
        User foundUser;
        foundUser = userService.findByLogin(login);
        if (foundUser != null) {
            return Response.status(Response.Status.OK).entity(foundUser).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("User not found by this login: " + login).build();
        }
    }

    /**
     * Return user in json format
     *
     * @param email users' email from DB
     * @return the user
     */

    @GET()
    @Path("/by-email/{email}")
    public Response getUserByEmail(@PathParam("email") String email) {
        log.info("Find user by email {}", email);
        User foundUser;
        foundUser = userService.findByEmail(email);
        if (foundUser != null) {
            return Response.status(Response.Status.OK).entity(foundUser).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("User not found by this email: " + email).build();
        }
    }

    /**
     * Add user to DB
     *
     * @param user the user
     * @return the response 200 - if user was added and 400 when user could be
     * added to DB
     */

    @POST
    @Path("/create")
    public Response create(@Valid User user) {
        log.info("Create user {}", user);
        if (user != null) {
            userService.create(user);
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    /**
     * Delete user from db by  id.
     *
     * @param id users id
     * @return the response
     */

    @DELETE
    @Path("/delete/{userId}")
    public Response delete(@PathParam("userId") @Valid Long id) {
        log.info("Delete user with id {}", id);
        User foundUser = userService.findById(id);
        if (foundUser != null) {
            userService.remove(foundUser);
            return Response.ok().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }


    /**
     * Update user response.
     *
     * @param user the user
     * @param id   users id
     * @return the response 200 -if user was updated,  and 400 when user
     * could be updated in DB
     */

    @PUT
    @Path("/edit/{userId}")
    public Response update(@PathParam("userId") @Valid Long id, @Valid User user) {
        log.info("Update user {} with id {}", user, id);
        if (user != null) {
            userService.update(user);
            return Response.ok().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
