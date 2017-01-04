package com.strengthtailor.model.repository;

import java.util.List;

import com.strengthtailor.model.entity.Regime;
import org.springframework.data.repository.CrudRepository;

public interface RegimeRepository extends CrudRepository<Regime, Long> {

    List<Regime> findByLastName(String lastName);
}

