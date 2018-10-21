package es.fpablo.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.fpablo.springboot.app.models.dao.IEntityDao;
import es.fpablo.springboot.app.models.entity.EntityClazz;

/**
 * 
 * @author ferpabeng
 *
 */
@Service
public class EntityServiceImpl implements IEntityService {

	@Autowired
	private IEntityDao entityDao;

	@Override
	@Transactional(readOnly = true)
	public List<EntityClazz> findAll() {
		return (List<EntityClazz>) entityDao.findAll();
	}

	@Override
	@Transactional
	public void save(EntityClazz entityClazz) {
		entityDao.save(entityClazz);
	}

	@Override
	@Transactional(readOnly = true)
	public EntityClazz findOne(Long id) {
		return entityDao.findOne(id);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		entityDao.delete(id);

	}

	@Override
	public Page<EntityClazz> findAll(Pageable pageable) {
		return entityDao.findAll(pageable);
	}

}
