package es.fpablo.springboot.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.fpablo.springboot.app.models.entity.EntityClazz;

/**
 * 
 * @author ferpabeng
 *
 */
public interface IEntityService {

	public List<EntityClazz> findAll();

	public Page<EntityClazz> findAll(Pageable pageable);

	public void save(EntityClazz entityClazz);

	public EntityClazz findOne(Long id);

	public void delete(Long id);

}
