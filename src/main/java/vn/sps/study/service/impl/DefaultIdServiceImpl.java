package vn.sps.study.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.sps.study.dal.entities.IdGeneratorEntity;
import vn.sps.study.dal.repo.IdGeneratorRepository;
import vn.sps.study.service.IdService;

@Service
public class DefaultIdServiceImpl implements IdService {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(DefaultIdServiceImpl.class);

	@Autowired
	private IdGeneratorRepository idRepository;

	@Override
	public String next(String generatorName) {

		IdGeneratorEntity generator = null;
		int currentId = 0;
		String nextId = null;

		synchronized (generatorName) {

			Optional<IdGeneratorEntity> o = idRepository
			        .findById(generatorName);
			if (o.isPresent()) {
				generator = o.get();
				currentId = Integer.valueOf(generator.getCurrentValue())
				        .intValue();
			} else {
				generator = new IdGeneratorEntity();
				generator.setGeneratorName(generatorName);
				generator.setDataType("INTEGER");
			}
			nextId = String.valueOf(currentId + 1);
			generator.setCurrentValue(nextId);
			idRepository.save(generator);
			LOGGER.info("Generator {} was changed value from {} to {}",
			        generatorName, currentId, nextId);
		}
		return nextId;
	}

}
