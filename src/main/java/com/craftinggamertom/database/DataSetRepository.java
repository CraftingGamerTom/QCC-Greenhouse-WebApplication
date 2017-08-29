package com.craftinggamertom.database;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DataSetRepository extends MongoRepository<DataSet, String> {
	public DataSet findBySensorID(String sensorID);
	public List<DataSet> findAllBySensorID(String sensorID);
	
	public DataSet findByDate(Date date);
	public List<DataSet> findAllByDate(Date date);
}
