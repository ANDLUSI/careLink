package com.csi.service.impl;

import com.csi.domain.Contract;
import com.csi.mapper.ContractMapper;
import com.csi.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ContractServiceImpl implements ContractService {

    @Autowired
    ContractMapper contractMapper;

    @Override
    public int saveId(int residentId) {
        return contractMapper.saveId(residentId);
    }

    @Override
    public int addContract(Contract contract) {
        return contractMapper.addContract(contract);
    }
    @Override
    public int deleteContractById(int id) {
        return contractMapper.deleteContractById(id);
    }

    @Override
    public int modifyContract(Contract contract) {
        return contractMapper.modifyContract(contract);
    }

    @Override
    public Contract findContractById(int id) {
        return contractMapper.findContractById(id);
    }

    @Override
    public List<Contract> findAllContract(String key) {
        return contractMapper.findAllContract(key);
    }
}
