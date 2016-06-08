package com.eeesys.frame.db;

import java.util.Collection;
import java.util.List;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper {
	
	 private static class InstanceHolder {
	        static final DBHelper INSTANCE = new DBHelper();
	    }

	private SQLiteDatabase db;
	
	private DBHelper(){
		db = Connector.getDatabase();
	}
	
	public static DBHelper getInstance(){
		return InstanceHolder.INSTANCE;
	}
	
	public boolean save(DataSupport ds){
		return ds.save();
	}
	
	public void saveThrows(DataSupport ds){
		 ds.saveThrows();
	}
	
	public void saveAll(Collection<? extends DataSupport> collection){
		DataSupport.saveAll(collection);
	}
	
	public int delete(DataSupport ds){
		return ds.delete();
	}
	
	public int delete(Class<? extends DataSupport> modelClass, long id){
		return DataSupport.delete(modelClass, id);
	}
	
	public int deleteAll(Class<? extends DataSupport> modelClass, String...conditions){
		return DataSupport.deleteAll(modelClass, conditions);
	}
	
	public int deleteAll(String tableName, String...conditions){
		return DataSupport.deleteAll(tableName, conditions);
	}
	
	public int update(DataSupport ds, long id){
		return ds.update(id);
	}
	
	public int updateAll(DataSupport ds, String... conditions){
		return ds.updateAll(conditions);
	}
	
	public <T> int update(Class<T> modelClass, ContentValues values, long id){
		return DataSupport.update(modelClass, values, id);
	}
	
	public <T> int updateAll(Class<T> modelClass, ContentValues values, String... conditions){
		return DataSupport.updateAll(modelClass, values, conditions);
	}
	
	public int updateAll(String tableName, ContentValues values, String... conditions){
		return DataSupport.updateAll(tableName, values, conditions);
	}
	
	public <T> T find(Class<T> modelClass, long id){
		return DataSupport.find(modelClass, id);
	}
	
	public <T> T findLast(Class<T> modelClass){
		return DataSupport.findLast(modelClass);
	}
	
	public <T> T findFirst(Class<T> modelClass){
		return DataSupport.findFirst(modelClass);
	}
	
	public <T> List<T> find(Class<T> modelClass, long... ids){
		return DataSupport.findAll(modelClass, ids);
	}
	
	public void ClosedB(){
		if(db != null){
			db.close();
		}
	}

}
