package com.example.codeshakeio.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@UtilityClass
public class ModelMapperUtils {
    private static ModelMapper modelMapper = new ModelMapper();

    /**
     * Model mapper property setting are specified in the following block.
     * Default property matching strategy is set to Strict see {@link MatchingStrategies}
     * Custom mappings are added using {@link ModelMapper#addMappings(PropertyMap)}
     */
    static {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public ModelMapper getModelMapper(PropertyMap<?, ?> propertyMapper) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.addMappings(propertyMapper);
        return modelMapper;
    }

    /**
     * <p>Note: outClass object must have default constructor with no arguments</p>
     *
     * @param <D>      type of result object.
     * @param <T>      type of source object to map from.
     * @param entity   entity that needs to be mapped.
     * @param outClass class of result object.
     * @return new object of <code>outClass</code> type.
     */
    public <D, T> D map(final T entity, Class<D> outClass) {
        return modelMapper.map(entity, outClass);
    }

    /**
     * <p>Note: outClass object must have default constructor with no arguments</p>
     *
     * @param <D>        type of objects in result list
     * @param <T>        type of entity in <code>entityList</code>
     * @param entityList list of entities that needs to be mapped
     * @param outCLass   class of result list element
     * @return list of mapped object with <code><D></code> type.
     */
    public <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass) {
        return mapAll(modelMapper, entityList, outCLass);
    }

    /**
     * <p>Note: outClass object must have default constructor with no arguments</p>
     *
     * @param <D>        type of objects in result list
     * @param <T>        type of entity in <code>entityList</code>
     * @param entityList list of entities that needs to be mapped
     * @param outCLass   class of result list element
     * @return list of mapped object with <code><D></code> type.
     */
    public <D, T> List<D> mapAll(ModelMapper modelMapperTmp, final Collection<T> entityList, Class<D> outCLass) {
        return entityList.stream()
                .map(entity -> modelMapperTmp.map(entity, outCLass))
                .collect(Collectors.toList());
    }

    /**
     * Maps {@code source} to {@code destination}.
     *
     * @param source      object to map from
     * @param destination object to map to
     */
    public <S, D> D map(final ModelMapper modelMapperTmp, final S source, D destination) {
        modelMapperTmp.map(source, destination);
        return destination;
    }

}
