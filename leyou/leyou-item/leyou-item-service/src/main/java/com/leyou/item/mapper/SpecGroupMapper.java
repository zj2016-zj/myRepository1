package com.leyou.item.mapper;

import com.leyou.item.pojo.SpecGroup;
import tk.mybatis.mapper.common.Mapper;

/**
 * 该表中存储的是组的分类id以及对应组的名字，一个分类id对应多个组
 */
public interface SpecGroupMapper extends Mapper<SpecGroup> {
}
