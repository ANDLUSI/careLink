package com.csi.service.impl;

import com.csi.domain.Bill;
import com.csi.mapper.BillMapper;
import com.csi.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BillServiceImpl implements BillService {

    @Autowired
    BillMapper billMapper;

    @Override
    public int saveBill(Bill bill) {
        return billMapper.saveBill(bill);
    }

    @Override
    public int deleteBillById(int id) {
        return billMapper.deleteBillById(id);
    }

    @Override
    public int modifyBill(Bill bill) {
        return billMapper.modifyBill(bill);
    }

    @Override
    public Bill findBillById(int id) {
        return billMapper.findBillById(id);
    }

    @Override
    public List<Bill> findAllBill(String key) {
        return billMapper.findAllBill(key);
    }

    @Override
    public double getPriceByEmpId(String employeeId) {
        return billMapper.getPriceByEmpId(employeeId);
    }

    @Override
    public double getPriceRoomNo(int roomNo) {
        return billMapper.getPriceRoomNo(roomNo);
    }

    @Override
    public double getPriceByFood() {
        return billMapper.getPriceByFood();
    }
}
