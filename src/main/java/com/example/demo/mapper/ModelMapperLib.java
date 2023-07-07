package com.example.demo.mapper;

// import com.github.dozermapper.core.DozerBeanMapperBuilder;
// import com.github.dozermapper.core.Mapper;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class ModelMapperLib {
    // private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    private static ModelMapper mapper = new ModelMapper();

    public static <O, D> D parseObject(O orgin, Class<D> destination) {
        return mapper.map(orgin, destination);
    }

    public static <O, D> List<D> parseListObjects(List<O> orgin, Class<D> destination) {
        List<D> destinationObjects = new ArrayList<>();

        for(O o: orgin) {
            destinationObjects.add(mapper.map(o, destination));
        }
        return  destinationObjects;
    }
}

