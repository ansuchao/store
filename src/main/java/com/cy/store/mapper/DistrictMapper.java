package com.cy.store.mapper;

import com.cy.store.entity.District;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface DistrictMapper {
    List<District> findByParent(String parent);

    String findNameByCode(String code);
}
