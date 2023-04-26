package com.haziqjava.restfulwebservices.filtering;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class FilteringController {

  @GetMapping("/filtering")
  public MappingJacksonValue filtering() {
    SomeBean someBean = new SomeBean("value 1", "value 2", "value 3");
    return serializingBeans(someBean, "field1", "field3");
  }

  @GetMapping("/filtering-list")
  public MappingJacksonValue filteringList() {
    List<SomeBean> someBeans = Arrays.asList(new SomeBean("value 1", "value 2", "value 3"),
            new SomeBean("value 13", "value 6", "value 4"));
    return serializingBeans(someBeans, "field2", "field3");
  }

//  Info: Serializing
  private MappingJacksonValue serializingBeans(Object data, String field1, String field2) {
    //    INFO: A simple holder for the POJO to serialize via MappingJackson2HttpMessageConverter along
//    with further serialization instructions to be passed in to the converter.
//    On the server side this wrapper is added with a ResponseBodyInterceptor after content negotiation
//    selects the converter to use but before the write.
    MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(data);
    SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(field1, field2);
    FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
    mappingJacksonValue.setFilters(filters);
    return mappingJacksonValue;
  }
}
