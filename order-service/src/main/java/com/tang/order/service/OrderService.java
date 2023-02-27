package com.tang.order.service;

import com.tang.order.mapper.OrderMapper;
import com.tang.order.pojo.Order;
import com.tang.order.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private RestTemplate restTemplate;

    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);
        // 2.使用RestTemplate发起request请求，查询用户
        // 2.1 url路径
        String url = "http://localhost:8081/user/" + order.getUserId();
        // 2.2发送http请求，实现远程调用
        final var user = restTemplate.getForObject(url, User.class);
        // 3.设置order的user属性
        order.setUser(user);
        // 4.返回
        return order;
    }
}
