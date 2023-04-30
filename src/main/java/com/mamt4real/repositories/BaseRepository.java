package com.mamt4real.repositories;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.google.common.collect.Lists;
import com.mamt4real.interfaces.CrudOperations;
import com.mamt4real.interfaces.HasID;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class BaseRepository<T extends HasID> implements CrudOperations<T> {
    protected final HashMap<Long, T> storage;
    private long lastId = 0;

    private final Class<T> classOfT;

    private final String STORAGE_FILE;

    public BaseRepository(String filename, Class<T> classOfT) {
        this.STORAGE_FILE = filename;
        this.classOfT = classOfT;
        storage =new HashMap<>();
        load();
    }

    @Override
    public long createOne(T data) {
        data.setId(++lastId);
        storage.put(lastId, data);
        return data.getId();

    }

    @Override
    public List<T> getAll() {
        return Lists.newArrayList(storage.values());
    }

    @Override
    public Optional<T> getOne(long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public T update(T data) {
        storage.put(data.getId(), data);
        return data;
    }

    @Override
    public void delete(T data) {
        storage.remove(data.getId());
    }

    public void load() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            MapType colType = mapper.getTypeFactory().constructMapType(HashMap.class, Long.class, classOfT);
            HashMap<Long, T> map = mapper.readValue(new FileReader(STORAGE_FILE), colType);
            lastId = map.size();
            storage.putAll(map);
        } catch (JsonGenerationException | JsonMappingException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    public void save() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new FileWriter(STORAGE_FILE), storage);
        } catch (JsonGenerationException | JsonMappingException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    public void purge(){
        storage.clear();
        lastId =0;
    }

}
