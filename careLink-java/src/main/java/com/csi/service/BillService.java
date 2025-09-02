package com.csi.service;

import com.csi.domain.Bill;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BillService {

    //创建新订单
    int saveBill(Bill bill);

    //通过id删除订单
    int deleteBillById(int id);

    //通过订单id修改订单状态
    int modifyBill(Bill bill);

    //通过id查询订单
    Bill findBillById(int id);

    //查询全部订单
    List<Bill> findAllBill(String key);

    double getPriceByEmpId(String employeeId);

    double getPriceRoomNo(int roomNo);

    double getPriceByFood();

}
