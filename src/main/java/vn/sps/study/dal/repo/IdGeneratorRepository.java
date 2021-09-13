package vn.sps.study.dal.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import vn.sps.study.dal.entities.IdGeneratorEntity;

@Repository
public interface IdGeneratorRepository
        extends CrudRepository<IdGeneratorEntity, String> {

}
