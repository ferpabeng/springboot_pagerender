package es.fpablo.springboot.app.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import es.fpablo.springboot.app.models.entity.EntityClazz;

/**
 * 
 * @author ferpabeng
 *
 */
public interface IEntityDao extends PagingAndSortingRepository<EntityClazz, Long> {

}
