package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.item.mapper.*;
import com.leyou.item.pojo.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class GoodsService {
    //通用属性的具体信息值
    @Autowired
    private SpuMapper spuMapper;
    //调用分类service
    @Autowired
    private CategoryService categoryService;
     //查询商标的名称
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private SpuDetailMapper spuDetailMapper;
    @Autowired
    private StockMapper stockMapper;

    public PageResult<SpuBo> querySpuBoByPage(String key, Boolean saleable, Integer page, Integer rows) {
       //1、封装条件，用来进行查询
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        // 搜索条件 （根据title进行模糊查询）
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("title", "%" + key + "%");
        }
        if (saleable != null) {
            criteria.andEqualTo("saleable", saleable);
        }

        // 分页条件
        PageHelper.startPage(page, rows);


        // 执行查询
        List<Spu> spus = this.spuMapper.selectByExample(example);
        PageInfo<Spu> pageInfo = new PageInfo<>(spus);

        List<SpuBo> spuBos = new ArrayList<>();
        spus.forEach(spu->{
            SpuBo spuBo = new SpuBo();
            // copy共同属性的值到新的对象
            BeanUtils.copyProperties(spu, spuBo);
            // 查询分类名称
            List<String> names = this.categoryService.queryNamesByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
            spuBo.setCname(StringUtils.join(names, "/"));

            // 查询品牌的名称
            spuBo.setBname(this.brandMapper.selectByPrimaryKey(spu.getBrandId()).getName());

            spuBos.add(spuBo);
        });
        return new PageResult<>(pageInfo.getTotal(), spuBos);
    }

    /**
     * 新增商品
     * @param spuBo
     */
    @Transactional
    public void saveGoods(SpuBo spuBo) {
        // 新增spu
        // 设置默认字段
        spuBo.setId(null);
        spuBo.setSaleable(true);
        spuBo.setValid(true);
        spuBo.setCreateTime(new Date());
        spuBo.setLastUpdateTime(spuBo.getCreateTime());
        this.spuMapper.insertSelective(spuBo);

        // 新增spuDetail
        SpuDetail spuDetail = spuBo.getSpuDetail();
        spuDetail.setSpuId(spuBo.getId());
        this.spuDetailMapper.insertSelective(spuDetail);

        saveSkuAndStock(spuBo);
    }
  //保存库存信息  开始的库存信息保存在sku中，所以需要遍历获取库存数量
    private void saveSkuAndStock(SpuBo spuBo) {
        spuBo.getSkus().forEach(sku -> {
            // 新增sku
            sku.setSpuId(spuBo.getId());
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            this.skuMapper.insertSelective(sku);

            // 新增库存
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            this.stockMapper.insertSelective(stock);
        });
    }

    /**
     * 根据spuId查询spuDetail
     * @param spuId
     * @return
     */
    public SpuDetail querySpuDetailBySpuId(Long spuId) {

        return this.spuDetailMapper.selectByPrimaryKey(spuId);
    }

    /**
     * 根据spuId查询sku的集合
     * @param spuId
     * @return
     */
    public List<Sku> querySkusBySpuId(Long spuId) {
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        List<Sku> skus = this.skuMapper.select(sku);
        skus.forEach(s -> {
            Stock stock = this.stockMapper.selectByPrimaryKey(s.getId());
            s.setStock(stock.getStock());
        });
        return skus;
    }

    /**
     * 编辑商品详情
     * @param spuBo
     */
    @Transactional
    public void updateGoods(SpuBo spuBo){
        //根据spuId查询要删除的sku
        List<Sku> skus = this.querySkusBySpuId(spuBo.getId());
        skus.forEach(sku -> {
            //删除stock
            this.stockMapper.deleteByPrimaryKey(sku.getId());
        });
        //删除sku(根据spu_id直接删除)
        Sku sku = new Sku();
        sku.setSpuId(spuBo.getId());
        this.skuMapper.delete(sku);
        //新增sku
        //新增stock
        saveSkuAndStock(spuBo);
        // 更新spu
        spuBo.setLastUpdateTime(new Date());
        spuBo.setCreateTime(null);
        spuBo.setValid(null);
        spuBo.setSaleable(null);
        this.spuMapper.updateByPrimaryKeySelective(spuBo);
        // 更新spu详情
        this.spuDetailMapper.updateByPrimaryKeySelective(spuBo.getSpuDetail());
    }
}
