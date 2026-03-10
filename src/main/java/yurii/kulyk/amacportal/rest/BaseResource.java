package yurii.kulyk.amacportal.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public abstract class BaseResource<T>{

    protected abstract List<T> findAll();
    protected abstract T find(UUID id);
    protected abstract T create(T entity);
    protected abstract T update(UUID id, T entity);
    protected abstract void delete(UUID id);

    @GET
    public List<T> getAll() {
        return findAll();
    }

    @GET
    @Path("/{id}")
    public T get(@PathParam("id") UUID id) {
        return find(id);
    }

    @POST
    public T post(T entity) {
        return create(entity);
    }

    @PUT
    @Path("/{id}")
    public T put(@PathParam("id") UUID id, T entity) {
        return update(id, entity);
    }

    @DELETE
    @Path("/{id}")
    public void deleteById(@PathParam("id") UUID id) {
        delete(id);
    }

}
