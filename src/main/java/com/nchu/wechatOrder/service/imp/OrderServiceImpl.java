package com.nchu.wechatOrder.service.imp;

import com.nchu.wechatOrder.domain.DO.OrderDetail;
import com.nchu.wechatOrder.domain.DO.OrderMaster;
import com.nchu.wechatOrder.domain.DO.ProductInfo;
import com.nchu.wechatOrder.domain.Enum.OrderEnum;
import com.nchu.wechatOrder.domain.Result.Result;
import com.nchu.wechatOrder.domain.VO.BaseInfo;
import com.nchu.wechatOrder.domain.VO.OrderVO;
import com.nchu.wechatOrder.domain.VO.PageResult;
import com.nchu.wechatOrder.domain.VO.SureOrderVO;
import com.nchu.wechatOrder.mapper.ex.OrderDetailMapperEx;
import com.nchu.wechatOrder.mapper.ex.OrderMasterMapperEx;
import com.nchu.wechatOrder.mapper.ex.ProductInfoMapperEx;
import com.nchu.wechatOrder.service.OrderService;
import com.nchu.wechatOrder.utils.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDetailMapperEx orderDetailMapper;
    @Autowired
    private OrderMasterMapperEx masterMapper;

    @Autowired
    private ProductInfoMapperEx productInfoMapperEx;


    /**
     * 将商品加入购物车
     *
     * @param OrderMap
     * @param baseInfo
     * @return
     */
    @Override
    @Transactional
    public Result<Boolean> toOrderCar(Map<String, Integer> OrderMap, BaseInfo baseInfo) {

        BigDecimal totalPrice = new BigDecimal(0);
        Integer totalNum = 0;

        //获取所有的菜品id 且菜品数量大于0的
        List<String> products = new ArrayList<>();
        OrderMap.forEach((k, v) -> {
            if (v > 0) {
                products.add(k);
            }
        });


        //根据菜品id列表查询所有的菜品
        List<ProductInfo> productInfos = productInfoMapperEx.selectByProductIds(products);
        //构造<菜品id，菜品实体>的map列表
        Map<String, ProductInfo> IdAndProductInfo = new HashMap<>();
        productInfos.forEach(x -> {
            IdAndProductInfo.put(x.getProductId(), x);
        });

        //遍历订单计算总数和总价
        for (Map.Entry<String, Integer> map : OrderMap.entrySet()) {
            //计算总数
            totalNum = totalNum + map.getValue();
            //获取单价
            BigDecimal Price = IdAndProductInfo.get(map.getKey()).getProductPrice();
            //获取单个菜品的总价
            BigDecimal singleSum = Price.multiply(BigDecimal.valueOf(map.getValue()));
            //计算订单总价
            totalPrice = singleSum.add(totalPrice);
        }

        //插入订单表
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setBuyerAddress(baseInfo.getBuyerAddress());
        orderMaster.setBuyerOpenid(baseInfo.getBuyerOpenId());
        orderMaster.setSellId(baseInfo.getSellId());
        orderMaster.setOrderAmount(totalPrice);
        orderMaster.setTotalnum(totalNum);
        byte status = OrderEnum.NO_Pay.getCode().byteValue();
        orderMaster.setOrderStatus(status);
        //生成唯一主键 由时间戳+6位数随机数组成
        String orderId = KeyUtil.genUniqueKey();
        orderMaster.setOrderId(orderId);
        orderMaster.setCreateTime(new Date());
        orderMaster.setUpdateTime(new Date());
        masterMapper.insert(orderMaster);

        //插入订单详情表


        for (ProductInfo productInfo : productInfos) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setCreateTime(new Date());
            String orderDetailId = KeyUtil.genUniqueKey();
            orderDetail.setDetailId(orderDetailId);
            orderDetail.setOrderId(orderId);
            orderDetail.setProductIcon(productInfo.getProductIcon());
            orderDetail.setProductId(productInfo.getProductId());
            orderDetail.setProductName(productInfo.getProductName());
            orderDetail.setProductPrice(productInfo.getProductPrice());
            Integer num = OrderMap.get(productInfo.getProductId());
            orderDetail.setProductQuantity(num);
            orderDetail.setUpdateTime(new Date());
            //todo 减去库存
            Integer nowStockNum = productInfo.getProductStock() - num;
            if (nowStockNum > 0) {
                productInfo.setProductStock(nowStockNum);
                productInfoMapperEx.updateByPrimaryKey(productInfo);
            } else {
                return Result.Create(-1, "库存不足");
            }

            //插入详情表
            orderDetailMapper.insert(orderDetail);


        }


        return Result.Create();
    }

    /**
     * 获取指定用户的购物车信息
     *
     * @param currPage
     * @param baseInfo
     * @return
     */
    @Override
    public PageResult queryOrderCarByUser(Integer currPage, BaseInfo baseInfo) {


        return queryOrderBystatus(currPage, baseInfo, OrderEnum.NO_Pay.getCode());

    }

    /**
     * 获取指定用户的已支付订单信息
     *
     * @param currPage
     * @param baseInfo
     * @return
     */
    @Override
    public PageResult queryUserOrderByUser(Integer currPage, BaseInfo baseInfo) {
        return queryOrderBystatus(currPage, baseInfo, OrderEnum.Pay.getCode());
    }

    /**
     * 获取指定用户的待退款订单信息
     * @param currPage
     * @param baseInfo
     * @return
     */
    @Override
    public PageResult queryapplyPayBack(Integer currPage, BaseInfo baseInfo) {
        return queryOrderBystatus(currPage, baseInfo, OrderEnum.APPLY_PAYBACK.getCode());
    }

    /**
     * 获取指定用户的已经退款完成订单信息
     * @param currPage
     * @param baseInfo
     * @return
     */
    @Override
    public PageResult QueryPayBack(Integer currPage, BaseInfo baseInfo) {
        return queryOrderBystatus(currPage, baseInfo, OrderEnum.PAYBACK.getCode());
    }

    /**
     * 获取指定用户的商家已接单的订单信息
     * @param currPage
     * @param baseInfo
     * @return
     */
    @Override
    public PageResult QuerySureOrder(Integer currPage, BaseInfo baseInfo) {
        return queryOrderBystatus(currPage, baseInfo, OrderEnum.SURE_ORDER.getCode());
    }


    /**
     * 根据订单状态查询用户的所有订单
     */
    private PageResult queryOrderBystatus(Integer currPage, BaseInfo baseInfo, Integer status) {
        //获取该用户的购物车订单  根据用户id和订单状态为0  且分页 每页2个订单
        List<OrderMaster> orderMasters = masterMapper.selectByOpenId((currPage - 1) * 2, 2, baseInfo.getBuyerOpenId(), status);

        //获取该用户的总订单数
        Integer totalSize = masterMapper.selectCountByPageAndOpenId(baseInfo.getBuyerOpenId(), status);


        if (orderMasters == null || orderMasters.size() == 0) {
            return PageResult.Create(null, currPage, totalSize / 2 + 1);
        }

        //获取所有的订单id
        List<String> OrderIds = orderMasters.stream().map(x -> x.getOrderId()).collect(Collectors.toList());

        //根据订单id列表 查询所有的菜品
        List<OrderDetail> orderDetails = orderDetailMapper.selectByOrderIds(OrderIds);

        //将相同订单号下的订单都归并到一起
        Map<String, List<OrderDetail>> OrderIdAndDetails = new HashMap<>();
        for (OrderDetail orderDetail : orderDetails) {

            if (OrderIdAndDetails.get(orderDetail.getOrderId()) == null) {
                List<OrderDetail> list = new ArrayList<>();
                list.add(orderDetail);
                OrderIdAndDetails.put(orderDetail.getOrderId(), list);
            } else {
                OrderIdAndDetails.get(orderDetail.getOrderId()).add(orderDetail);
            }
        }


        List<OrderVO> orderVOS = new ArrayList<>();

        //构建返回给前端的结果
        for (OrderMaster orderMaster : orderMasters) {
            OrderVO orderVO = new OrderVO();
            orderVO.setOrderMaster(orderMaster);
            List<OrderDetail> orderDetailList = OrderIdAndDetails.get(orderMaster.getOrderId());
            orderVO.setOrderDetailList(orderDetailList);
            orderVOS.add(orderVO);
        }


        return PageResult.Create(orderVOS, currPage, totalSize / 2 + 1);
    }


    /**
     * 取消订单
     *
     * @param OrderId
     * @return
     */
    @Override
    @Transactional
    public Result<Boolean> CancelOrderCar(String OrderId) {

        //todo 加库存
        addStock(OrderId);
        //删除订单详情项目
        orderDetailMapper.deleteByOrderId(OrderId);
        //删除订单项目
        masterMapper.deleteByPrimaryKey(OrderId);

        return Result.Create();
    }

    /**
     * 申请退款
     * @param OrderId
     * @return
     */
    @Override
    public Result<Boolean> applyPayBack(String OrderId) {

        //更改订单状态为申请退款中
       OrderMaster orderMaster= masterMapper.selectByPrimaryKey(OrderId);
       Byte status=OrderEnum.APPLY_PAYBACK.getCode().byteValue();
       orderMaster.setOrderStatus(status);
        masterMapper.updateByPrimaryKey(orderMaster);

        return Result.Create();
    }

    /**
     * 取消申请退款
     * @param OrderId
     * @return
     */
    @Override
    public Result<Boolean> CancelapplyPayBack(String OrderId) {
        //更改订单状态从申请退款变更为已支付
        OrderMaster orderMaster= masterMapper.selectByPrimaryKey(OrderId);
        Byte status=OrderEnum.Pay.getCode().byteValue();
        orderMaster.setOrderStatus(status);
        masterMapper.updateByPrimaryKey(orderMaster);

        return Result.Create();

    }


    /**
     * 退款和取消订单时候增加库存
     */
    private void addStock(String OrderId){

        List<OrderDetail> orderDetailList = orderDetailMapper.selectByOrderId(OrderId);
        List<String> ProductIds = orderDetailList.stream().map(x -> x.getProductId()).collect(Collectors.toList());
        List<ProductInfo> productInfos = productInfoMapperEx.selectByProductIds(ProductIds);


        Map<String,ProductInfo> productInfoMap=new HashMap<>();
        for (ProductInfo productInfo : productInfos) {
            productInfoMap.put(productInfo.getProductId(),productInfo);
        }



        for (OrderDetail orderDetail : orderDetailList) {
            ProductInfo productInfo=productInfoMap.get(orderDetail.getProductId());
            Integer nowStockNum=productInfo.getProductStock()+orderDetail.getProductQuantity();
            productInfo.setProductStock(nowStockNum);
            //更新库存
            productInfoMapperEx.updateByPrimaryKey(productInfo);

        }



    }



    /**
     * 用户确认订单
     *
     * @param sureOrderVO
     * @return
     */
    @Override
    @Transactional
    public Result<Boolean> SureOrder(SureOrderVO sureOrderVO) {

        String OrderId = sureOrderVO.getOrderId();

        //查询订单信息
        OrderMaster orderMaster = masterMapper.selectByPrimaryKey(OrderId);
        Map<String, Integer> OrderMap = sureOrderVO.getListMap();

        List<String> DetailIds = new ArrayList<>();
        //获取菜单详情id列表
        if (OrderMap != null && OrderMap.size() != 0) {
            OrderMap.forEach((k, v) -> {
                DetailIds.add(k);
            });

        }

        //所有的子菜单列表
        List<OrderDetail> AllorderDetails = new ArrayList<>();
        if (DetailIds.size() != 0) {
            AllorderDetails = orderDetailMapper.selectByDetailIds(DetailIds);

        }

        //该订单下的子菜单列表  筛选一次  确定无误
        List<OrderDetail> orderDetails = new ArrayList<>();
        if (AllorderDetails.size() != 0) {
            for (OrderDetail orderDetail : AllorderDetails) {
                if (orderDetail.getOrderId().equals(OrderId)) {
                    orderDetails.add(orderDetail);
                }
            }

        }


        if (orderDetails.size() != 0) {

            for (OrderDetail orderDetail : orderDetails) {
                //更新子菜单的数量
                orderDetail.setUpdateTime(new Date());
                orderDetail.setProductQuantity(OrderMap.get(orderDetail.getDetailId()));
                orderDetailMapper.updateByPrimaryKey(orderDetail);
            }

            //获取该订单下的所有子菜单项
            List<OrderDetail> orderDetailList = orderDetailMapper.selectByOrderId(OrderId);
            BigDecimal totalPrice = new BigDecimal(0);
            Integer totalNum = 0;

            for (OrderDetail orderDetail : orderDetailList) {

                //子菜单的总数量
                totalNum = totalNum + orderDetail.getProductQuantity();
                //获取单价
                BigDecimal Price = orderDetail.getProductPrice();
                //获取单个菜品的总价
                BigDecimal singleSum = Price.multiply(BigDecimal.valueOf(orderDetail.getProductQuantity()));
                //计算订单总价
                totalPrice = singleSum.add(totalPrice);
            }


            //更新订单的总价  子菜单的总数量 和更改该订单状态为已支付
            orderMaster.setUpdateTime(new Date());
            orderMaster.setTotalnum(totalNum);
            orderMaster.setOrderAmount(totalPrice);
            Byte status = OrderEnum.Pay.getCode().byteValue();
            orderMaster.setOrderStatus(status);
            masterMapper.updateByPrimaryKey(orderMaster);


        } else {
            orderMaster.setUpdateTime(new Date());
            Byte status = OrderEnum.Pay.getCode().byteValue();
            orderMaster.setOrderStatus(status);
            masterMapper.updateByPrimaryKey(orderMaster);
            //更改该订单状态为已支付
        }


        return Result.Create();
    }
}
